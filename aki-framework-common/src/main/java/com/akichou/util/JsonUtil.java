package com.akichou.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json Utility
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> String convertToJsonString(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}