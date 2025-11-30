package com.torrado.crud.services;

import com.torrado.crud.entities.Product;
import com.torrado.crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {

        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> update(Long id, Product product) {

        Optional<Product> productDB = productRepository.findById(id);

        if(productDB.isPresent()){
            Product prod = productDB.get();
            prod.setName(product.getName());
            prod.setPrice(product.getPrice());
            prod.setDescription(product.getDescription());
            productRepository.save(prod);
            return Optional.of(prod);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> delete(Long id) {
        Optional<Product> productDB = productRepository.findById(id);

        productDB.ifPresent(prod -> productRepository.delete(prod));


        return  productDB;
    }
}
