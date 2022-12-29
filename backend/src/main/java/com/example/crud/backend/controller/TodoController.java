package com.example.crud.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crud.backend.repository.TodoRepository;
import com.example.crud.backend.model.Todo;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TodoController {
	@Autowired
	TodoRepository todoRepository;

	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> getAllTodos(@RequestParam(required = false) String title) {
		System.out.println("GET ALL TODOS");
		try {
			List<Todo> todos = new ArrayList<Todo>();

			if (title == null)
				todoRepository.findAll().forEach(todos::add);
			else
				todoRepository.findByTitleContaining(title).forEach(todos::add);

			if (todos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(todos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable("id") long id) {
		System.out.println("GET TODO WITH ID = " + id);
		Optional<Todo> todoData = todoRepository.findById(id);

		if (todoData.isPresent()) {
			System.out.println(todoData);
			return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/todos")
	public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
		System.out.println("CREATE TODO");
		try {
			Todo insertedTodo = todoRepository
					.save(new Todo(todo.getTitle(), todo.getDescription(), false));

			System.out.println(insertedTodo);
			return new ResponseEntity<>(insertedTodo, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<Todo> updateTodo(@PathVariable("id") long id, @RequestBody Todo todo) {
		System.out.println("UPDATE TODO WITH ID = " + id);
		try {
			Optional<Todo> updatingTodo = todoRepository.findById(id);

			if (updatingTodo.isPresent()) {
				Todo retTodo = updatingTodo.get();
				retTodo.setTitle(todo.getTitle());
				retTodo.setDescription(todo.getDescription());
				retTodo.setPublished(todo.isPublished());

				System.out.println(retTodo);
				return new ResponseEntity<>(todoRepository.save(retTodo), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Todo> deleteTodo(@PathVariable("id") long id) {
		System.out.println("DEL TODO WITH ID = " + id);
		try {
			todoRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/todos")
	public ResponseEntity<Todo> deleteAllTodos() {
		System.out.println("DEL ALL TODOS");
		try {
			todoRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/todos/published")
	public ResponseEntity<List<Todo>> findByPublished() {
		System.out.println("GET PUBLISHED TODOS");
		try {
			List<Todo> publishedTodos = todoRepository.findByPublished(true);

			if (publishedTodos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(publishedTodos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
