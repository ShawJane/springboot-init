package com.wsj.springbootinit.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsj.springbootinit.model.vo.SmmsVO;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SmmsManager {

    public static final String url = "https://smms.app/api/v2";
    @Value("${smms.token}")
    private String token;

    public SmmsVO uploadImg(File file) {
        return doUpload(file);
    }

    public SmmsVO uploadImg(String filePath) {
        File file = new File(filePath);
        return doUpload(file);
    }

    public SmmsVO deleteImg(String imgHash) {
        // 使用unirest
        HttpResponse<String> response = Unirest.get(url + "/delete/" + imgHash)
                .header("Authorization", token)
                .asString();
        // Json转map
        Map<String, Object> mapSource = convertJsonToMap(response.getBody());
        SmmsVO smmsVO = new SmmsVO();
        smmsVO.setSuccess((Boolean) mapSource.get("success"));
        smmsVO.setMessage((String) mapSource.get("message"));
        return smmsVO;
    }

    private SmmsVO doUpload(File file) {
        // 构造 Header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", token);
        // 构造 params
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("smfile", file);
        // 发送
        HttpResponse<String> response = Unirest.post(url + "/upload")
                .header("Authorization", token)
                .field("smfile", file)
                .asString();
        // Json转map
        Map<String, Object> mapSource = convertJsonToMap(response.getBody());
        // 根据是否上传成功来构造返回值
        SmmsVO smmsVO = new SmmsVO();
        if ((Boolean) mapSource.get("success")) {
            Map<String, Object> mapData = (Map<String, Object>) mapSource.get("data");
            smmsVO.setSuccess(true);
            smmsVO.setUrl((String) mapData.get("url"));
            smmsVO.setHash((String) mapData.get("hash"));
            return smmsVO;
        } else {
            smmsVO.setSuccess(false);
            smmsVO.setUrl((String) mapSource.get("images"));
            smmsVO.setMessage((String) mapSource.get("code"));
            return smmsVO;
        }
    }

    private Map<String, Object> convertJsonToMap(String json) {
        // 使用TypeToken来保留Map<String, Object>的类型信息
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        // 将JSON字符串转换为Map
        return new Gson().fromJson(json, type);
    }
}