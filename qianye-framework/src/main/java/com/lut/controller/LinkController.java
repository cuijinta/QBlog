package com.lut.controller;

import com.lut.result.Result;
import com.lut.service.LinkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 浅夜
 * @Description 友链控制器
 * @DateTime 2023/10/26 11:58
 **/
@Api(tags = "友链相关")
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    /**
     * 获取所有友链
     * @return
     */
    @GetMapping("/getAllLink")
    public Result getAllLink() {
        return linkService.getAllLink();
    }
}