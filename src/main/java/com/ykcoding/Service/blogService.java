package com.ykcoding.Service;

import com.ykcoding.po.BlogQuery;
import com.ykcoding.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface blogService {

    public Blog getBlog(long id);
    public Blog saveBlog(Blog blog);
    public void deleteBlog(long id);
    public Blog updateBlog(Blog blog,long id);
    public Blog getBlogByName(String name);
    Page<Blog> listBlog(Long tagId,Pageable pageable);
    public Page<Blog> ListBlog(Pageable pageable, BlogQuery blog);
    public Page<Blog> ListBlog(Pageable pageable);
    public List<Blog> ListReCommendBlogTop(Integer size);
    public Page<Blog> query(Pageable pageable,String query);
    public Blog getAndconvert(Long id);

    public Map<String,List<Blog>> ArchiveBlog();
    public Long countBlog();

}
