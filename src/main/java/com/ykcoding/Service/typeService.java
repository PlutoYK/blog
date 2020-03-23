package com.ykcoding.Service;

import com.ykcoding.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface typeService {
    Type saveType(Type type);

    Type updateType(long id,Type type);

    Type getType(long id);

    void deleteType(long id);

    Page<Type> ListType(Pageable pageable);

    Type getTypeByName(String name);

    List<Type> ListType();
    List<Type> ListTypeTop(Integer size);
}
