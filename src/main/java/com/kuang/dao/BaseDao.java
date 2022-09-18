package com.kuang.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    static {
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }
    //获取数据库连接
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    //编写查询公共类
    public static ResultSet execute(Connection connection,String sql,Object[] parms,ResultSet resultSet,PreparedStatement preparedStatement) throws SQLException {
        //预编译的sql，在后边直接执行就可以了
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < parms.length; i++) {
            preparedStatement.setObject(i+1,parms[i]);
        }
         resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    //增删改公共类
    public static int execute(Connection connection,PreparedStatement preparedStatement,String sql,Object[] parms) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < parms.length; i++) {
            preparedStatement.setObject(i+1,parms[i]);
        }
        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }
    //释放资源
    public static Boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        Boolean flag = true;
        if (resultSet!=null){
            try {
                resultSet.close();
                //GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (connection!=null){
            try {
                connection.close();
                //GC回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

}


