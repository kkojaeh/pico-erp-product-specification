package pico.erp.product.specification.content;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.attachment.AttachmentId;
import pico.erp.product.specification.ProductSpecificationId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.user.UserId;

public interface ProductSpecificationContentRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    @Valid
    @NotNull
    ProductSpecificationContentId id;

    @Valid
    @NotNull
    ProductSpecificationId specificationId;

    @Min(1)
    int revision;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    @Valid
    @NotNull
    ProductSpecificationContentId id;

    @Valid
    AttachmentId imageId;

    @Valid
    AttachmentId bluePrintId;

    @Size(max = TypeDefinitions.CLOB_LENGTH)
    String description;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CommitRequest {

    @Valid
    @NotNull
    ProductSpecificationContentId id;

    @Valid
    UserId committerId;

  }

}
