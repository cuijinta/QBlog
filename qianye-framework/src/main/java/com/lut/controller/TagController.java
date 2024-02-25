package com.lut.controller;

import com.lut.pojo.dto.TagListDto;
import com.lut.pojo.vo.PageVO;
import com.lut.pojo.vo.TagVO;
import com.lut.result.Result;
import com.lut.service.TagService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 标签(Tag)表控制层
 *
 * @author qianye
 * @since 2024-02-21 11:14:46
 */

@RestController
@RequestMapping("/content/tag")
@Api(tags = "文章标签相关")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 分页获取标签
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param tagListDto 标签名、标签备注
     * @return 分页对象
     */
    @GetMapping("/list")
    public Result<PageVO> list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    /**
     * 新增标签
     * @param tagListDto 标签实体
     * @return 新增结果
     */
    @PostMapping
    public Result save(@RequestBody TagListDto tagListDto) {
        return tagService.saveTag(tagListDto.getName(), tagListDto.getRemark());
    }

    /**
     * 删除标签
     * @param id 标签id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long[] id) {
        return tagService.deleteById(id);
    }

    /**
     * 根据id获取标签
     * @param id  标签id
     * @return 标签响应对象
     */
    @GetMapping("/{id}")
    public Result<TagVO> getById(@PathVariable Long id) {
        return tagService.getTag(id);
    }

    /**
     * 修改标签
     * @param tagVO 请求对象
     * @return 修改后的标签响应对象
     */
    @PutMapping
    public Result update(@RequestBody TagVO tagVO) {
        return tagService.updateTag(tagVO);
    }

    /**
     * 列出所有的标签
     * @return
     */
    @GetMapping("/listAllTag")
    public Result listAllTag() {
        return tagService.getAllTag();
    }
}

