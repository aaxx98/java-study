package my.jpa.entity;

import java.io.Serializable;
import java.util.Objects;

public class ProductKey implements Serializable {

  private static final long serialVersionUID = 1L;

  private String code;
  private int number;

  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public int getNumber() {
    return number;
  }
  public void setNumber(int number) {
    this.number = number;
  }
  @Override
  public int hashCode() {
    return Objects.hash(code, number);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProductKey other = (ProductKey) obj;
    return Objects.equals(code, other.code) && number == other.number;
  }
}
