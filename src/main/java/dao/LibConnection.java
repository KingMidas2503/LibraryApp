package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LibConnection {

    private Connection libConnection;

    private static LibConnection instance;

    private LibConnection() {}


    public static Connection getLibConnection() {
        if(instance == null) {
            instance = new LibConnection();
            try {
                Class.forName("org.postgresql.Driver");
                // Контейнер в Docker № 398e7ae621c1
                instance.libConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/habrdb", "habrpguser", "pgpwd4habr");
                System.out.println("Соединение установлено");
            } catch (ClassNotFoundException e) {
                System.out.print("Блин, возникла ошибка: ");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.print("Блин, возникла ошибка: ");
                e.printStackTrace();
            }
        }
        return instance.libConnection;
    }




}
