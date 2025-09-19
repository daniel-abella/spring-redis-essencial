package com.danielabella.springcomredisv2.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Precisamos que a entidade implemente Serializable
 * É obrigatório quando se usa o cache padrão do Redis com JDK serialization em Spring Boot.
 * Por padrão, o RedisCacheManager utiliza o JdkSerializationRedisSerializer, que exige que os objetos armazenados no cache implementem a interface Serializable.
 */
@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Book implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
}