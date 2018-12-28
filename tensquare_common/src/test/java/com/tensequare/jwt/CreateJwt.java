package com.tensequare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/21
 */
public class CreateJwt {

    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("666")
                .setSubject("小马")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .setExpiration(new Date(new Date().getTime()+60000))  // token过期时间设置（毫秒）
                .claim("role","admin");
        System.out.println(jwtBuilder.compact());
    }
}
