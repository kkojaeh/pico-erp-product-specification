package pico.erp.product.specification.content;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.document.DocumentId;
import pico.erp.product.specification.ProductSpecificationId;
import pico.erp.user.UserId;

@Data
public class ProductSpecificationContentView {

  ProductSpecificationContentId id;

  ProductSpecificationId specificationId;

  int revision;

  UserId committerId;

  LocalDateTime committedDate;

  LocalDateTime createdDate;

  DocumentId documentId;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Filter {

    @NotNull
    ProductSpecificationId specificationId;

  }

}
