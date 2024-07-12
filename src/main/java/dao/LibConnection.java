package dao;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class LibConnection {

    private Connection libConnection;

    private static LibConnection instance;

    Yaml yaml = new Yaml();

    private InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Links.yaml");
    private Map<String, Object> sqlObject = yaml.load(inputStream);

    private String driver = sqlObject.get("driver").toString();
    private String dbms = sqlObject.get("DBMS").toString();
    private String host = sqlObject.get("host").toString();
    private String port = sqlObject.get("port").toString();
    private String user = sqlObject.get("user").toString();
    private String username = sqlObject.get("username").toString();
    private String password = sqlObject.get("password").toString();

    private LibConnection() {}


    public static Connection getLibConnection() {
        if(instance == null) {
            instance = new LibConnection();
            try {
                Class.forName(instance.driver);
                // Контейнер в Docker № 398e7ae621c1
                String url = instance.dbms + "://" + instance.host + ":" + instance.port + "/" + instance.user;
                String username = instance.username;
                String password = instance.password;
                instance.libConnection = DriverManager.getConnection(url, username, password);
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
