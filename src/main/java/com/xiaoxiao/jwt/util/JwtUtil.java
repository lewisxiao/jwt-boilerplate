package com.xiaoxiao.jwt.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaoxiao.jwt.constant.Constant;
import com.xiaoxiao.jwt.properties.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties) {
        JwtUtil.jwtProperties = jwtProperties;
    }

    /**
     * 生成token
     * @param username
     * @return
     */
    public static String sign(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            Date expireDate = new Date(System.currentTimeMillis() + jwtProperties.getExpiration());
            String token = JWT.create()
                    .withClaim(Constant.USERNAME, username)
                    .withIssuer(jwtProperties.getIssuer())
                    .withSubject(jwtProperties.getSubject())
                    .withAudience(jwtProperties.getAudience())
                    .withExpiresAt(expireDate)
                    .withIssuedAt(jwtProperties.getIssuedAt())
                    .sign(algorithm);

            return token;
        } catch (Exception exception){
            log.error("create token exception: ", exception);
            //Invalid Signing configuration / Couldn't convert Claims.

            return null;
        }
    }

    /**
     * 验证token
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtProperties.getIssuer())
                    .withSubject(jwtProperties.getSubject())
                    .withAudience(jwtProperties.getAudience())
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            if (jwt != null) {
                return true;
            }
        } catch (Exception exception){
            //Invalid signature/claims
            log.error("verify token exception: ", exception);
        }

        return false;
    }

    public static String getUsername(String token) {
        if (StringUtils.isEmpty(token)){
            return null;
        }

        DecodedJWT decodedJwt = getJwt(token);
        if (decodedJwt == null) {
            return null;
        }

        String username = decodedJwt.getClaim(Constant.USERNAME).asString();
        return username;
    }

    private static DecodedJWT getJwt(String token) {
        return JWT.decode(token);
    }
}
