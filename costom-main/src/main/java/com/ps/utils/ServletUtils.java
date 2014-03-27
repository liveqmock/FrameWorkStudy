package com.ps.utils;


import org.apache.commons.lang3.Validate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Package com.ps.utils
 * @Description
 * @Date 14-3-10
 * @USER saxisuer
 */
public class ServletUtils {

    /**
     * 设置让浏览器弹出下载对话框的Header,不同浏览器使用不同的编码方式.
     *
     * @param request
     * @param response
     * @param fileName
     */
    public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String CONTENT_DISPOSITION = "Content-Disposition";
        try {
            String agent = request.getHeader("User-Agent");
            String encodedfileName = null;
            if (null != agent) {
                agent = agent.toLowerCase();
                if (agent.contains("firefox") || agent.contains("chrome") || agent.contains("safari")) {
                    encodedfileName = "filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"";
                } else if (agent.contains("msie")) {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                } else if (agent.contains("opera")) {
                    encodedfileName = "filename*=UTF-8\"" + fileName + "\"";
                } else {
                    encodedfileName = "filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"";
                }
            }
            response.setHeader(CONTENT_DISPOSITION, "attachment; " + encodedfileName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
     * 返回的结果的Parameter名已去除前缀
     *
     * @param request
     * @param prefix
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement().toString();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {

                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    /**
     * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
     *
     * @param params
     * @param prefix
     * @return
     */
    public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
        if (params == null || params.size() == 0) {
            return "";
        }
        if (prefix == null) {
            prefix = "";
        }
        StringBuilder queryStringBuilder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            queryStringBuilder.append(prefix).append(entry.getKey()).append("=").append(entry.getValue());
            if (it.hasNext()) {
                queryStringBuilder.append("&");
            }
        }
        return queryStringBuilder.toString();
    }
}
