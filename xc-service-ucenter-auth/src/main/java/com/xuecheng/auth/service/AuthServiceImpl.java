package com.xuecheng.auth.service;

import com.alibaba.fastjson.JSON;
import com.xuecheng.api.auth.service.AuthService;
import com.xuecheng.framework.client.XcServiceList;
import com.xuecheng.framework.domain.ucenter.ext.AuthToken;
import com.xuecheng.framework.domain.ucenter.request.LoginRequest;
import com.xuecheng.framework.domain.ucenter.response.AuthCode;
import com.xuecheng.framework.domain.ucenter.response.JwtResult;
import com.xuecheng.framework.domain.ucenter.response.LoginResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginResult login(LoginRequest loginRequest) {
        if (loginRequest == null) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        if (loginRequest.getUsername() == null) {
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        if (loginRequest.getPassword() == null) {
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }
        AuthToken auth = getAuth(loginRequest.getUsername(), loginRequest.getPassword());
        if (auth == null) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }
        boolean saveToken = saveToken(auth, 1200);
        if (!saveToken) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }
        return new LoginResult(CommonCode.SUCCESS, auth.getAccess_token());
    }

    @Override
    public ResponseResult logout(String jitKey) {
        delete(jitKey);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public JwtResult getUserJwt(String uid) {
        if (uid == null) {
            return new JwtResult(CommonCode.FAIL, null);
        }
        AuthToken token = getToken(uid);
        if (token != null) {
            String jwt_token = token.getJwt_token();
            return new JwtResult(CommonCode.SUCCESS, jwt_token);
        }
        return new JwtResult(CommonCode.FAIL, null);
    }

    private AuthToken getToken(String uid) {
        String key = "user_token:" + uid;
        String json = stringRedisTemplate.opsForValue().get(key);
        if (json != null) {
            AuthToken authToken = JSON.parseObject(json, AuthToken.class);
            return authToken;
        }
        return null;
    }

    private boolean saveToken(AuthToken authToken, long ttl) {
        String key = "user_token:" + authToken.getAccess_token();
        String value = JSON.toJSONString(authToken);
        stringRedisTemplate.boundValueOps(key).set(value, ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire > 0;
    }

    private AuthToken getAuth(String username, String password) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(XcServiceList.XC_SERVICE_UCENTER_AUTH);
        URI uri = serviceInstance.getUri();
        String httpPath = uri + "/auth/oauth/token";
        LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                //当响应的值为400或401时候也要正常响应，不要抛出异常
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        String basic = getBasic();
        headers.add("Authorization", basic);
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> body2 = restTemplate.exchange(httpPath, HttpMethod.POST, httpEntity, Map.class);
        Map httpMap = body2.getBody();
        if (httpMap == null
                || httpMap.get("access_token") == null
                || httpMap.get("refresh_token") == null
                || httpMap.get("jti") == null) {
            if (httpMap != null && httpMap.get("error_description") != null) {
                String error_description = (String) httpMap.get("error_description");
                if (error_description.indexOf("UserDetailsService returned null") != -1) {
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                } else if (error_description.indexOf("坏的凭证") != -1) {
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }
            return null;
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token((String) httpMap.get("jti"));
        authToken.setJwt_token((String) httpMap.get("access_token"));
        authToken.setRefresh_token((String) httpMap.get("refresh_token"));
        return authToken;

    }

    private String getBasic() {
        String basic = clientId + ":" + clientSecret;
        byte[] encode = Base64Utils.encode(basic.getBytes());
        return "Basic " + new String(encode);
    }

    private boolean delete(String jitKey) {
        String key = "user_token:" + jitKey;
        Boolean delete = stringRedisTemplate.delete(key);
        return delete;
    }
}
