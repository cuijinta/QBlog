package com.lut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.mapper.LinkMapper;
import com.lut.pojo.entity.Link;
import com.lut.service.LinkService;
import org.springframework.stereotype.Service;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-10-26 12:01:01
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

}
