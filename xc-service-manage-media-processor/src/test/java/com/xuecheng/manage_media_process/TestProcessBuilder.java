package com.xuecheng.manage_media_process;

import com.xuecheng.framework.utils.Mp4VideoUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author Administrator
 * @version 1.0
 * @create 2018-07-12 9:11
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProcessBuilder {
    @Test
    public void testProsccBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("ping", "127.0.0.1");
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            String s = IOUtils.toString(inputStream, "gbk");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProsccBuilder2() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("ping","127.0.0.1");
        processBuilder.command("ipconfig", "127.0.0.1");
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            String s = IOUtils.toString(inputStream, "gbk");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFFmpeg() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("ping","127.0.0.1");
        ArrayList<String> command = new ArrayList<>();
        command.add("E:\\Theplug-in\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe");
        command.add("-i");
        command.add("E:\\xc_video\\lucene.avi");
        command.add("-y"); //覆盖输出文件
        command.add("-c:v");
        command.add("libx264");
        command.add("-s");
        command.add("1280x720");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-b:a");
        command.add("63k");
        command.add("-b:v");
        command.add("753k");
        command.add("-r");
        command.add("18");
        command.add("E:\\xc_video\\lucene.mp4");
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            String s = IOUtils.toString(inputStream, "gbk");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFFmpeg2() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("ping","127.0.0.1");
        ArrayList<String> command = new ArrayList<>();
        command.add("E:\\Theplug-in\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe");
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            String s = IOUtils.toString(inputStream, "gbk");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFFmpeg3() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //processBuilder.command("ping","127.0.0.1");
        ArrayList<String> command = new ArrayList<>();
        command.add("E:\\Theplug-in\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe");
        command.add("-i");
        command.add("E:\\xc_video\\lucene.mp4");
        command.add("-hls_time");
        command.add("10");
        command.add("-hls_list_size");
        command.add("0");
        command.add("-hls_segment_filename");
        command.add("E:\\xc_video\\hls\\lucene_%05d.ts");
        command.add("E:\\xc_video\\hls\\lucene.m3u8");
        processBuilder.command(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process start = processBuilder.start();
            InputStream inputStream = start.getInputStream();
            String s = IOUtils.toString(inputStream, "gbk");
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
