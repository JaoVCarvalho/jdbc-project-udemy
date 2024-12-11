package application;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        SellerDAO sellerDAO = DaoFactory.createSellerDao();

        try(Scanner sc = new Scanner(System.in);){
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.println("=== TEST 01: findById ===");
            Seller seller = sellerDAO.findById(1);
            System.out.println(seller);
            System.out.println("\n=========================\n");

            System.out.println("=== TEST 02: findAll ===");
            List<Seller> sellers = sellerDAO.findAll();
            for (Seller obj : sellers){
                System.out.println(obj);
            }
            System.out.println("\n=========================\n");

            System.out.println("=== TEST 03: insert ===");
            seller = new Seller(null, "Emma Watson", "emmawatson@gmail.com", LocalDate.parse("15/04/1990", fmt), 15000.0, new Department(3, null));
            sellerDAO.insert(seller);
            System.out.println(sellerDAO.findById(seller.getId()));
            System.out.println("\n=========================\n");

            System.out.println("=== TEST 04: update ===");
            seller = sellerDAO.findById(8);
            seller.setName("Bobby Fischer");
            seller.setEmail("bobbyfischer@gmail.com");
            sellerDAO.update(seller);
            System.out.println("Update completed!");
            System.out.println("\n=========================\n");

            System.out.println("=== TEST 05: delete ===");
            System.out.print("Enter id for delete test: ");
            int id = sc.nextInt();
            sellerDAO.deleteById(id);
            System.out.println("Delete completed!");
            System.out.println("\n=========================\n");

        } finally {
            sellerDAO.close();
        }

    }
}
