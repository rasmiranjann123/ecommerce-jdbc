package com.kodewala.ecommerce.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kodewala.ecommerce.config.DBConnection;
import com.kodewala.ecommerce.model.Product;

public class ProductRepository {
	
	public List<Product> findAll() throws SQLException {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT product_id, name, description,price, stock_quantity FROM products ORDER BY product_id";
                

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock_quantity")
                );

                products.add(product);
            }
        }

        return products;
    }

    public Product findById(int productId)
            throws SQLException {

        String sql = "SELECT product_id, name, description,price, stock_quantity FROM products WHERE product_id = ?";
                
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getBigDecimal("price"),
                            rs.getInt("stock_quantity")
                    );
                }
            }
        }

        return null;
    }
    
    
    public boolean reduceStock(Connection con, int productId, int quantity)
            throws SQLException {

    	String sql = "UPDATE products "
    	        + "SET stock_quantity = stock_quantity - ? "
    	        + "WHERE product_id = ? "
    	        + "AND stock_quantity >= ?";
                

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);

            int rowsUpdated = ps.executeUpdate();

            return rowsUpdated == 1;
        }
    }
    
    
    

public int findStockQuantity(Connection con, int productId)
        throws SQLException {

	String sql = "SELECT stock_quantity "
	        + "FROM products "
	        + "WHERE product_id = ? "
	        + "FOR UPDATE";
            

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, productId);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("stock_quantity");
            }
        }
    }

    return -1;
}

 
}
