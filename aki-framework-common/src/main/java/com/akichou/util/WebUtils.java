package com.akichou.util;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Web Utility
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
@Slf4j
public class WebUtils {
    /**
     * Render 渲染 string to client side
     *
     * @param response render object
     * @param string rendered string
     */
    public static void renderString(HttpServletResponse response, String string) {

        try {

            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e) {

            log.error(e.getMessage());
        }
    }

    public static void setDownLoadHeader(String filename, HttpServletResponse response) {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        String fName = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("\\+", "%20") ;
        response.setHeader("Content-disposition","attachment; filename=" + fName);
    }
}