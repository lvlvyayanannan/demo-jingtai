package com.kgc.demo.controller;

import com.kgc.demo.Model.Layer;
import com.kgc.demo.service.LayerService;
import com.kgc.demo.util.Api;
import com.kgc.demo.util.FtpFileUtil;
import com.kgc.demo.util.String2date;
import com.kgc.demo.util.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName ftpController
 * @Description TODO
 * @Author yaozhaobao
 * @Date 2019/6/15 14:40
 * @Version 1.0
 **/
@RestController
@RequestMapping("/layer")
public class ftpController {
    @Autowired
    private LayerService layerService;

    Upload upload = new Upload();

    Api api = new Api();

    public static final String RECORD_TIME="2018";


    @RequestMapping("/upload")
    public Map upload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Map map = new HashMap();
        try {
            //上传目录地址
//            String uploadDir = request.getSession().getServletContext().getRealPath("upload");
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            System.out.println(uploadDir);
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //调用上传方法
            String fileName = upload.executeUpload(uploadDir, file);
           // uploadDir = uploadDir.substring(0, uploadDir.length() - 1);


            map.put("fileName", fileName);
            map.put("dir", uploadDir);
            map.put("code",1);
            map.put("msg","上传成功");

        } catch (Exception e) {
            map.put("code",2);
            map.put("msg","上传失败");
            e.printStackTrace();
        }
        return  map;
    }


    @RequestMapping("/add_layer")
    public Map<String,Object> add_layer(HttpServletRequest req){
        String fileName = req.getParameter("fileName");
        String layerName=req.getParameter("layerName");
        String description=req.getParameter("description");//有注解，默认转换
        String strReleaseTime=req.getParameter("releaseTime");
        if (layerName==null){
            return api.returnJson(3,"warning");
        }
        UUID uuid=UUID.randomUUID();
        String layerId=uuid.toString();
        Layer layer=new Layer();
        Date releaseTime= String2date.DateChange(strReleaseTime);
        Date recordTime=String2date.DateChange(RECORD_TIME);
        layer.setLayerId(layerId);
        layer.setLayerName(layerName);
        layer.setFileName(fileName);
        layer.setReleaseTime(releaseTime);
        layer.setDescription(description);
        layer.setRecordTime(recordTime);
        int is_add=layerService.InsertLayer(layer);
        if (is_add!=0){
            return api.returnJson(1,"添加成功");
        }else{
            return api.returnJson(2,"添加失败");
        }
    }





}
