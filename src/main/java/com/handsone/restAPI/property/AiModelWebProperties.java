package com.handsone.restAPI.property;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@Getter @Setter @Slf4j
@ConfigurationProperties(prefix = "ai")
public class AiModelWebProperties {
    private String scheme;
    private String host;
    private int port;
    protected String baseUrl;

    @PostConstruct
    public void init() {
        log.info("AiModel.scheme=[{}]", scheme);
        log.info("AiModel.host=[{}]", host);
        log.info("AiModel.port=[{}]", port);
        log.info("AiModel.baseUrl=[{}]", baseUrl);
    }
}
