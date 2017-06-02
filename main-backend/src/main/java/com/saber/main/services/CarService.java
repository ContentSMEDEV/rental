package com.saber.main.services;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.*;
import com.saber.main.services.Car;

import javax.sql.DataSource;

@Component
public class CarService {

	@Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;


    public Car getCarByID(int carID) {
        Connection conn = null;
        try {

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * from car where id = ?");
            ps.setInt(1, carID);
            Car car = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                car = new Car(rs.getInt("carid"), rs.getString("model"), "brand");
            }
            rs.close();
            ps.close();
            return car;

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public DataSource getDataSource(){
        return dataSource;
    }
}
