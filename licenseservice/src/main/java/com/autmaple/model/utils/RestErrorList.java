package com.autmaple.model.utils;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;


@Getter
@Setter
public class RestErrorList extends ArrayList<ErrorMessage> {
    private HttpStatus status;

    public RestErrorList(HttpStatus status, ErrorMessage... errors) {
        this(status.value(), errors);
    }

    public RestErrorList(int status, ErrorMessage... errors) {
        this.status = HttpStatus.valueOf(status);
        addAll(Arrays.asList(errors));
    }
}
