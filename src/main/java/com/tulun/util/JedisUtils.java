package com.tulun.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 操作redis的工具类
 * @author Administrator
 *
 * Jedis java语言 和  redis server通信用的api
 * common-pool
 *
 */
public class JedisUtils {
	/**
	 * redis的连接池
	 */
	private static JedisPool pool = null;
	
	static{
	    // MySQL Server 3306   Redis Server 6379
		pool = new JedisPool("127.0.0.1", 6379);
	}
	
	/**
	 * 从redis连接池获取一条链接
     * MySQL数据库 称作   关系型数据库
     * redis key-value  => ORM框架中
     * 1. memcached和redis都是缓存服务器，他们之间有什么区别？
     * 存储key-value键值对的缓存数据库（NoSQL数据库）
     *
     * memcached只能在内存中进行数据存储（memcache能不能持久化数据）
     * redis不仅可以在内存中进行存储，还可以持久化数据（RDB方式AOF方式）
     *
     * memcached它只能存取字符串String类型
     * redis可以存取 String，byte[]，List，Set，Map
     *
     * memcached的key =》 value的内存限制1M
     * redis的key =》 value的的内存限制1G
     *
     * 2. memcached和redis缓存本身支不支持线程安全
     * 3. memcahced和redis支不支持事务处理
     *
     * 面试必问问题：
     * 4.缓存数据一致性问题
     *
     * 5.了解redis的一些相关内容
     * C/C++  C语言写的     java nio
     *
     * Linux操作系统提供了系统级别的五种I/O模型：
     * 杜同学         =》         日本
     *          飞机场先买票
     *                        送票员
     *             王同学（代理卖票）
     *
     * 同步阻塞I/O
     * 同步非阻塞I/O   socket
     * I/O复用    一个线程可以监听多个socket了
     * 信号I/O
     * 异步非阻塞I/O
     *
     * Java I/O复用接口                         selector
     * Linux系统提供的I/O复用接口  select/poll   epoll
     *
     * redis server所采用的网络模型就是 epoll + 多线程
     *                             网络I/O线程  一个线程+epoll
     * Java Netty
     * 主线程 + selector   =》  专门负责读取新用户的链接channel
     *
     * 工作线程1  =》 selector模型 channel
     * 工作线程2  =》 selector模型 channel
     * 工作线程3  =》 selector模型 channel
     * 工作线程4  =》 selector模型 channel
     *
     *
	 * @return
	 */
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	/**
	 * 把连接归还到redis连接池
	 * @param jedis
	 */
	public static void close(Jedis jedis){
		if(jedis != null){
			jedis.close();
            // 把jedis归还到连接池
		}
	}

    /**
     * 直接存放对象
     * @param jedis
     * @param key
     * @param object
     */
	public static void set(Jedis jedis, String key, Object object){
	    byte[] values = serizlize(object);
        jedis.set(key.getBytes(), values);
    }

    /**
     * 获取对象本身
     * @param jedis
     * @param key
     * @return
     */
    public static Object get(Jedis jedis, String key){
	    byte[] values = jedis.get(key.getBytes());
	    if(values == null){
	        return null;
        }
	    return deserialize(values);
    }

    /**
     * 序列化
     */
    public static byte[] serizlize(Object object){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(baos != null){
                    baos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 反序列化
     */
    public static Object deserialize(byte[] bytes){
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try{
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
