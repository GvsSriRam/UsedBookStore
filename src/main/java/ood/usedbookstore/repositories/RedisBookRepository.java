package ood.usedbookstore.repositories;

import ood.usedbookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisBookRepository {
    @Autowired
    private RedisTemplate<String, Book> redisTemplate;

    public void cacheBook(Book book) {
        redisTemplate.opsForValue().set("book:" + book.getId(), book);
    }

    public Book getCachedBook(Long id) {
        return redisTemplate.opsForValue().get("book:" + id);
    }

    // ... add methods for recent views and recommendations ...
}
