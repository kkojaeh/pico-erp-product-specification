package pico.erp.product.specification.content.process;

import lombok.Data;
import pico.erp.process.ProcessId;
import pico.erp.process.info.ProcessInfo;
import pico.erp.product.specification.content.ProductSpecificationContentId;

@Data
public class ProductSpecificationContentProcessData {

  ProductSpecificationContentProcessId id;

  ProductSpecificationContentId contentId;

  ProcessId processId;

  ProcessInfo processInfo;

  boolean updatable;

}
