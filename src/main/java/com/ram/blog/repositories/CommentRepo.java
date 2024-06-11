package com.ram.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ram.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
