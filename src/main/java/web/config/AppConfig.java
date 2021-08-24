package web.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
public class AppConfig {

   private Environment env;

   @Autowired
   public void setEnv(Environment env) { this.env = env; }

   private Properties jpaProperties() {
      Properties properties = new Properties();
      properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
      properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
      return properties;
   }
   @Bean
   public DataSource dataSource(){
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
      dataSource.setUrl(env.getRequiredProperty("db.url"));
      dataSource.setUsername(env.getRequiredProperty("db.username") );
      dataSource.setPassword(env.getRequiredProperty("db.password") );
      return dataSource;
   }
   @Bean
   public JpaVendorAdapter vendorAdapter() {
      return new HibernateJpaVendorAdapter();
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean managerFactory() {
      LocalContainerEntityManagerFactoryBean managerFactory = new LocalContainerEntityManagerFactoryBean();
      managerFactory.setDataSource(dataSource());
      managerFactory.setJpaVendorAdapter(vendorAdapter());
      managerFactory.setPackagesToScan("web.model");
      managerFactory.setJpaProperties(jpaProperties());
      return managerFactory;
   }

   @Bean
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(managerFactory().getObject());
      return transactionManager;
   }
}
