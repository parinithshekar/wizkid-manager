package dev.owow.wizkidmanager2000.utils;

import org.springframework.http.HttpHeaders;

public class CommonUtils {

    public static HttpHeaders getCorsHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:3000");
        return httpHeaders;
    }
}
