package edu.columbia.gskr.tweetdbcrawler.mybatis.util;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */


public class MyBatisConnectionFactory {

    private static final SqlSessionFactory factory;

    private MyBatisConnectionFactory() {
    }

    static {
        InputStream inputStream = null;
        try {
            inputStream = MyBatisConnectionFactory.class.getClassLoader().getResourceAsStream("mybatis-configuration.xml");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        factory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return factory;
    }
}
