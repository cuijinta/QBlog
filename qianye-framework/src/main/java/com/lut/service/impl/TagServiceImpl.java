package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.exception.GlobalException;
import com.lut.mapper.TagMapper;
import com.lut.pojo.dto.TagListDto;
import com.lut.pojo.entity.Tag;
import com.lut.pojo.vo.PageVO;
import com.lut.pojo.vo.TagVO;
import com.lut.result.Result;
import com.lut.service.TagService;
import com.lut.utils.BeanCopyUtils;
import com.lut.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author qianye
 * @since 2024-02-21 11:14:48
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;

    /**
     * 分页获取标签
     * @param pageNum 当前页数
     * @param pageSize 每页条数
     * @param tagListDto 标签名、标签备注
     * @return 分页对象
     */
    @Override
    public Result<PageVO> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVO pageVo = new PageVO(page.getRecords(),page.getTotal());
        return Result.okResult(pageVo);
    }

    /**
     * 新增标签
     * @param name 标签名
     * @param remark 标签备注
     * @return 新增结果
     */
    @Override
    public Result saveTag(String name, String remark) {
        Long userId = SecurityUtils.getUserId();
        if(!StringUtils.hasText(name) && !StringUtils.hasText(remark)) {
            throw new GlobalException(AppHttpCodeEnum.PARAM_LOST);
        }
        Tag tag = new Tag();
        tag.setCreateBy(userId);
        tag.setUpdateBy(userId);
        tag.setName(name);
        tag.setRemark(remark);
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        boolean save = save(tag);
        return save? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 根据id获取标签
     * @param id  标签id
     * @return 标签响应对象
     */
    @Override
    public Result<TagVO> getTag(Long id) {
        Tag tag = getById(id);
        if(ObjectUtils.isNull(tag) || tag.getDelFlag() == 1) {
            throw new GlobalException(AppHttpCodeEnum.OPERATION_ERROR);
        }
        TagVO tagVO = BeanCopyUtils.copyBean(tag, TagVO.class);
        return Result.okResult(tagVO);
    }

    /**
     * 删除标签
     * @param id 标签id
     * @return 删除结果
     */
    @Override
    public Result deleteById(Long[] id) {
//        Long userId = SecurityUtils.getUserId();
        int delete = tagMapper.deleteBatchIds(Arrays.asList(id));
//        Tag tag = getById(id);
//        tag.setDelFlag(1);
//        tag.setUpdateBy(userId);
//        tag.setUpdateTime(new Date());
//        int delete = tagMapper.deleteById(tag);
        return delete == 0 ? Result.errorResult(500,"删除失败") : Result.okResult();
    }

    /**
     * 修改标签
     * @param tagVO  标签实体
     * @return 修改后的标签响应对象
     */
    @Override
    public Result updateTag(TagVO tagVO) {
        Long userId = SecurityUtils.getUserId();
        Tag tag = getById(tagVO.getId());
        if(ObjectUtils.isNull(tag) || tag.getDelFlag() == 1) {
            throw new GlobalException(AppHttpCodeEnum.OPERATION_ERROR);
        }
        tag.setUpdateBy(userId);
        tag.setUpdateTime(new Date());
        tag.setName(tagVO.getName());
        tag.setRemark(tagVO.getRemark());
        boolean update = updateById(tag);
        return update ? Result.okResult() : Result.errorResult(500, "更新失败");
    }

    /**
     * 列出所有的标签
     * @return
     */
    @Override
    public Result getAllTag() {
        List<Tag> tags = list();
        if(CollectionUtils.isEmpty(tags)) return Result.okResult();
        List<TagVO> tagVOList = BeanCopyUtils.copyBeanList(tags, TagVO.class);
        return Result.okResult(tagVOList);
    }
}

