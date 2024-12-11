package com.example.BuzzByte.service;

import com.example.BuzzByte.model.Tag;
import com.example.BuzzByte.repository.TagRepository;
import com.example.BuzzByte.utils.validation.GenericValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final GenericValidator<Tag> tagValidator;

    @Override
    public Tag addTag(Tag tag) {
        try {
            if (tag == null) {
                throw new IllegalArgumentException("Tag cannot be null");
            }

            tagValidator.validate(tag);
            return tagRepository.save(tag);
        } catch (Exception e) {
            throw new RuntimeException("Error adding tag: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Tag updateTag(Tag tag) {
        var updatedTag = tagRepository.findById(tag.getId()).orElseThrow(
                () -> new EntityNotFoundException(String.format("Tag with id %d not found", tag.getId()))
        );

        updatedTag.setName(tag.getName());

        try {
            tagValidator.validate(updatedTag);
            return tagRepository.save(updatedTag);
        } catch (Exception e) {
            throw new RuntimeException("Error updating tag: " + e.getMessage());
        }
    }

    @Override
    public void deleteTag(long tagId) {
        try {
            tagRepository.deleteById(tagId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting tag: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Tag getTag(long tagId) {
        Optional<Tag> tag = tagRepository.findById(tagId);

        if (tag.isEmpty()) {
            throw new EntityNotFoundException(String.format("Tag with id %d not found", tagId));
        }

        return tag.get();
    }

    @Override
    @Transactional
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public Page<Tag> getTags(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Page<Tag> findAllByCriteria(Long tagId, String tagName, Pageable pageable) {
        Specification<Tag> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (tagId != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), tagId));
            }
            if (tagName != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + tagName + "%"));
            }
            if (predicates.isEmpty() || query == null) return null;
            query.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
            return query.getRestriction();
        });
        Page<Tag> found = tagRepository.findAll(specification, pageable);
        return found;
    }
}
