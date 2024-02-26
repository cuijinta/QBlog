package com.lut.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lut.pojo.entity.Link;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-10-26 12:01:01
 */
public interface LinkService extends IService<Link> {

    /**
     * 获取友链列表
     * @return
     */
    Result getAllLink();

    Result<PageVO> pageLink(Integer pageNum, Integer pageSize, String name, String status);

    Result deleteBatch(Long[] ids);
}
