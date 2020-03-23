package com.ykcoding.dao;

import com.ykcoding.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface commentDao extends JpaRepository<Comment,Long> {
    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);

}
