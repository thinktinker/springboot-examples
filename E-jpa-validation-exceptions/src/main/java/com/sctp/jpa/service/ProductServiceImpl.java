package com.sctp.jpa.service;

import com.sctp.jpa.model.Product;
import com.sctp.jpa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Optional<Product> updateProduct(String id, Product product){
        Optional<Product> result = productRepo.findById(id);

        try{
            Product temp = result.get();
            temp.setName(product.getName());
            return Optional.of(productRepo.save(temp));
        }catch(Exception e){
            return result;
        }
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
