package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private static Connection conn;

    public static Connection getConnection(){

        if(conn == null){
            try {

                Properties properties = loadProperties();
                conn = DriverManager.getConnection(properties.getProperty("dburl"), properties);

            } catch (SQLException e){
                /* A ideia de lançar uma DbException é pelo fato dela herdar de RuntimeException e, assim,
                 * evitando a necessidade de sempre tratar a SQL Exception (herda de Exception) no código.
                 * Dessa forma, posso tratar no momento que achar mais necessário por ser RuntimeException.
                 * */
                throw new DbException(e.getMessage());
            }
        }

        return conn;
    }

    public static void closeConnection(){
        try{
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        } catch (IOException e){
            throw new DbException(e.getMessage());
        }
    }

}
