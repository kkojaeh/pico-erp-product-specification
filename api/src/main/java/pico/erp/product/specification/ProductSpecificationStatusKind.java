package pico.erp.product.specification;

import lombok.AllArgsConstructor;
import pico.erp.shared.data.LocalizedNameable;

@AllArgsConstructor
public enum ProductSpecificationStatusKind implements LocalizedNameable {

  /**
   * 작성중
   */
  DRAFTING,

  /**
   * 제출 함
   */
  COMMITTED;

  public boolean isCommittable() {
    return this == DRAFTING;
  }

  public boolean isDraftable() {
    return this == COMMITTED;
  }


}
