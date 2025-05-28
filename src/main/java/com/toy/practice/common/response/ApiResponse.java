package com.toy.practice.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse <T>{
    private final boolean success;
    private final T data;
    private final String message;

    public static <T> ApiResponse<T> success (T data){
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> success (T data, String message){
        return new ApiResponse<>(true,data,message);
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(true, data, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, null, message);
    }

    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(true, null, null);
    }

    public static <T> ApiResponse<T> ok(String message) {
        return new ApiResponse<>(true, null, message);
    }
}
