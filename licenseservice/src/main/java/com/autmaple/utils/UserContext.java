package com.autmaple.utils;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserContext {
    public static final String CORRELATION_ID = "TMX-CORRELATION-ID";
    public static final String AUTH_TOKEN = "TMX-AUTH-TOKEN";
    public static final String USER_ID = "TMX-USER-ID";
    public static final String ORGANIZATION_ID = "TMX-ORGANIZATION-ID";

    private String correlationId = "";
    private String authToken = "";
    private String userId = "";
    private String organizationId = "";
}
