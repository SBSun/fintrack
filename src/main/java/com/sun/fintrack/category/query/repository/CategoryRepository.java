package com.sun.fintrack.category.query.repository;

import com.sun.fintrack.category.domain.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
