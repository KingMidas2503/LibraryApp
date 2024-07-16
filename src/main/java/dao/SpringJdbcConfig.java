package dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;


public class SpringJdbcConfig {

    private JdbcTemplate jdbcTemplate;

    private static SpringJdbcConfig instance;

    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    Yaml yaml = new Yaml();
    private InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("yml-files/Links.yaml");
    private Map<String, Object> sqlObject = yaml.load(inputStream);

    private String driver = sqlObject.get("driver").toString();
    private String dbms = sqlObject.get("DBMS").toString();
    private String host = sqlObject.get("host").toString();
    private String port = sqlObject.get("port").toString();
    private String user = sqlObject.get("user").toString();
    private String username = sqlObject.get("username").toString();
    private String password = sqlObject.get("password").toString();

    private SpringJdbcConfig() {}

    public static JdbcTemplate getJdbcTemplate() {
        if (instance == null) {
            instance = new SpringJdbcConfig();
            instance.dataSource = new SimpleDriverDataSource();
            String url = instance.dbms + "://" + instance.host + ":" + instance.port + "/" + instance.user;
            String username = instance.username;
            String password = instance.password;
            try {
                Class.forName(instance.driver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            instance.dataSource.setDriverClass(org.postgresql.Driver.class);
            instance.dataSource.setUrl(url);
            instance.dataSource.setUsername(username);
            instance.dataSource.setPassword(password);
            instance.jdbcTemplate = new JdbcTemplate(instance.dataSource);
            System.out.println("Соединение установлено");
        }

        return instance.jdbcTemplate;
    }
}
