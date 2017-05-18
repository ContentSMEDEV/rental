package com.saber.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.*;
import java.util.Date;
import org.gjt.mm.mysql.Driver;

public class UserService {
    private JdbcTemplate jdbcTemplate;


    public UserService() {}


    public static void do_login() {
        try
        {
            // create our mysql database connection

            String myDriver = "org.gjt.mm.mysql.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");

            // our SQL SELECT query.
            // if you only need a few columns, specify them by name instead of using "*"
            String query = "SELECT * FROM user";

            // create the java statement
            Statement st = conn.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            // iterate through the java resultset
            while (rs.next())
            {
                long id = rs.getInt("id");
                String _username = rs.getString("username");
                String _password = rs.getString("password");
                String _role = rs.getString("role");

                // print the results
                System.out.format("%s, %s, %s, %s\n", id, _username,_password,_role);

            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }

     public static long loginVerify (String username, String password) {
        return  1;
     }


	 public List<User> findAll() {
	        return jdbcTemplate.query("SELECT * FROM user",
	                (rs, rowNum) -> new User(rs.getLong("id")));
	    }





	 
//	 public void update(User user) {
//	        jdbcTemplate.update("UPDATE customers SET first_name=?, last_name=? WHERE id=?",
//	                customer.getFirstName(), customer.getLastName(), customer.getId());
//	    }
}
