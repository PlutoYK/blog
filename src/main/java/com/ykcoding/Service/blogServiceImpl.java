package com.ykcoding.Service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ykcoding.dao.blogDao;
import com.ykcoding.po.BlogQuery;
import com.ykcoding.pojo.Blog;
import com.ykcoding.pojo.Type;
import com.ykcoding.utils.MarkdownUtil;
import com.ykcoding.utils.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class blogServiceImpl implements blogService {
    @Autowired
    private blogDao blogdao;
    @Override
    public Blog getBlog(long id) {
        return blogdao.getOne(id);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogdao.save(blog);
    }
    @Transactional
    @Override
    public void deleteBlog(long id) {
        blogdao.deleteById(id);
    }
    @Transactional
    @Override
    public Blog updateBlog(Blog blog, long id) {
        Blog b = blogdao.getOne(id);
        if(b==null){

        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogdao.save(b);
    }

    @Override
    public Blog getBlogByName(String title) {
        return blogdao.findByTitle(title);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogdao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> ListBlog(Pageable pageable, BlogQuery blog) {
        return blogdao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicate.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicate.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }if(blog.isRecommend()){
                    predicate.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicate.toArray(new Predicate[predicate.size()]));

                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> ListBlog(Pageable pageable) {
        return blogdao.findAll(pageable);
    }

    @Override
    public List<Blog> ListReCommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);

        return blogdao.findRecommendTop(pageable);
    }

    @Override
    public Page<Blog> query(Pageable pageable, String query) {
        return blogdao.query(query,pageable);
    }
    @Transactional
    @Override
    public Blog getAndconvert(Long id) {
        Blog blog = blogdao.getOne(id);
        if(blog ==null){
        //抛出不存在异常
        }

        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();

        b.setContent(MarkdownUtil.markdownToHtmlExtensions(content));
        blogdao.updateView(id);
        return b;

    }

    @Override
    public Map<String, List<Blog>> ArchiveBlog() {
        List<String> years = blogdao.findYears();
        Map<String,List<Blog>> map = new HashMap<>();
        for(String year:years){
            map.put(year,blogdao.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogdao.count();
    }
}
