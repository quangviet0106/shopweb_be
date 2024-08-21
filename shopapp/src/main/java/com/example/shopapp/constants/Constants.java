package com.example.shopapp.constants;


import org.springframework.stereotype.Component;

@Component
public class Constants {

    public static final long JWT_EXPIRATION = 2592000;
    public static final String JWT_SECRETKEY = "15PXGKPEufQdmBn5P1HEXxQaHlsYfHMW6PRF7KsOqys=";

    public static final  String API_VERSION = "${spring.sendgrid.api-key}";
    public static  String ADMIN = "ADMIN";
    public static String USER = "USER";
}
