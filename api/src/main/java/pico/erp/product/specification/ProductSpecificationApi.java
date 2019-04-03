package pico.erp.product.specification;

import javax.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pico.erp.shared.data.Role;

public final class ProductSpecificationApi {

  @RequiredArgsConstructor
  public enum Roles implements Role {

    PRODUCT_SPECIFICATION_WRITER,

    PRODUCT_SPECIFICATION_ACCESSOR,

    PRODUCT_SPECIFICATION_MANAGER;

    @Id
    @Getter
    private final String id = name();

  }
}
