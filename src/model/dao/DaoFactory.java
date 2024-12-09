package model.dao;

import db.ConnectionFactory;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDAO createSellerDao(){
        return new SellerDaoJDBC(ConnectionFactory.getInstance().getConnection());
    }

    public static DepartmentDAO createDepartmentDao(){
        return new DepartmentDaoJDBC(ConnectionFactory.getInstance().getConnection());
    }
}
