package com.sctp.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctp.jpa.model.Product;
import com.sctp.jpa.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
//import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest // place the @AutoConfigureMockMvc annotation on this class under test
class ProductServiceControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean   // annotates that ProductService is what we want to set up as a mock service
    private ProductService productService;

    private Product product1, product2;
    private Product updatedProduct1;

    @Autowired
    private ObjectMapper objectMapper;  // used for sending data in string format when we run our tests

    @BeforeEach // set up some initialisations before we use run the test
    void init(){
        product1 = new Product("1", "Salmon");
        product2 = new Product("2", "Trout");
        updatedProduct1 = new Product("1", "Norwegian Salmon");
    }

    @Test   // testing all products to be returned for GET request for all products
    void getProducts() throws Exception{
        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        when(productService.getProducts()).thenReturn(list);

        this.mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void getSingleProduct() throws Exception {
        //CHALLENGE TO WRITE THE UNIT TEST TO GET A SINGLE PRODUCT
        when(productService.getProduct(any(String.class)))
                .thenReturn(Optional.ofNullable(product1));

        this.mockMvc.perform(get("/product/{id}","1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product1.getId())))
                .andExpect(jsonPath("$.name", is(product1.getName())));
    }

    @Test
    void updateProduct() throws Exception{
        // this is unit test, therefore the expected output stated here
        when(productService.updateProduct(any(String.class), any(Product.class)))
                .thenReturn(Optional.ofNullable(updatedProduct1));

        // this is what the unit test will perform and the expected output should
        // be the same as the above unit test case
        this.mockMvc.perform(put("/products/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedProduct1.getId())))
                .andExpect(jsonPath("$.name", is(updatedProduct1.getName())));
    }

    @Test
    void deleteProduct() throws Exception{
        // doNothing().when(productService).deleteProduct(anyString());
        // this.mockMvc.perform(delete("/products/{id}", "1")).andExpect(status().isNoContent());

        when(productService.deleteProduct(any(String.class)))
                .thenReturn(Optional.ofNullable((product1)));

        this.mockMvc.perform(delete("/products/{id}", "1")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product1.getId())))
                .andExpect(jsonPath("$.name", is(product1.getName())));
    }

    @Test
    void createProduct() throws Exception{
        when(productService.createProduct(any(Product.class))).thenReturn(product1);

        this.mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(product1.getId())))
                .andExpect(jsonPath("$.name", is(product1.getName())));

    }
}