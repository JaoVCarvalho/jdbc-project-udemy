package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory instance;

    private ConnectionFactory(){}

    // Singleton Pattern
    public static ConnectionFactory getInstance(){
        if(instance == null){
//          synchronized é usada para garantir que apenas uma thread por vez possa executar um bloco de código ou method crítico.
            synchronized (ConnectionFactory.class){
                if(instance == null){
                    instance = new ConnectionFactory();
                }
            }
        }

        return instance;
    }

    public Connection getConnection(){

        Properties properties = loadProperties();

        try{
            return DriverManager.getConnection(properties.getProperty("dburl"), properties);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    private Properties loadProperties(){

        Properties properties = new Properties();

        try(FileInputStream file = new FileInputStream("db.properties")){
            properties.load(file);
        } catch (IOException e){
            throw new DatabaseConnectionException(e.getMessage());
        }

        return properties;
    }
}
