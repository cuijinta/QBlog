package com.lut.controller;

import com.lut.constant.AppHttpCodeEnum;
import com.lut.pojo.entity.Link;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.LinkService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

/**
 * @Author 浅夜
 * @Description 友链控制器
 * @DateTime 2023/10/26 11:58
 **/
@Api(tags = "友链相关")
@RestController
@RequestMapping
public class LinkController {
    @Autowired
    private LinkService linkService;

    /**
     * 获取所有友链
     * @return
     */
    @GetMapping("/link/getAllLink")
    public Result getAllLink() {
        return linkService.getAllLink();
    }

    @GetMapping("/content/link/list")
    public Result<PageVO> page(Integer pageNum, Integer pageSize, String name, String status) {
        return linkService.pageLink(pageNum, pageSize, name, status);
    }

    @PostMapping("/content/link")
    public Result save(@RequestBody Link link) {
         linkService.save(link);
         return Result.okResult();
    }

    @GetMapping("content/link/{id}")
    public Result getInfo(@PathVariable Long id) {
        Link link = linkService.getById(id);
        return Result.okResult(link);
    }

    @PutMapping("/content/link")
    public Result update(@RequestBody Link link) {
        boolean update = linkService.updateById(link);
        return update ? Result.okResult() : Result.errorResult(AppHttpCodeEnum.OPERATION_ERROR);
    }

    @DeleteMapping("/content/link/{ids}")
    public Result delete(@PathVariable Long[] ids) {
        return linkService.deleteBatch(ids);

    }

}
