package com.purplecloud.chat.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.purplecloud.chat.bean.ResultData;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

public class ResponseUtils {
    public static void out(HttpServletResponse response, ResultData r){
        // 将ResultData对象转换为JSON字符串，并设置为响应体
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] responseBody = new byte[0];
        try {
            responseBody = objectMapper.writeValueAsBytes(r);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response.setStatus(511);
        response.setContentType("application/json");
        try {
            response.getWriter().write(Arrays.toString(responseBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
