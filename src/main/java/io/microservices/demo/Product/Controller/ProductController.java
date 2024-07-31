package io.microservices.demo.Product.Controller;

import io.microservices.demo.Product.Repository.CategoryRepository;
import io.microservices.demo.Product.Service.ProductService;
import io.microservices.demo.Product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> product = productService.getAllProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Integer id) {
       LOGGER.info("inside getProductById {} ",id);
       Optional<Product> product = productService.getProductById(id);
       return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws Exception {
        LOGGER.info("inside addProduct {} ", product);
        try {
            productService.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", e);
        }
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(name = "id") Integer id, @RequestBody Product productDetails) throws Exception {
         productService.updateProduct(id,productDetails);
        return  ResponseEntity.ok("Product Updated Successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("delete success");
    }



}




