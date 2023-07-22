package com.syh.bilibili.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RSAUtilTest {
    @Test
    public void testEncrypt() throws Exception {
        String password = "TR";
        String encryptedPassword = RSAUtil.encrypt(password);
        System.out.println(encryptedPassword);
        Assertions.assertEquals(password, RSAUtil.decrypt(encryptedPassword));
    }
}
