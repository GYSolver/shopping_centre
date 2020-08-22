package com.soton.shopping_centre.controller.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soton.shopping_centre.pojo.Category;
import com.soton.shopping_centre.pojo.Product;
import com.soton.shopping_centre.pojo.ProductSpecification;
import com.soton.shopping_centre.pojo.Specification;
import com.soton.shopping_centre.service.CategoryService;
import com.soton.shopping_centre.service.ProductService;
import com.soton.shopping_centre.service.ProductSpecificationService;
import com.soton.shopping_centre.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
@RequestMapping("/dashboard/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductSpecificationService productSpecificationService;

    @Autowired
    SpecificationService specificationService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/")
    public String onGetIndex(Model model){
        List<Product> products = productService.queryAllProducts();
        model.addAttribute("products",products);
        return "/dashboard/product/index";
    }

    @GetMapping("/add")
    public String onGetAdd(Model model){
        List<Category> categories = categoryService.queryAllCategories();
        model.addAttribute("categories",categories);
        return "/dashboard/product/add";
    }

    @PostMapping("/add")
    public String onPostAdd(Product product, int[] specificationId, int[] price, int[] stock, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if(product!=null&&product.getName()!=null&&price!=null&&stock!=null){
            //store image
            //String path = request.getServletContext().getRealPath("/");
            String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            staticPath=staticPath.replace("%20"," ");
            File absPath = new File(staticPath+"\\upload");
            if (!absPath.exists()) {
                absPath.mkdirs();
            }
            String timeStamp=System.currentTimeMillis() + file.getOriginalFilename();
            String absPathStr=absPath +"\\"+timeStamp;

            file.transferTo(new File(absPathStr));
            product.setImagePath("/upload/"+timeStamp);
            productService.addProduct(product);

            //store specifications
            String jsonStr=null;
            //serialize specifications
            for(int i=0;i<price.length;i++){
                //specification
                if(specificationId!=null) {
                    HashMap<String,String> specMap = new LinkedHashMap<>();
                    for (int j = 0; j < specificationId.length / price.length; j++) {
                        Specification specification = specificationService.querySpecificationById(specificationId[i * (specificationId.length / price.length) + j]);
                        specMap.put(specification.getName(), specification.getValue());
                    }
                    jsonStr = objectMapper.writeValueAsString(specMap);
                }

                ProductSpecification productSpecification=new ProductSpecification();
                productSpecification.setProductId(product.getId());
                productSpecification.setSpecification(jsonStr);
                productSpecification.setPrice(price[i]);
                productSpecification.setStock(stock[i]);
                productSpecificationService.addPdctSpec(productSpecification);
            }
            return "redirect:/dashboard/product/";
        }
        else
            return "/front-stage/404";
    }

    @GetMapping("/edit/{id}")
    public String onGetEdit(@PathVariable Integer id, Model model){
        Product product = productService.queryProductById(id);
        model.addAttribute("product",product);
        return "/dashboard/product/edit";
    }

    @PostMapping("/edit")
    public String onPostEdit(Product product,@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if(product!=null&&product.getName()!=null){
            //change image
            String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
            staticPath=staticPath.replaceAll("%20"," ");
            File absPath = new File(staticPath);

            String relPathStr=productService.queryProductById(product.getId()).getImagePath().replace("/","\\");
            String absPathStr=absPath+relPathStr;
            file.transferTo(new File(absPathStr));
            productService.editProduct(product);
            return "redirect:/dashboard/product/";
        }
        else
            return "/front-stage/404";
    }


    @PostMapping("/delete/{id}")
    public String onPostDelete(@PathVariable Integer id){
        //delete image
        String staticPath = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        staticPath=staticPath.replaceAll("%20"," ");

        String relPathStr=productService.queryProductById(id).getImagePath();
        String absPathStr=staticPath+relPathStr;
        File file = new File(absPathStr);
        if(file.exists())
            file.delete();

        //delete database record        //productSpecifications are also deleted in ProductService
        productService.deleteProductById(id);
        return "redirect:/dashboard/product/";
    }
}
