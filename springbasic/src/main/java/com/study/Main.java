package com.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("hello world!");

        // JDBC 사용법
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.h2.Driver");
            // ~/test 파일로 연결하고 MySQL호환모드를 사용하겠다는 의미
            // String url = "jdbc:h2~/test;MODE=MySQL";

            String url = "jdbc:h2:mem:test;MODE=MySQL"; // 메모리로 연결
            connection = DriverManager.getConnection(url, "sa", "");
            statement = connection.createStatement();

            // 트랜잭션 확인을 위해
            connection.setAutoCommit(false);

            // 테이블 생성
            statement.execute("create table member(id int auto_increment, username varchar(255) not null, password varchar(255) not null, primary key(id))");

            // 데이터 추가
            statement.executeUpdate("insert into member(username, password) values('test', '1234')");

            ResultSet resultSet = statement.executeQuery("select id, username, password from member");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                logger.info("id : "+id+", username : "+username+", password : "+password);
            }

            connection.commit();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();

            // 롤백 
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
