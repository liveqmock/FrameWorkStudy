package com.ps.custom.util.persistence;


import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package com.ps.custom.util.persistence
 * @Description
 * @Date 14-3-3
 * @USER saxisuer
 */
public class SearchFilter {
    public SearchFilter() {
    }

    public enum Operator {
        EQ, LIKE, GT, LT, GTE, LTE, IN
    }

    private String fieldName;
    private Object value;
    private Operator operator;

    public SearchFilter(String fieldName, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    /**
     * earchParams中key的格式为OPERATOR_FIELDNAME
     *
     * @param searchParams
     * @return
     */
    public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if (!StringUtils.isNotBlank(value)) {
                continue; // 过滤掉空值
            }
            String[] names = StringUtils.split(key, "_");
            if (names.length != 2) {
                throw new IllegalArgumentException(key + " is not a valid search filter name");
            }
            String filedName = names[1];
            Operator operator = Operator.valueOf(names[0]);

            // 创建searchFilter
            SearchFilter filter = new SearchFilter(filedName, operator, value);
            filters.put(key, filter);
        }
        return filters;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
