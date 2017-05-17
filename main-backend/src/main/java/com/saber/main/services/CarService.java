package com.saber.main.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class CarService {

	@Autowired
    private JdbcTemplate jdbcTemplate;

	public List findAll() {
		return jdbcTemplate.query("SELECT license, model, brand FROM car ",
                (rs, rowNum) -> new Car(rs.getLong("license"), rs.getString("model"), rs.getString("brand")));
		
	}
    /*public  List<Car> findAll() {
        return jdbcTemplate.query("SELECT license, model, brand FROM car ",
                (rs, rowNum) -> new Car(rs.getLong("license"), rs.getString("model"), rs.getString("brand")));
    }
*/
    public void update(Car car) {
        jdbcTemplate.update("UPDATE car SET model=?, brand=? WHERE license=?",
                car.getModel(), car.getBrand(), car.getLicense());
    }
}
