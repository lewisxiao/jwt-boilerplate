package com.xiaoxiao.jwt;

import com.xiaoxiao.jwt.util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        String token = JwtUtil.sign("levis");
        System.out.println(token);
        System.out.println(JwtUtil.getUsername(token));
    }
}
