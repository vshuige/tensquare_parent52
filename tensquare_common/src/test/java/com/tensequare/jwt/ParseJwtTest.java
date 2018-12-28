package com.tensequare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;

/**
 * @Author: liangzhihui
 * @Date: 2018/12/21
 */
public class ParseJwtTest {

    public static void main(String[] args) {
        Claims claims = Jwts.parser().setSigningKey("itcast")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pqawiLCJpYXQiOjE1NDU2MTYyMDIsImV4cCI6MTU0NTYxNjI2Miwicm9sZSI6ImFkbWluIn0.4N-pd49slIpoy-7uEdgq7oxhi5O2Zh5AmtSK2pOTDkE")
                .getBody();
        System.out.println("用户ID：" + claims.getId());
        System.out.println("用户名：" + claims.getSubject());
        System.out.println("登录时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getIssuedAt()));
        System.out.println("过期时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(claims.getExpiration()));
        System.out.println("用户角色：" + claims.get("role"));
    }
}
