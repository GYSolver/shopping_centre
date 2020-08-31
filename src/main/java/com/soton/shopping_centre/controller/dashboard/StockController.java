package com.soton.shopping_centre.controller.dashboard;

import com.soton.shopping_centre.pojo.Order;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.service.OrderService;
import com.soton.shopping_centre.service.ProductService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/dashboard/stock")
public class StockController {
    @Autowired
    ProductSpecificationService productSpecificationService;

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Product> products = productService.queryAllProducts();
        Iterator<Product> iterator = products.iterator();
        while(iterator.hasNext()){
            Product product = iterator.next();
            List<ProductSpecification> psList = product.getProductSpecifications();
            psList.removeIf(ps -> ps.getStock() > 10);
            product.setProductSpecifications(psList);
            if(product.getProductSpecifications().size()==0){
                iterator.remove();
            }
        }
        model.addAttribute("products",products);
        return "/dashboard/stock/index";
    }
}
