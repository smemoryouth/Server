package com.tulun.service;

import com.tulun.bean.Product;

/**
 * 描述: 商品服务类
 *
 * @Author shilei
 * @Date 2018/11/17
 */
public interface IProductService {

    /**
     * 添加商品的服务接口方法
     * @param product
     */
    void addProduct(Product product);

    /**
     * 删除商品的接口方法
     * @param p
     */
    void removeProduct(Product p);

    /**
     * 修改商品的接口方法
     * @param product
     */
    void modifyProduct(Product product);

    /**
     * 根据商品id查询商品的接口方法
     * @param id
     * @return
     */
    Product queryProductById(Integer id);
}
