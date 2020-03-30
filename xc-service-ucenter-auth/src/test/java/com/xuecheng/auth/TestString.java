package com.xuecheng.auth;

import org.junit.Test;

public class TestString {
    @Test
    public void test() {
        String a = "UserDetailsService returned null, which is an interface contract violation";
        int c = a.indexOf("UserDetailsService returned null");
        System.out.println(c);
    }
}
