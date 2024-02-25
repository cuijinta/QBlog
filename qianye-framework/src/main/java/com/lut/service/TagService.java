package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.dto.TagListDto;
import com.lut.pojo.entity.Tag;
import com.lut.pojo.vo.PageVO;
import com.lut.pojo.vo.TagVO;
import com.lut.result.Result;

/**
 * 标签(Tag)表服务接口
 *
 * @author qianye
 * @since 2024-02-21 11:14:48
 */
public interface TagService extends IService<Tag> {

    /**
     * 分页获取标签
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param tagListDto 标签名、标签备注
     * @return 分页实体
     */
    Result<PageVO> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 新增标签
     * @param name 标签名
     * @param remark 标签备注
     * @return 新增结果
     */
    Result saveTag(String name, String remark);

    /**
     * 根据id获取标签
     * @param id  标签id
     * @return 标签响应对象
     */
    Result<TagVO> getTag(Long id);

    /**
     * 删除标签
     * @param id 标签id
     * @return 删除结果
     */
    Result deleteById(Long[] id);

    /**
     * 修改标签
     * @param id  标签id
     * @return 修改后的标签响应对象
     */
    Result updateTag(TagVO id);

    /**
     * 列出所有的标签
     * @return
     */
    Result getAllTag();
}

