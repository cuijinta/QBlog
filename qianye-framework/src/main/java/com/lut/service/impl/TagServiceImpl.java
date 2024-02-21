package com.lut.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.mapper.TagMapper;
import com.lut.pojo.entity.Tag;
import com.lut.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author qianye
 * @since 2024-02-21 11:14:48
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

