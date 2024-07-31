package io.microservices.demo.Product.Service;

import io.microservices.demo.Configuration.UserContext;
import io.microservices.demo.Product.Integration.model.User;
import io.microservices.demo.Product.Integration.service.CommonService;
import io.microservices.demo.Product.Repository.CategoryRepository;
import io.microservices.demo.Product.Repository.ProductRepository;
import io.microservices.demo.Product.model.Category;
import io.microservices.demo.Product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommonService commonService;

    public Product addProduct(Product product) throws Exception {

        Long userId = UserContext.getUserId();
        Category category = categoryRepository.findById(product.getTempCategoryId()).orElse(new Category());
        product.setCategory(category);
        User user = commonService.findUserById(userId);
        if(user == null){
            throw new Exception("User not found");
        }
        product.setUserId(userId);
        return productRepository.save(product);
    }


    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Integer id, Product updatedProduct) throws Exception  {
       
        Optional<Product> maybeProduct = productRepository.findById(id);
        
        if(maybeProduct.isPresent()) {

            Product oldProduct = maybeProduct.get();
            oldProduct.setProductName(updatedProduct.getProductName());
            oldProduct.setDescription(updatedProduct.getDescription());
            oldProduct.setPrice(updatedProduct.getPrice());
            oldProduct.setGstPercentage(updatedProduct.getGstPercentage());

            Category category = categoryRepository.findById(updatedProduct.getTempCategoryId()).orElseThrow(() -> new Exception("Category not found"));
            oldProduct.setCategory(category);

            productRepository.save(oldProduct);
        }else{
            throw new Exception("Product Not Found");
        }



    }
}







