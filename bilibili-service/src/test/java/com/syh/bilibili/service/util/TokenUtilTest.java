package com.syh.bilibili.service.util;

import com.syh.bilibili.domain.exception.ConditionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class TokenUtilTest {
    @Test
    public void testTokenGenerateAndVerify() throws Exception {
        Long userId = 2147483647L;

        String token = TokenUtil.generateToken(userId, 3600);
        System.out.println(token);
        Long userIdFromToken = TokenUtil.verifyToken(token);

        Assertions.assertEquals(userId, userIdFromToken);
    }

    @Test
    public void testTokenGenerateAndVerifyExpired() throws Exception {
        Long userId = 2147483647L;

        String token = TokenUtil.generateToken(userId, 30);

        TimeUnit.MINUTES.sleep(1);
        Assertions.assertThrows(ConditionException.class, () -> TokenUtil.verifyToken(token));
    }
}
