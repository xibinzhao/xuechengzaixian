package com.xuecheng.order;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    @Test
    public void test() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        long time = date.getTime();
        System.out.println(time);
        time = time + 60;
        System.out.println(time);
        Date date1 = new Date(new Date().getTime() + 60 * 1000);
        String format2 = simpleDateFormat.format(date1);
        System.out.println(format);
        System.out.println(format2);
    }
}
