package com.thoughtworks.ketsu.domain.product;

import com.mongodb.DB;
import com.mongodb.DBCursor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {
    Product save(Map<String, Object> info);
    List<Product> list();
    Optional<Product> find(String id);
}
