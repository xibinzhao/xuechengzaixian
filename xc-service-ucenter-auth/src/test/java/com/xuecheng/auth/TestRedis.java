package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test() {
        String key = "user_token:9734b68f‐cf5e‐456f‐9bd6‐df578c711390";
        String value = "hhh";
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "1111");
        map.put("name", value);
        String json = JSON.toJSONString(map);
        stringRedisTemplate.boundValueOps(key).set(json, 60, TimeUnit.SECONDS);
        String hhhh = stringRedisTemplate.opsForValue().get(key);
        Long expire = stringRedisTemplate.getExpire(key);
        System.out.println(expire);
        System.out.println(hhhh);
    }
}
