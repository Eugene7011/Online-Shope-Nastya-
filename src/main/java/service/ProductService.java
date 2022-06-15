package service;

import dao.ProductDao;
import entity.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class ProductService {
    private ProductDao productDao;

    public void add(Product product) {
        LocalDateTime now = LocalDateTime.now();
        product.setCreationDate(now);
        productDao.add(product);
        System.out.println("Product added");
    }
}
