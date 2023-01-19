package at.technikumwien.webshop.repository;

import java.util.List;

import at.technikumwien.webshop.model.Product;

public interface ProductRepository {

    List<Product> findAll();

    int findQuatityById(Long id);
}
