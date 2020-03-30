package com.xuecheng.manage_media_process.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.media.MediaFile;
import com.xuecheng.framework.domain.media.MediaFileProcess_m3u8;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.utils.HlsVideoUtil;
import com.xuecheng.framework.utils.Mp4VideoUtil;
import com.xuecheng.manage_media_process.dao.MediaFileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class FFmpegLister {
    @Autowired
    private MediaFileRepository mediaFileRepository;
    @Value("${xc-service-manage-media.ffmpeg-path}")
    private String ffmpegPath;
    @Value("${xc-service-manage-media.video-location}")
    private String videoFileRoot;

    @RabbitListener(queues = {"${xc-service-manage-media.mq.queue-media-video-processor}"}, containerFactory = "customContainerFactory")
    public void parseFile(String msg) {
        //转换字符串为对象
        Map map = JSON.parseObject(msg, Map.class);
        String mediaId = (String) map.get("mediaId");
        if (mediaId == null || mediaId.equals("")) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        Optional<MediaFile> byId = mediaFileRepository.findById(mediaId);
        if (!byId.isPresent()) {
            return;
        }
        MediaFile mediaFile = byId.get();
        if (!mediaFile.getFileType().equals("avi")) {
            mediaFile.setProcessStatus("303004");
            mediaFileRepository.save(mediaFile);
            return;
        } else {
            mediaFile.setProcessStatus("303001");
            mediaFileRepository.save(mediaFile);
        }
        //String ffmpeg_path, String avi video_path, String mp4_name, String mp4folder_path
        //String ffmpeg_path, String mp4video_path, String m3u8_name,String m3u8folder_path
        String aviPath = videoFileRoot + mediaFile.getFilePath() + mediaFile.getFileName();
        String mp4Name = mediaFile.getFileId() + ".mp4";
        String mp4FileFolder = videoFileRoot + mediaFile.getFilePath();
        String mp4Path = mp4FileFolder + mp4Name;
        String m3u8Name = mediaFile.getFileId() + ".m3u8";
        String m3u8FolderPath = videoFileRoot + mediaFile.getFilePath() + "hls/";
        String fileUrl = mediaFile.getFilePath() + "hls/" + m3u8Name;
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpegPath, aviPath, mp4Name, mp4FileFolder);
        String result = mp4VideoUtil.generateMp4();
        if (!result.equals("success") || result == null) {
            mediaFile.setFileStatus("303003");
            mediaFile.setProcessStatus(result);
            mediaFileRepository.save(mediaFile);
            return;
        }
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpegPath, mp4Path, m3u8Name, m3u8FolderPath);
        String m3u8Result = hlsVideoUtil.generateM3u8();
        if (m3u8Result == null || !m3u8Result.equals("success")) {
            mediaFile.setFileStatus("303003");
            mediaFile.setProcessStatus(result);
            mediaFileRepository.save(mediaFile);
            return;
        }
        List<String> ts_list = hlsVideoUtil.get_ts_list();
        MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
        mediaFileProcess_m3u8.setTslist(ts_list);
        mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
        mediaFile.setFileStatus("303002");
        mediaFile.setProcessStatus("303002");
        mediaFile.setFileUrl(fileUrl);
        mediaFileRepository.save(mediaFile);
    }
}
