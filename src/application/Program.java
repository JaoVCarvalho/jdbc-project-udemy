package application;

import db.ConnectionFactory;
import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {

        Seller seller = DaoFactory.createSellerDao().findById(1);

        System.out.println(seller);

    }
}
