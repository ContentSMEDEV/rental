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
import java.lang.String;


public class UserService {
private JdbcTemplate jdbcTemplate;

    public static boolean check_auth(String setUsername,String setPassword) {

        try{

            String myUrl = "jdbc:mysql://localhost:3306/db?serverTimezone=UTC&useSSL=false";
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(myUrl, "root", "root");


            String query = "SELECT * FROM user WHERE username ="+"'"+setUsername+"'"+"AND password="+"'"+setPassword+"' limit 1";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs != null ){

                //true
                while (rs.next())
                {
                    long id = rs.getInt("id");
                    String _username = rs.getString("username");
                    String _password = rs.getString("password");
                    String _role = rs.getString("role");

                    // print the results
                    System.out.format("%s, %s, %s, %s\n", id, _username,_password,_role);
                    st.close();
                    return true;
                }




            }
            else {
                //false (empty last row)
                System.out.print("Not......");
                st.close();
                return false;

            }

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
            return  false;
        }

        return  false;

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
