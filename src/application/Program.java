package application;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Program {
    public static void main(String[] args) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("=== TEST 01: findById ===");
        Seller seller = DaoFactory.createSellerDao().findById(1);
        System.out.println(seller);
        System.out.println("\n=========================\n");

        System.out.println("=== TEST 02: findAll ===");
        List<Seller> sellers = DaoFactory.createSellerDao().findAll();
        for (Seller obj : sellers){
            System.out.println(obj);
        }
        System.out.println("\n=========================\n");

        System.out.println("=== TEST 03: insert ===");
        seller = new Seller(null, "Emma Watson", "emmawatson@gmail.com", LocalDate.parse("15/04/1990", fmt), 15000.0, new Department(3, null));
        DaoFactory.createSellerDao().insert(seller);
        System.out.println(DaoFactory.createSellerDao().findById(seller.getId()));
        System.out.println("\n=========================\n");

    }
}
