package com.thoughtworks.ketsu.domain.product;

import com.mongodb.DB;
import com.mongodb.DBCursor;

import java.util.Map;

public interface ProductRepository {
    DBCursor save(Map<String, Object> info, DB db);
}
