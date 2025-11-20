package my.jpa.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Product2Key implements Serializable {

  private String code;
  private int number;

  public Product2Key() {}

  public Product2Key(String code, int number) {
    this.code = code;
    this.number = number;
  }

  public String getCode() {
    return code;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product2Key that = (Product2Key) o;
    return number == that.number && Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, number);
  }
}
