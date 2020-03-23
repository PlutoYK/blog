package com.ykcoding.Service;

import com.ykcoding.pojo.Comment;

import java.util.List;

public interface commentService {
    List<Comment> ListCommentByBlogId(Long blogId);
    Comment save(Comment comment);

}
