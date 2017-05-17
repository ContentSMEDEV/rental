package com.saber.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

public class UserService {

	@Autowired
    private static JdbcTemplate jdbcTemplate;
	
	public UserService() {
			
		}
	
	 public List<User> findAll() {
	        return jdbcTemplate.query("SELECT * FROM user",
	                (rs, rowNum) -> new User(rs.getLong("id")));
	    }
	 
	 public long loginVerify(String email, String password) {
		 String sql = "SELECT id FROM user where email=? and password=?";
		 
		 long userId = jdbcTemplate.queryForObject(sql, new Object[] { email,password}, Long.class);
		 return userId;
		 
		 
	    }

	 
//	 public void update(User user) {
//	        jdbcTemplate.update("UPDATE customers SET first_name=?, last_name=? WHERE id=?",
//	                customer.getFirstName(), customer.getLastName(), customer.getId());
//	    }
}
