package com.lcy.demo.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Objects;

/**
 * @description:
 * @author: luchenyang
 * @date: 2022/8/24
 */

@WebFilter(filterName = "CharsetFilter", urlPatterns = "/*")
@Log4j2
public class ReqLogFilter extends OncePerRequestFilter {

    //OncePerRequestFilter  只执行一次过滤器

    /**
     * 排除上传文件类请求
     */
    private static final String IGNORE_CONTENT_TYPE = "multipart/form-data";

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper requset = new ContentCachingRequestWrapper(servletRequest);
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(servletResponse);
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        try {
            filterChain.doFilter(requset, response);
            status = servletResponse.getStatus();
        } finally {
            if (!Objects.equals(IGNORE_CONTENT_TYPE, servletRequest.getContentType())) {

                WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletRequest.getServletContext());

                //请求参数
                StringBuffer requestParams = new StringBuffer();
                if ("POST".equals(servletRequest.getMethod())) {
                    String params = getRequestBody(requset);
                    requestParams.append(params);
                } else {
                    String queryString = servletRequest.getQueryString();
                    requestParams.append(queryString);
                }

                //响应结果,如果没有统一返回格式，此处需要改写
                RequestLog eventLog = new RequestLog();
                eventLog.setUrl(servletRequest.getRequestURI());
                eventLog.setCreateTime(new Date());

                try {

                    RequestModel requestModel = JSON.parseObject(requestParams.toString(), RequestModel.class);
                    eventLog.setCustomerCd(String.valueOf(requestModel.getCompanyCode()));
                    eventLog.setOrginReqData(JSON.toJSONString(requestModel));

                    if (response.getContentType().contains("json")) {
                        String result = getResponseBody(response);
                        ResponseModel responseModel = JSON.parseObject(result, ResponseModel.class);
                        eventLog.setResStatus(responseModel.getCode());
                    } else {
                        eventLog.setResStatus(ResponseModel.successCode);
                    }

//                    RequestLogMapper bean = ctx.getBean(RequestLogMapper.class);
//                    bean.insert(eventLog);
                } catch (Exception e) {
                    log.error("转换请求对象失败", e);
                }

            }
            response.copyBodyToResponse();
        }

    }


    private String getRequestBody(ContentCachingRequestWrapper wrapper) throws IOException {
        String requestBody = null;
        if (wrapper != null) {
            byte[] contentAsByteArray = wrapper.getContentAsByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentAsByteArray);
            requestBody = IOUtils.toString(byteArrayInputStream, Charset.forName("UTF-8"));
        }
        return requestBody;
    }

    private String getResponseBody(ContentCachingResponseWrapper wrapper) throws IOException {
        String responseBody = null;
        if (wrapper != null) {
            byte[] contentAsByteArray = wrapper.getContentAsByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentAsByteArray);
            responseBody = IOUtils.toString(byteArrayInputStream, Charset.forName("UTF-8"));
        }
        return responseBody;
    }


}