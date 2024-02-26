package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.mapper.LinkMapper;
import com.lut.pojo.entity.Link;
import com.lut.pojo.vo.LinkVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.LinkService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author qianye
 * @since 2023-10-26 12:01:01
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    /**
     * 获取友链列表
     * @return
     */
    @Override
    public Result getAllLink() {

        //只查询审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);

        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(links, LinkVO.class);
        return Result.okResult(linkVOS);
    }

    @Override
    public Result<PageVO> pageLink(Integer pageNum, Integer pageSize, String name, String status) {
        Page page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name), Link::getName, name);
        queryWrapper.eq(StringUtils.hasText(status), Link::getStatus, status);

        page(page, queryWrapper);
        return Result.okResult(new PageVO(page.getRecords(), page.getTotal()));
    }

    @Override
    public Result deleteBatch(Long[] ids) {
        int delete = 0;
        if(ids.length > 0) {
            List<Long> idList = (List<Long>) CollectionUtils.arrayToList(ids);
            delete = linkMapper.deleteBatchIds(idList);
        }

        return delete > 0? Result.okResult() : Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
