package model.dao.impl;

import db.ConnectionFactory;
import db.DatabaseConnectionException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SellerDaoJDBC implements SellerDAO {

    private Connection conn;

    public SellerDaoJDBC (Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {

        String sql = "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE seller.Id = ?";

        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    int departmentId = rs.getInt(6);
                    String departmentName = rs.getString(7);
                    Department department = new Department(departmentId, departmentName);

                    int sellerId = rs.getInt(1);
                    String sellerName = rs.getString(2);
                    String sellerEmail = rs.getString(3);
                    LocalDate sellerBirthDate = rs.getDate(4).toLocalDate();
                    double sellerBaseSalary = rs.getDouble(5);

                    Seller seller = new Seller(sellerId, sellerName, sellerEmail, sellerBirthDate, sellerBaseSalary, department);
                    return seller;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
