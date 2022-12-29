package com.example.crud.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.crud.backend.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	List<Todo> findByPublished(boolean published);

	List<Todo> findByTitleContaining(String title);
}
