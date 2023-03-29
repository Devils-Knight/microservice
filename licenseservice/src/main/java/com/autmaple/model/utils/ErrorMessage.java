package com.autmaple.model.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private String message;
    private String code;
    private String detail;

    public ErrorMessage(String message, String detail) {
        this(message, detail, "");
    }

    public ErrorMessage(String message) {
        this(message, "", "");
    }
}
