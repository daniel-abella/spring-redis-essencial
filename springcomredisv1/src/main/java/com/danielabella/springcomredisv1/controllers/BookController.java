package com.danielabella.springcomredisv1.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.danielabella.springcomredisv1.entities.Book;
import com.danielabella.springcomredisv1.services.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return service.create(book);
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book bookData) {
        return service.update(id, bookData);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}