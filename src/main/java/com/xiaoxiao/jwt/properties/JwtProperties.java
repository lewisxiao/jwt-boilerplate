package com.xiaoxiao.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@ConfigurationProperties(prefix = "config.jwt")
@Component
@Data
public class JwtProperties {
    /**
     * iss: 签发人
     */
    private String issuer;

    /**
     * sub: 主题
     */
    private String subject;

    /**
     * aud: 受众
     */
    private String audience;

    /**
     * exp: 过期时间, 单位：毫秒
     */
    private Long expiration;

    /**
     * nbf: 生效时间
     */
    private Date notBefore = new Date();

    /**
     * iat: 签发时间
     */
    private Date issuedAt = new Date();

    private String secret;
}
