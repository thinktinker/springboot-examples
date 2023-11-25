package com.sctp.jpa.service;

import com.sctp.jpa.model.Product;
import com.sctp.jpa.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.any;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) //Use the Mokito framework to create and inject mocked objects into our JUnit tests
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepo;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    private Product product1, product2, product3;

    @BeforeEach // @BeforeEach annotation initialises init() function before each test (@Test) is run
    void init() {
        product1 = new Product("1", "Salmon");
        product2 = new Product("2", "Trout");
        product3 = new Product("2", "Norwegian Salmon");
    }

    @Test
    void createProduct() {

        // Perform a Test Case:
        // when JPA repository (productRepo) save() a Product, the returned result should be that of product1
        // productServiceImpl invokes productRepo's save() method via createProduct method, passing in product1
        // and createProduct() method returns the new product to variable newProduct

        when(productRepo.save(any(Product.class))).thenReturn(product1);
        Product newProduct = productServiceImpl.createProduct(product1);


        // Verify the Result(s):
        // JUNIT assertNotNull() method checks that createProduct() has indeed returned a result to variable newProduct
        // JUNIT assertThat() checks that product1 is indeed created, where newProduct variable's id isEqualTo "1"

        assertNotNull(newProduct);
        assertThat(newProduct.getId()).isEqualTo("1");

        // CHALLENGE #1, replace and run: assertThat(newProduct.getId()).isEqualTo("2");
        // Note down your finding(s) and the reason
    }

    @Test
    void updateProduct() {

        // Perform a Test Case:
        // when JPA repository (productRepo) finds a productById(), the returned result should be of product1
        // when JPA repository (productRepo) save a Product, the returned result should be of product1
        // Update product1 variable, change product1.name from "Salmon" to "Grouper"
        // productServiceImpl invokes productRepo's save() method via updateProduct, passing in product1.id and product1
        // and updateProduct() method returns to currentProduct variable the updated Product

        when(productRepo.findById(anyString())).thenReturn(Optional.ofNullable(product1));
        when(productRepo.save(any(Product.class))).thenReturn(product1);
        product1.setName("Grouper");
        Optional<Product> currentProduct = productServiceImpl.updateProduct(product1.getId(), product1);


        // Verify the Result(s):
        // JUNIT assertNotNull() method checks that updateProduct() has indeed returned a result to currentProduct
        // JUNIT assertEquals() method checks that currentProduct is indeed updated, where
        // variable currentProduct's name is expected to be equal to "Grouper"
        // Note that we use get() method to obtain the name from variable currentProduct as it is returned optionally

        assertNotNull(currentProduct);
        assertEquals("Grouper", currentProduct.get().getName());

        // CHALLENGE #1, replace and run: assertEquals("Marlin", currentProduct.get().getName());
        // Note down your finding(s) and the reason
    }

    @Test
    void getProducts() {

        // Perform a Test Case:
        // Create a list (List<Product>) and add product1 and product2 to it
        // when JPA repository (productRepo) findAll() products, it should return the results similar to list
        // productServiceImpl invokes productRepo's findAll() method, via getProducts() method
        // and getProducts() returns a list of Products to the variable called results on all products found


        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        when(productRepo.findAll()).thenReturn(list);
        List<Product> results = (List<Product>) productServiceImpl.getProducts();


        // Verify the Result(s):
        // JUNIT assertNotNull() method checks that getProducts() has indeed returned a list of results (NOT null)
        // JUNIT assertEquals() method checks that variable result's size() is expected to be equal to list's size of 2

        assertNotNull(results);
        assertEquals(2, results.size());

        // CHALLENGE #1, replace and run: assertEquals(5, results.size());
        // Note down your finding(s) and the reason
    }

    @Test
    void getProduct() {

        // Perform a Test Case:
        // when JPA repository (productRepo) find() a product, it should return the result similar to product1
        // productServiceImpl invokes productRepo's findById() method via getProduct() method
        // and getProduct() method returns a Product to variable currentProduct with the Product that is found.


        when(productRepo.findById(anyString())).thenReturn(Optional.of(product1));
        Optional<Product> currentProduct = productServiceImpl.getProduct(product1.getId());

        // Verify the Result(s):
        // JUNIT assertNotNull() method checks that getProduct() has indeed returned a result (NOT null)
        // JUNIT assertThat() method checks that variable currentProduct is NOT empty
        // JUNIT assertThat() method checks that variable currentProduct's id is indeed the returned product
        // where product1.id isEqualTo "1"

        assertNotNull(currentProduct);
        assertThat(currentProduct.isEmpty()).isNotEqualTo(true);
        assertThat(currentProduct.get().getId()).isEqualTo("1");

        // CHALLENGE #1, replace and run: assertThat(currentProduct.get().getId()).isEqualTo("5");
        // Note down your finding(s) and the reason
    }

    @Test
    void getProductsByName() {

        // Perform a Test Case:
        // Create a list and add product1, product3
        // when JPA repository (productRepo) calls custom method findByNameContaining(), it should return results similar to list
        // productServiceImpl invokes productRepo's findByNameContaining() method signature via getProductsByName(),
        // and getProductsByName() is returned a list of Products to variable results with the products that are found.

        List<Product> list = new ArrayList<>();
        list.add(product1); // "Salmon"
        list.add(product3); // "Norwegian Salmon"

        when(productRepo.findByNameContaining(anyString())).thenReturn(list);
        List<Product> results = (List<Product>) productServiceImpl.getProductsByName("Salmon");


        // Verify the Result(s):
        // JUNIT assertNotNull() method checks that variable getProductsByName() has indeed returned results (NOT null)
        // JUNIT assertEquals() method checks that the result's size() is equal to the list's size of 2
        // JUNIT assertThat() method check that there are Products returned to variable results (NOT empty)
        // JUNIT assertThat() is also used to check that data returned to variable results isSameAs list

        assertNotNull(results);
        assertEquals(2, results.size());
        assertThat(results.isEmpty()).isNotEqualTo(true);
        assertThat(results).isSameAs(list);

        // CHALLENGE #1, replace and run: assertEquals(5, results.size());
        // CHALLENGE #2: create List<Product> anotherList, replace and run assertThat(results).isSameAs(anotherList);
        // Note down your finding(s) and the reason
    }

    @Test
    void deleteProduct() {

        // Perform a Test Case:
        // when JPA repository (productRepo) findById() Product.id of "1", the returned result should be that of product1
        // do nothing when JPA repository (productRepo) executes deletesById
        // productServiceImpl invokes productRepo's deleteById() method via delete method(), where
        // deleteProduct() method returns to variable deletedProduct the Product that was deleted


        String productId = "1";
        when(productRepo.findById(anyString())).thenReturn(Optional.of(product1));
        doNothing().when(productRepo).deleteById(anyString());
        Optional<Product> deletedProduct = productServiceImpl.deleteProduct(productId);

        // Verify the Result(s):
        // invoke mokito's verify method that check if productRepo has indeed invoked deleteById by ONE time

        verify(productRepo, times(1)).deleteById(productId);

        // CHALLENGE #1, replace and run: productServiceImpl.deleteProduct("2");
        // CHALLENGE #2: replace and run: verify(productRepo, times(2)).deleteById(productId);
        // Note down your finding(s)
    }

}

// Additional Suggestion
// We can adopt the approach below to test deleteProduct()
// method since is updated to return the Product that is deleted

/*
@Test
void deleteProduct(){
    when(productRepo.findById(anyString())).thenReturn(Optional.ofNullable(product1));
    Optional<Product> deletedProduct = productServiceImpl.deleteProduct(product1.getId());

    // check if the deleted product is present in the variable returned
    // after calling ProductServiceImpl to delete the product (deleteProduct)
    assertThat(deletedProduct.isPresent()).isTrue();

    // check if the deletedProduct value is same as the value of the product we want to delete
    assertThat(deletedProduct.get()).isEqualTo(product1);
}
*/