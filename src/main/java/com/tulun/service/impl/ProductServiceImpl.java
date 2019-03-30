package com.tulun.service.impl;

import com.tulun.bean.Product;
import com.tulun.dao.ProductMapper;
import com.tulun.service.IProductService;
import com.tulun.util.JedisUtils;
import com.tulun.util.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import redis.clients.jedis.Jedis;

/**
 * 描述: 商品服务的实现类  通过服务层直接访问dao层
 *
 * @Author shilei
 * @Date 2018/11/17
 */
public class ProductServiceImpl implements IProductService {

    /**
     * SqlSession session = fa.opensession
     * getMapper
     * session.close
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) {
        SqlSession session = MyBatisUtils.openSession();
        System.out.println("获取session");
        try {
            ProductMapper pm = session.getMapper(ProductMapper.class);
            System.out.println("获取mapper对象");
            pm.insert(product);
            System.out.println("插入");
            session.commit();
            System.out.println("提交");
        } catch (Exception e) {
            session.rollback();
            System.out.println("回滚");
        } finally {
            session.close();
            System.out.println("关闭session");
        }
    }

    @Override
    public void removeProduct(Product product) {
        SqlSession session = MyBatisUtils.openSession();
        try {
            ProductMapper pm = session.getMapper(ProductMapper.class);
            pm.deleteByPrimaryKey(product.getId());
            session.commit();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void modifyProduct(Product product) {
        SqlSession session = MyBatisUtils.openSession();
        try {
            ProductMapper pm = session.getMapper(ProductMapper.class);
            pm.updateByPrimaryKey(product);
            session.commit();
        } catch (Exception e) {
            session.rollback();
        } finally {
            session.close();
        }
    }

    /**
     * 实际应用中，查询操作远远多于更新操作，因此对于不太变化的
     * 数据，进行缓存操作，效率比较高
     * memcacahe  redis原因
     * memcacahe  mc(client) ms(server)
     * redis      rc(client) rs(server)
     * <p>
     * 必须先运行memcacahe server redis server
     * <p>
     * 用户1  发起update操作     删除缓存                    更新数据库
     * <p>
     * 用户2  发起select操作            查询数据库  回写缓存
     * <p>
     * 最终导致数据库和缓存中的数据不一致  这样的问题怎么解决？
     *
     * @param id
     * @return
     */
    @Override
    public Product queryProductById(Integer id) {
        // 现在缓存当中，查找该数据是否存在
        Jedis jedis = JedisUtils.getJedis();
        System.out.println("获取jedis对象");
        Product p = (Product) JedisUtils.get(jedis, String.valueOf(id));
        System.out.println("jedisUtils的get方法调用获取product对象");
        if (jedis.exists(String.valueOf(id))) {
            System.out.println("从redis取");
            return p;
        }
        // 如果缓存中没有找到，就去数据库当中取
        SqlSession session = MyBatisUtils.openSession();
        System.out.println("mybatis获得session");
        try {
            ProductMapper pm = session.getMapper(ProductMapper.class);
            System.out.println("session的getMapper");
            p = pm.selectByPrimaryKey(id);
            System.out.println("从数据库取");
            // 获取数据，放到缓存当中，然后再返回对象
            JedisUtils.set(jedis, String.valueOf(id), p);
            return p;
        } finally {
            session.close();
            System.out.println("关闭session");
        }
    }
}
