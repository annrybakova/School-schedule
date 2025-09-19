package com.solvd.school.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public final class MyBatisUtil {
    private static final SqlSessionFactory SQL_SESSION_FACTORY;
    static {
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    private MyBatisUtil() {}
    public static SqlSessionFactory getSqlSessionFactory() { return SQL_SESSION_FACTORY; }
}
