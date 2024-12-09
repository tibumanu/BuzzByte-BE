package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Tag;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    @Transactional
    Tag addTag(Tag tag);
    @Transactional
    Tag updateTag(Tag tag);
    @Transactional
    void deleteTag(long tagId);
    @Transactional
    Tag getTag(long tagId);
    @Transactional
    List<Tag> getAllTags();
    @Transactional
    Page<Tag> getTags(int pageNumber, int pageSize);
    @Transactional
    Page<Tag> findAllByCriteria(Long tagId, String tagName, Pageable pageable);
}
