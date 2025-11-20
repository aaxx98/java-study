package my.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import my.jpa.config.MyPersistenceUnitInfo;
import my.jpa.entity.Product2;
import my.jpa.entity.Product2Key;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class Main {

  public static void main(String[] args) {
    Map<String, String> props = new HashMap<>();
    props.put("hibernate.hbm2ddl.auto", "update");
    props.put("hibernate.show_sql", "true");

    EntityManagerFactory emf = new HibernatePersistenceProvider()
        .createContainerEntityManagerFactory(new MyPersistenceUnitInfo(),
            props); // persistence.xml 없이 사용 가능

    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();

    // @IdClass
    {
      // 등록
// 			Product p = new Product();
//			p.setCode("uplus");
//			p.setNumber(1);
//			p.setColor("blue");
//			em.persist(p);

      // 조회
//       ProductKey key = new ProductKey();
//       key.setCode("uplus");
//       key.setNumber(1);
//       Product p = em.find(Product.class, key);
//       System.out.println(p);

// 등록2
//       Product2Key key = new Product2Key("uplus", 1);
//       Product2 p2 = new Product2(key, "blue");
//       em.persist(p2);

// 조회2
      Product2Key key = new Product2Key("uplus", 1);
      Product2 result = em.find(Product2.class, key);
      System.out.println(result);
    }

    em.getTransaction().commit(); // 이 시점에 DB 작업

    em.close();

  }
}
