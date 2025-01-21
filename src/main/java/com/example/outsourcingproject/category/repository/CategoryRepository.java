package com.example.outsourcingproject.category.repository;

import com.example.outsourcingproject.entity.Category;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByNameIn(
        Collection<String> names,
        Sort sort
    );
}