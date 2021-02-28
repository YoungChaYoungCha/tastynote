package com.youngchayoungcha.tastynote.config;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class FileConfig {

    public static final String uploadFileBasePath = "./src/main/resources/static/upload/";

    public static final String uploadFileBaseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/upload/";
}
