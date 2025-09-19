package com.danielabella.springcomredisv2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielabella.springcomredisv2.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}