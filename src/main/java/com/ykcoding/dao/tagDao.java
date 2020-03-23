package com.ykcoding.dao;

import com.ykcoding.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface tagDao extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
