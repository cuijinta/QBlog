package com.lut.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lut.constant.AppHttpCodeEnum;
import com.lut.constant.SystemConstants;
import com.lut.exception.GlobalException;
import com.lut.mapper.CommentMapper;
import com.lut.pojo.entity.Comment;
import com.lut.pojo.vo.CommentVO;
import com.lut.pojo.vo.PageVO;
import com.lut.result.Result;
import com.lut.service.CommentService;
import com.lut.service.UserService;
import com.lut.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author qianye
 * @since 2024-02-15 23:49:14
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    /**
     * 分页获取评论列表
     *
     * @param commentType 评论类型
     * @param articleId 评论所属文章id
     * @param pageNum   当前页数
     * @param pageSize  每页条数
     * @return 分页列表
     */
    @Override
    public Result commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //根据文章id查询根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId)
                .eq(Comment::getType, commentType)
                .orderByDesc(Comment::getCreateTime)
                .eq(Comment::getRootId, -1);
        //分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVO> commentVOList = toCommentVOList(page.getRecords());
        //查询所有子评论的集合
        if(!CollectionUtils.isEmpty(commentVOList)) {
            commentVOList.stream().map(commentVO -> {
                List<CommentVO> children = getChildren(commentVO.getId());
                commentVO.setChildren(children);
                return commentVO;
            }).collect(Collectors.toList());
        }

        return Result.okResult(new PageVO(commentVOList, page.getTotal()));
    }

    /**
     * 发送评论
     * @param comment 评论对象
     * @return 发送成功
     */
    @Override
    public Result addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new GlobalException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return Result.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     *
     * @param id 根评论的id
     * @return
     */
    private List<CommentVO> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        List<CommentVO> commentVOList = toCommentVOList(comments);
        return commentVOList;
    }

    private List<CommentVO> toCommentVOList(List<Comment> list) {
        List<CommentVO> commentVOList = BeanCopyUtils.copyBeanList(list, CommentVO.class);

        if(CollectionUtils.isEmpty(list)) return null;
        //遍历vo集合，通过createBy查询用户的昵称并赋值
        commentVOList.stream().map(commentVO -> {
            //通过createBy查询用户昵称
            if(userService.getById(commentVO.getCreateBy()) == null) return null;
            String nickName = userService.getById(commentVO.getCreateBy()).getNickName();
            commentVO.setUsername(nickName);
            //如果toCommentUserId不为-1时才进行查询
            if (commentVO.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVO.getToCommentUserId()).getNickName();
                commentVO.setToCommentUserName(toCommentUserName);
            }
            return commentVO;
        }).collect(Collectors.toList());

        return commentVOList;
    }
}
