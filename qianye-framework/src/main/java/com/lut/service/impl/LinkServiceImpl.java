package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.SystemConstants;
import com.lut.mapper.LinkMapper;
import com.lut.pojo.entity.Link;
import com.lut.pojo.vo.LinkVO;
import com.lut.result.Result;
import com.lut.service.LinkService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author qianye
 * @since 2023-10-26 12:01:01
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

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
}
