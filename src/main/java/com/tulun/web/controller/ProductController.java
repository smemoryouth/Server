package com.tulun.web.controller;

import com.tulun.bean.Product;
import com.tulun.service.IProductService;
import com.tulun.service.impl.ProductServiceImpl;

/**
 * 描述: 商品的控制器层
 *
 * @Author shilei
 * @Date 2018/11/17
 */
public class ProductController {

    private IProductService ips;

    public ProductController(){
        ips = new ProductServiceImpl();
    }

    public void add(Product p1) {
        System.out.println("addProduct方法调用");
        ips.addProduct(p1);
    }
    public Product query(Integer id){
        System.out.println("queryProductById方法调用");
        return ips.queryProductById(id);
    }
    public void remove(Product p){
        ips.removeProduct(p);
    }
    public void modify(Product p){
        ips.modifyProduct(p);
    }
}
