package com.ykcoding.Service;

import com.ykcoding.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface tagService {
    Tag saveTag(Tag type);

    Tag updateTag(long id, Tag type);

    Tag getTag(long id);

    void deleteTag(long id);

    Page<Tag> ListTag(Pageable pageable);

    Tag getTagByName(String name);

    List<Tag> ListTag();
    List<Tag> ListTag(String ids);
    List<Tag> ListTagTop(Integer size);
}
