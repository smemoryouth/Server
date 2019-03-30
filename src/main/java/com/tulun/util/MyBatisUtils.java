package com.tulun.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;


/**
 * 描述:
 *
 * @Author shilei
 * @Date 2018/11/4
 */
public class MyBatisUtils{
    private static MyBatisUtils instance = new MyBatisUtils();
    private SqlSessionFactory sqlSessionFactory = null;

    private MyBatisUtils(){
        String resource = "mybatis.xml";
        InputStream in;
        //读取数据库配置文件config.xml的数据，
        //包含了事务配置，连接池配置，mysqld连接信息配置，mybatis xml文件路径信息
        in = MyBatisUtils.class.getClassLoader().getResourceAsStream(resource);

        //创建sqlSessionFactory对象,整个应用周期都存在
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    public static SqlSession openSession(){
        //参数传入true设置自动提交事务的
        SqlSession session = instance.sqlSessionFactory.openSession();
        //手动提交事务
        session.commit(true);
        return session;
    }
}
