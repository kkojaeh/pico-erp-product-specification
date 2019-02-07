package pico.erp.product.specification;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.item.ItemId;
import pico.erp.user.UserId;

public interface ProductSpecificationRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class DraftRequest {

    @Valid
    @NotNull
    ProductSpecificationId id;

    @Valid
    @NotNull
    ItemId itemId;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CommitRequest {

    @Valid
    @NotNull
    ProductSpecificationId id;

    @Valid
    @NotNull
    UserId committerId;

  }

}
