package application;

import db.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            System.out.println("Connection established...");
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
