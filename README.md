
# 📚 Spring Boot Redis API Example (Books API)

Este projeto demonstra como criar uma API RESTful de Livros usando **Spring Boot**, **Spring Data JPA**, **H2 Database (persistência em disco)** e **Redis** com cache ativado.

---

## 🔨 Criação do Projeto

O projeto foi criado com [https://start.spring.io](https://start.spring.io) com as seguintes configurações:

- **Project:** Maven
- **Language:** Java
- **Spring Boot:** 3.x.x
- **Group:** `com.danielabella`
- **Artifact:** `springcomredisv2`
- **Name:** `springcomredisv1`
- **Description:** `Example using Redis with Spring Boot`
- **Packaging:** Jar
- **Java:** 17

### 🧩 Dependências incluídas

- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- H2 Database
- Spring Data Redis
- Lombok

---

## 📁 Estrutura de Arquivos

```
src/
└── main/
    ├── java/com/danielabella/springcomredisv2/
    │   ├── controller/
    │   │   └── BookController.java
    │   ├── entities/
    │   │   └── Book.java
    │   ├── repository/
    │   │   └── BookRepository.java
    │   ├── service/
    │   │   ├── BookService.java
    │   │   └── BookServiceImpl.java
    │   ├── config/
    │   │   └── RedisConfig.java
    │   └── Springcomredisv2Application.java
    └── resources/
        └── application.properties
```

---

## 🧠 Explicação das Anotações do Redis

| Anotação | Explicação |
|----------|------------|
| `@EnableCaching` | Ativa o mecanismo de cache do Spring |
| `@Cacheable("books")` | Indica que o resultado do método será armazenado em cache com a chave `books` |
| `@CacheEvict(value = "books", allEntries = true)` | Remove entradas do cache quando o método é executado |
| `@CachePut(value = "books", key = "#book.id")` | Atualiza diretamente o cache com o retorno do método |

---

## ⚙️ application.properties

```properties
spring.datasource.url=jdbc:h2:file:./data/bookdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true

# Redis
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

---

## 🐳 Como Rodar o Redis com Docker

```bash
docker run --name redis -p 6379:6379 -d redis
```

### Acessar o Redis CLI

```bash
docker exec -it redis redis-cli
```

### Comandos úteis no Redis CLI

```bash
KEYS *                       # Lista todas as chaves
GET "books::SimpleKey []"   # Mostra o conteúdo em cache (modo binário por padrão)
```

### ❗ Para visualização legível: use JSON como serializador

```java
@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer()
                )
            );
    }
}
```

---

## 📬 Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET    | /books   | Lista todos os livros (usa cache) |
| POST   | /books   | Adiciona novo livro e limpa o cache |
| PUT    | /books/{id} | Atualiza um livro e atualiza o cache |
| DELETE | /books/{id} | Remove um livro e limpa o cache |

---

## ✅ Exemplo de JSON para POST

```json
{
  "title": "Clean Architecture",
  "author": "Robert C. Martin"
}
```

---

## ▶️ Como Rodar o Projeto

```bash
./mvnw spring-boot:run
```

Acesse o console do H2 em: `http://localhost:8080/h2-console`

---

## 🧪 Teste de Cache com Redis CLI

```bash
docker exec -it redis redis-cli

# Listar chaves
KEYS *

# Ver conteúdo do cache (se estiver serializando com Jackson)
GET "books::SimpleKey []"
```

---

Criado por [Daniel Abella](https://www.daniel-abella.com)
