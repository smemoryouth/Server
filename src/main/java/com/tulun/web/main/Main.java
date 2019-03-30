package com.tulun.web.main;

import com.tulun.bean.Product;
import com.tulun.web.controller.ProductController;

/**
 * 描述: 主类，测试类
 *
 * @Author shilei
 * @Date 2018/11/17
 */
public class Main {
    public static void main(String[] args) {
        /**
         * MVC模型
         * M =》 Model =》 模型（数据 bean）
         * V =》 View  =》 视图（数据的展示）
         * C =》 Controller  =》  控制器层（和用户的请求直接交互）
         *
         * 1.main方法的代码当做  用户发起的请求
         * 2.用户发起的请求，都在后台的controller里面进行处理
         * 3.controller层根据请求的分类，访问不同的服务层Service层
         * 4.Service负责具体的业务逻辑处理工作，并负责访问dao层
         * 5.Service做完逻辑处理，返回结果给Controller层，
         *   Controller再返回信息给用户
         *
         *   dao层的访问需要考虑事务，服务器中间件的引入，
         *   都是在Service服务层进行处理的
         */

        ProductController pc = new ProductController();

        Product p1 = new Product(2, "自行车", 120.0,
                100, "山地牌自行车");
        System.out.println("add方法调用");
//        pc.add(p1);
        System.out.println(pc.query(1));
    }
}
