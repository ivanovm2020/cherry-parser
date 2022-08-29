package ru.ivanov.cherryparser.repos;

import ru.ivanov.cherryparser.domain.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductTypeRepo extends CrudRepository<ProductType, Long> {
    List<ProductType> findById(Integer id);
}