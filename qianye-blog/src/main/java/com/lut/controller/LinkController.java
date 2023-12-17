package com.lut.controller;

import com.lut.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 浅夜
 * @Description TODO
 * @DateTime 2023/10/26 11:58
 **/
@RestController
@RequestMapping("/link")
public class LinkController {

    @GetMapping("/getAllLink")
    public Result getAllLink() {
        return null;
    }
}
