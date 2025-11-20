package my.jpa.config;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyPersistenceUnitInfo implements PersistenceUnitInfo {

  private final List<String> managedClassNames = new ArrayList<>();

  public MyPersistenceUnitInfo() {
    // 여기에 JPA 엔티티 클래스 이름을 모두 추가해야 함
    managedClassNames.add("my.jpa.entity.Product");
    managedClassNames.add("my.jpa.entity.Product2");
  }

  @Override
  public String getPersistenceUnitName() {
    return "myJpaUnit";  // unit 이름 설정
  }

  @Override
  public String getPersistenceProviderClassName() {
    return "org.hibernate.jpa.HibernatePersistenceProvider";
  }

  @Override
  public PersistenceUnitTransactionType getTransactionType() {
    return PersistenceUnitTransactionType.RESOURCE_LOCAL;
  }

  @Override
  public DataSource getJtaDataSource() {
    return null;
  }

  @Override
  public DataSource getNonJtaDataSource() {
    return null;
  }

  @Override
  public List<String> getMappingFileNames() {
    return new ArrayList<>();
  }

  @Override
  public List<URL> getJarFileUrls() {
    return new ArrayList<>();
  }

  @Override
  public URL getPersistenceUnitRootUrl() {
    return null;
  }

  @Override
  public List<String> getManagedClassNames() {
    return managedClassNames;
  }

  @Override
  public boolean excludeUnlistedClasses() {
    return false;
  }

  @Override
  public SharedCacheMode getSharedCacheMode() {
    return SharedCacheMode.ENABLE_SELECTIVE;
  }

  @Override
  public ValidationMode getValidationMode() {
    return ValidationMode.AUTO;
  }

  @Override
  public Properties getProperties() {
    Properties props = new Properties();

    props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
    props.put("hibernate.connection.url", "jdbc:mysql://localhost:3307/myapp");
    props.put("hibernate.connection.username", "root");
    props.put("hibernate.connection.password", "1234");

    props.put("hibernate.hbm2ddl.auto", "update");
    props.put("hibernate.show_sql", "true");

    return props;
  }

  @Override
  public String getPersistenceXMLSchemaVersion() {
    return "3.0";
  }

  @Override
  public ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  @Override
  public void addTransformer(ClassTransformer transformer) {
  }

  @Override
  public ClassLoader getNewTempClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}
