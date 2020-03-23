package com.ykcoding.Service;


import com.ykcoding.dao.typeDao;
import com.ykcoding.pojo.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class typeServiceImpl implements typeService{
    @Autowired
    private typeDao typedao;
    @Override
    @Transactional
    public Type saveType(Type type) {
        return typedao.save(type);
    }
    @Transactional
    @Override
    public Type updateType(long id, Type type) {
        Type t = typedao.getOne(id);
        if(t==null){

        }
        BeanUtils.copyProperties(type,t);
        return this.saveType(t);
    }

    @Override
    public Type getType(long id) {
        return typedao.getOne(id);
    }
    @Transactional
    @Override
    public void deleteType(long id) {
        typedao.deleteById(id);
    }

    @Override
    public Page<Type> ListType(Pageable pageable) {
        return typedao.findAll(pageable);
    }

    @Override
    public Type getTypeByName(String name) {
        return typedao.findByName(name);
    }

    @Override
    public List<Type> ListType() {
        return typedao.findAll();
    }

    @Override
    public List<Type> ListTypeTop(Integer size) {
        Sort sort =Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typedao.findTop(pageable);
    }


}
