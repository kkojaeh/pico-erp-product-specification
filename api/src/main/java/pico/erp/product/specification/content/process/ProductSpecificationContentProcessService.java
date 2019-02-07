package pico.erp.product.specification.content.process;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.product.specification.content.ProductSpecificationContentId;

public interface ProductSpecificationContentProcessService {

  ProductSpecificationContentProcessData create(
    @Valid @NotNull ProductSpecificationContentProcessRequests.CreateRequest request);

  boolean exists(@Valid @NotNull ProductSpecificationContentProcessId id);

  ProductSpecificationContentProcessData get(
    @Valid @NotNull ProductSpecificationContentProcessId id);

  List<ProductSpecificationContentProcessData> getAll(
    @Valid @NotNull ProductSpecificationContentId contentId);

  void update(@Valid @NotNull ProductSpecificationContentProcessRequests.UpdateRequest request);

}
