package com.xuecheng.auth;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;
import springfox.documentation.spring.web.json.Json;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestJwt {
    @Test
    public void test() {
        //密钥路径
        String jwtKeyStore = "xc.keystore";
        //密钥别名
        String alias = "xckey";
        //密钥库访问密码
        String storePass = "xuechengkeystore";
        //密钥访问密码
        String keypass = "xuecheng";
        //设置获取路径对线
        ClassPathResource classPathResource = new ClassPathResource(jwtKeyStore);
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, storePass.toCharArray());
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias, keypass.toCharArray());
        RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "goudan");
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(map), new RsaSigner(aPrivate));
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    @Test
    public void test2() {
        String public_key = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
        String encoded = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55SWQiOiIxIiwidXNlcnBpYyI6bnVsbCwidXNlcl9uYW1lIjoiaXRjYXN0Iiwic2NvcGUiOlsiYXBwIl0sIm5hbWUiOiJ0ZXN0MDIiLCJ1dHlwZSI6IjEwMTAwMiIsImlkIjoiNDkiLCJleHAiOjE1NzQ5NTI0OTcsImF1dGhvcml0aWVzIjpbInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYmFzZSIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfZGVsIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9saXN0IiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZV9wbGFuIiwieGNfdGVhY2htYW5hZ2VyX2NvdXJzZSIsImNvdXJzZV9maW5kX2xpc3QiLCJ4Y190ZWFjaG1hbmFnZXIiLCJ4Y190ZWFjaG1hbmFnZXJfY291cnNlX21hcmtldCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfcHVibGlzaCIsInhjX3RlYWNobWFuYWdlcl9jb3Vyc2VfYWRkIl0sImp0aSI6IjI5YjhhZTA4LTYzNTUtNGNlZC1iNDEwLTVjYTFhNjI2MmRjOCIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.B_-OqM_igIS5Bv9bVNeVjsyF8IxcSkV8-bJ7wenhw2TOH0csQa3KiFw87nPaTxW7HrclVD4bzjUAa8qcP3jSA5Q3T6S66Y1YynuFXq1Rwjq0o_Fi94xkZEVknZjDID_GMtmzwMBfQIN5JsbaFSIS2upAWNnr2rNnueXzx_c8sXWF63XPv-n60xwbGdzkiRQI2Kp8AFI1CfLoqZJY3ScNmC-tVm2Ow--Ay3niv_9H__1W8dofV6qT9EWnv9S__50GgPCT7FxdOzfl-kzpvHq1n-FOhzBEMF0vjySZQtXtep2odbQSlZeNZsmXjtNkbC1iq_86XQqkxcLoBNnZsFV2Fg";
        Jwt jwt = JwtHelper.decodeAndVerify(encoded, new RsaVerifier(public_key));
        String claims = jwt.getClaims();
        System.out.println(claims);
    }

    @Test
    public void test3() {

    }
}
