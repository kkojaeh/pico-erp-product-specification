package pico.erp.product.specification.content.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.process.ProcessId;
import pico.erp.process.info.ProcessInfo;
import pico.erp.product.specification.content.ProductSpecificationContentId;

public interface ProductSpecificationContentProcessRequests {

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class CreateRequest {

    ProductSpecificationContentProcessId id;

    ProductSpecificationContentId contentId;

    ProcessId processId;

    ProcessInfo processInfo;

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  class UpdateRequest {

    ProductSpecificationContentProcessId id;

    ProcessInfo processInfo;

  }

}
