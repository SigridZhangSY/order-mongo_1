package com.thoughtworks.ketsu.domain.product;

import com.mongodb.DB;
import com.mongodb.DBCursor;

import java.util.Map;

public interface ProductRepository {
    Product save(Map<String, Object> info);
}
