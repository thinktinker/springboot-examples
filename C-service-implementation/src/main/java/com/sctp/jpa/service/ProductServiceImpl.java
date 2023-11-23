package com.sctp.jpa.service;

import com.sctp.jpa.model.Product;
import com.sctp.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
    public void updateProduct(String id, Product product){
        Optional<Product> result = productRepo.findById(id);
        Product temp;
        if(result.isPresent()) {
            temp = result.get();
        }else{
            temp = null;
        }
        temp.setName(product.getName());
        productRepo.save(temp);
    }

    @Override
    public void deleteProduct(String id){
        productRepo.deleteById(id);
    }

    @Override
    public Collection<Product> getProducts(){
        Collection<Product> result;
        result = new ArrayList<>();
        productRepo.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Optional<Product> getProduct(String id){
        return productRepo.findById(id);
    }
}
