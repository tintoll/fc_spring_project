package com.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws ClassNotFoundException {
        logger.info("hello world!");

        // JDBC 사용법
        Class.forName("org.h2.Driver");
        // ~/test 파일로 연결하고 MySQL호환모드를 사용하겠다는 의미
        // String url = "jdbc:h2~/test;MODE=MySQL";
        String url = "jdbc:h2:mem:test;MODE=MySQL"; // 메모리로 연결

        // java7에나 나온  try~resource 문법 Autoclose 자동으로 된다.
        // Connection 과 Statement 는 AutoCloseable 상속받고 있어서 가능함.
        try ( Connection connection = DriverManager.getConnection(url, "sa", "");
              Statement statement = connection.createStatement()){

            // 트랜잭션 확인을 위해
            connection.setAutoCommit(false);

            // 테이블 생성
            statement.execute("create table member(id int auto_increment, username varchar(255) not null, password varchar(255) not null, primary key(id))");

            try {
                // 데이터 추가
                statement.executeUpdate("insert into member(username, password) values('test', '1234')");
                connection.commit();
            } catch(SQLException e) {
                connection.rollback();
            }

            ResultSet resultSet = statement.executeQuery("select id, username, password from member");
            while (resultSet.next()) {
                Member member = new Member(resultSet);
                logger.info(member.toString());
            }


        }  catch (SQLException e) {
           e.printStackTrace();
        }

    }

}
