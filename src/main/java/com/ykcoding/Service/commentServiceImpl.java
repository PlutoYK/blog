package com.ykcoding.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ykcoding.dao.commentDao;
import com.ykcoding.pojo.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class commentServiceImpl implements commentService {
    @Autowired
    private commentDao commentdao;
    @Override
    public List<Comment> ListCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");

        return commentdao.findByBlogIdAndParentCommentNull(blogId,sort);
    }

    @Override
    public Comment save(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if(parentCommentId!=-1){
            comment.setParentComment(commentdao.getOne(parentCommentId));

        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentdao.save(comment);
    }
    /**
     *用于临时存放评论集合
     */
    private List<Comment> tempReply = new ArrayList<>();

    /**
     *循环遍历顶级评论
     * @param comments
     *
     */
    private List<Comment> rootComment(List<Comment> comments){
            List<Comment> list = new ArrayList<>();
            for(Comment comment:comments) {
                Comment c = new Comment();
                BeanUtils.copyProperties(comment, c);
                list.add(c);
            }
            //合并所有子孙节点评论到顶级评论下的集合中
            combineChildren(list);
            return list;



    }

    /**
     * 将所有顶级评论下的子孙级评论转换成子级评论
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for(Comment comment:comments){
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply:replys){
                //循环迭代，找到所有子孙节点级评论
                recursively(reply);
            }
            comment.setReplyComments(tempReply);
            //重置临时存放变量
            tempReply = new ArrayList<>();

        }
    }

    private void recursively(Comment comment) {
        tempReply.add(comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReply.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
