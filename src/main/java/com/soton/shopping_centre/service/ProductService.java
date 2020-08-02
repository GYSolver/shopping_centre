package com.soton.shopping_centre.service;

import com.soton.shopping_centre.pojo.Product;

import java.util.List;

public interface ProductService {
    public List<Product> queryAllProducts();
    public Product queryProductById(Integer id);
    public Product queryProductByName(String name);

    public int addProduct(Product product);
    public int deleteProductById(Integer id);
    public int editProduct(Product product);
}
