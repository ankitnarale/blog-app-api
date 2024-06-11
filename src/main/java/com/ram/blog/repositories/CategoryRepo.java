package com.ram.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
