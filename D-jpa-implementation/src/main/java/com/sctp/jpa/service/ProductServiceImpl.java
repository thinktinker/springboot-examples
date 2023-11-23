package com.sctp.jpa.service;

import com.sctp.jpa.model.Product;
import com.sctp.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(@Autowired ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public void createProduct(Product product){
        productRepo.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product){
        Optional<Product> result = productRepo.findById(id);
        Product temp;
        if(result.isPresent()) {
            temp = result.get();
            temp.setName(product.getName());
            return productRepo.save(temp);
        }
        return null;
    }

    @Override
    public void deleteProduct(String id){
        productRepo.deleteById(id);
    }

    @Override
    public Collection<Product> getProducts(){
        return productRepo.findAll();
    }

    public Collection<Product> getProductsByName(String name){
        return productRepo.findByNameContaining(name);
    }

    @Override
    public Optional<Product> getProduct(String id){
        return productRepo.findById(id);
    }

}
