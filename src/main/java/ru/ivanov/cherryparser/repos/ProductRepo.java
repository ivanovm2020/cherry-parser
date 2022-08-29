package ru.ivanov.cherryparser.repos;

import ru.ivanov.cherryparser.domain.Product;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> findAllByDescriptionContains(String description);
}