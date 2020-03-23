package com.ykcoding.Service;


import com.ykcoding.dao.tagDao;
import com.ykcoding.pojo.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class tagServiceImpl implements tagService{
    @Autowired
    private tagDao tagdao;
    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagdao.save(tag);
    }
    @Transactional
    @Override
    public Tag updateTag(long id, Tag tag) {
        Tag t = tagdao.getOne(id);
        if(t==null){

        }
        BeanUtils.copyProperties(tag,t);
        return this.saveTag(t);
    }

    @Override
    public Tag getTag(long id) {
        return tagdao.getOne(id);
    }
    @Transactional
    @Override
    public void deleteTag(long id) {
        tagdao.deleteById(id);
    }

    @Override
    public Page<Tag> ListTag(Pageable pageable) {
        return tagdao.findAll(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagdao.findByName(name);
    }

    @Override
    public List<Tag> ListTag() {
        return tagdao.findAll();
    }

    @Override
    public List<Tag> ListTag(String ids) {
        return tagdao.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> ListTagTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return tagdao.findTop(pageable);
    }


    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids)&&ids!=null) {
            String[] idArray = ids.split(",");
            for (int i=0;i<idArray.length;i++) {
                list.add(new Long(idArray[i]));
            }
        }
        return list;
    }

}
