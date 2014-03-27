package com.ps.custom.util.persistence;

import com.ps.custom.SecurityConstants;
import com.ps.utils.Exceptions;
import com.ps.utils.ServletUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package com.ps.custom.util.persistence
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
public class DynamicSpecifications {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSpecifications.class);

    // 用于存储每个线程的request请求
    private static final ThreadLocal<HttpServletRequest> LOCAL_REQUEST = new ThreadLocal<HttpServletRequest>();

    private static final String SHORT_DATE = "yyyy-MM-dd";
    private static final String LONG_DATE = "yyyy-MM-dd mm:HH:ss";
    private static final String TIME = "mm:HH:ss";

    public static void putRequest(HttpServletRequest request) {
        LOCAL_REQUEST.set(request);
    }

    public static HttpServletRequest getRequest() {
        return LOCAL_REQUEST.get();
    }

    public static void removeRequest() {
        LOCAL_REQUEST.remove();
    }

    public static Collection<SearchFilter> genSearchFilter(ServletRequest request) {
        Map<String, Object> searchParams = ServletUtils.getParametersStartingWith(request, SecurityConstants.SEARCH_PREFIX);
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        return filters.values();
    }

    public static <T> Specification<T> bySearchFilter(ServletRequest request, final Class<T> entityClazz, final Collection<SearchFilter> searchFilters) {
        return bySearchFilter(request, entityClazz, searchFilters.toArray(new SearchFilter[]{}));
    }

    public static <T> Specification<T> bySearchFilter(ServletRequest request, final Class<T> entityClazz, final SearchFilter... searchFilters) {
        Collection<SearchFilter> filters = genSearchFilter(request);
        Set<SearchFilter> set = new HashSet<SearchFilter>(filters);
        for (SearchFilter searchFilter : searchFilters) {
            set.add(searchFilter);
        }
        return bySearchFilter(entityClazz, set);
    }

    public static <T> Specification<T> bySearchFilter(final Class<T> entityClazz, final Collection<SearchFilter> searchFilters) {
        final Set<SearchFilter> filterSet = new HashSet<SearchFilter>();
        ServletRequest request = getRequest();
        if (request != null) {
            // 数据权限中的FILTER
            Collection<SearchFilter> nestFilters = (Collection<SearchFilter>) request.getAttribute(SecurityConstants.NEST_DYNAMIC_SEARCH);
            if (nestFilters != null && !nestFilters.isEmpty()) {
                for (SearchFilter searchFilter : nestFilters) {
                    filterSet.add(searchFilter);
                }
            }
        }
        //自定义FILTER
        for (SearchFilter searchFilter : searchFilters) {
            filterSet.add(searchFilter);
        }
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (filterSet != null && !filterSet.isEmpty()) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    for (SearchFilter filter : filterSet) {
                        // nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
                        String[] names = StringUtils.split(filter.getFieldName(), ".");
                        Path expression = tRoot.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }
                        Class clazz = expression.getJavaType();
                        if (Date.class.isAssignableFrom(clazz) && !filter.getValue().getClass().equals(clazz)) {
                            filter.setValue(convert2Date((String) filter.getValue()));
                        } else if (Enum.class.isAssignableFrom(clazz) && !filter.getValue().getClass().equals(clazz)) {
                            filter.setValue(convert2Enum(clazz, (String) filter.getValue()));
                        }
                        switch (filter.getOperator()) {
                            case EQ:
                                predicates.add(criteriaBuilder.equal(expression, filter.getValue()));
                                break;
                            case LIKE:
                                predicates.add(criteriaBuilder.like(expression, "%" + filter.getValue() + "%"));
                                break;
                            case GT:
                                predicates.add(criteriaBuilder.greaterThan(expression, (Comparable) filter.getValue()));
                                break;
                            case LT:
                                predicates.add(criteriaBuilder.lessThan(expression, (Comparable) filter.getValue()));
                                break;
                            case GTE:
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
                                break;
                            case LTE:
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
                                break;
                            case IN:
                                predicates.add(criteriaBuilder.in(expression.in((Object[]) filter.getValue())));
                                break;
                        }
                    }
                    //把所有条件用AND 连接起来
                    if (predicates.size() > 0) {
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static <E extends Enum<E>> E convert2Enum(Class<E> enumClass, String enumString) {
        return EnumUtils.getEnum(enumClass, enumString);
    }

    private static Date convert2Date(String dateString) {
        SimpleDateFormat sFormat = new SimpleDateFormat(SHORT_DATE);
        try {
            return sFormat.parse(dateString);
        } catch (ParseException e) {
            try {
                return sFormat.parse(LONG_DATE);
            } catch (ParseException e1) {
                try {
                    return sFormat.parse(TIME);
                } catch (ParseException e2) {
                    logger.error("Convert time is error! The dateString is " + dateString + "." + Exceptions.getStackTraceAsString(e2));
                }
            }
        }
        return null;
    }

}
