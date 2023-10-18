package com.wsj.springbootinit.controller;

import com.wsj.springbootinit.common.BaseResponse;
import com.wsj.springbootinit.common.ErrorCode;
import com.wsj.springbootinit.common.ResultUtils;
import com.wsj.springbootinit.exception.BusinessException;
import com.wsj.springbootinit.manager.SmmsManager;
import com.wsj.springbootinit.model.vo.SmmsVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

@RestController
@RequestMapping("/imgUpload")
public class ImgUploadController {
    @Resource
    private SmmsManager smmsManager;

    @PostMapping("/userAvatar")
    public BaseResponse<SmmsVO> uploadUserAvatar(@RequestPart("file") File file) {
        // 先删除图床中原有的图片再上传
        if (smmsManager.deleteImg("here_is_imgHash").getSuccess()) {
            SmmsVO smmsVO = smmsManager.uploadImg(file);
            //String url = smmsVO.getUrl();
            //String imgHash = smmsVO.getHash();
            return ResultUtils.success(smmsVO);
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新头像失败");
        }
    }

}
