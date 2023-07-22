package com.syh.bilibili.service.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.syh.bilibili.domain.exception.ConditionException;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {
    private static final String ISSUER = "syh";

    public static String generateToken(Long userId, int ttl) {
        if (userId == null || ttl < 0) {
            throw new ConditionException("500", "Error when generating user token");
        }

        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.SECOND, ttl);

            return JWT.create()
                    .withKeyId(String.valueOf(userId))
                    .withIssuer(ISSUER)
                    .withExpiresAt(calendar.getTime())
                    .sign(algorithm);
        } catch (Exception exception) {
            throw new ConditionException("500", "Internal error");
        }
    }

    public static Long verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(RSAUtil.getPublicKey(), RSAUtil.getPrivateKey());

            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            return Long.valueOf(jwt.getKeyId());
        } catch (TokenExpiredException exception) {
            throw new ConditionException("555", "Token expired");
        } catch (JWTVerificationException exception) {
            throw new ConditionException("500", "Invalid user token");
        } catch (Exception exception) {
            throw new ConditionException("500", "Internal error");
        }
    }
}
