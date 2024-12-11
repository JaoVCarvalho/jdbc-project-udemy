package model.dao.impl;

import db.DatabaseConnectionException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDAO {

    private Connection conn;

    public SellerDaoJDBC (Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

        String sql = "INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, Date.valueOf(seller.getBirthDate()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = ps.getGeneratedKeys();

                if(rs.next()){
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
            } else {
                throw new DatabaseConnectionException("Unexpected error! No rows affected");
            }

        } catch (SQLException e){
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void update(Seller seller) {
        String sql = "UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? WHERE Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, Date.valueOf(seller.getBirthDate()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            ps.setInt(6, seller.getId());

            ps.executeUpdate();

        } catch (SQLException e){
            throw new DatabaseConnectionException(e.getMessage());
        }
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

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {

                    Department department = createDepartment(rs);
                    Seller seller = createSeller(rs, department);
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

        List<Seller> sellers= new ArrayList<>();
        String sql = "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "ORDER BY Name";

        try(PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            Map<Integer, Department> departmentMap = new HashMap<>();

            while(rs.next()){

                if(departmentMap.get(rs.getInt(6)) == null){
                    Department department = createDepartment(rs);
                    departmentMap.put(department.getId(), department);
                }

                Seller seller = createSeller(rs, departmentMap.get(rs.getInt(6)));
                sellers.add(seller);

            }

            return sellers;

        } catch (SQLException e){
            throw new DatabaseConnectionException(e.getMessage());
        }

    }

    private Department createDepartment(ResultSet rs) throws SQLException{
        int id = rs.getInt(6);
        String name = rs.getString(7);

        return new Department(id, name);
    }

    private Seller createSeller(ResultSet rs, Department department) throws SQLException{
        int id = rs.getInt(1);
        String name = rs.getString(2);
        String email = rs.getString(3);
        LocalDate birthDate = rs.getDate(4).toLocalDate();
        double baseSalary = rs.getDouble(5);

        return new Seller(id, name, email, birthDate, baseSalary, department);
    }
}
