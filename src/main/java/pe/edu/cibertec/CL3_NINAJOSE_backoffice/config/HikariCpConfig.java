package pe.edu.cibertec.CL3_NINAJOSE_backoffice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariCpConfig {

    @Value("${DB_BACK_URL:jdbc:mysql://localhost:3306/fabric}")
    private String dbUrl;

    @Value("${DB_BACK_USER:root}")
    private String dbUser;

    @Value("${DB_BACK_PASS:123456}")
    private String dbPass;

    @Value("${DB_BACK_DRIVER:com.mysql.cj.jdbc.Driver}")
    private String dbDriver;

    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();

        // Configurar propiedad de conexión a BD
        config.setJdbcUrl(dbUrl);
        config.setUsername(dbUser);
        config.setPassword(dbPass);
        config.setDriverClassName(dbDriver);

        // Configurar propiedades del pool de HikariCP
        config.setMaximumPoolSize(20); // Número de conexiones máximas en el pool.
        config.setMinimumIdle(5);     // Mínimas conexiones en espera.
        config.setIdleTimeout(300000); // Tiempo antes de cerrar conexiones inactivas.
        config.setConnectionTimeout(30000); // Tiempo máximo de espera para obtener una conexión.


        System.out.println("###### HikariCP initialized ######");
        return new HikariDataSource(config);
    }
}
