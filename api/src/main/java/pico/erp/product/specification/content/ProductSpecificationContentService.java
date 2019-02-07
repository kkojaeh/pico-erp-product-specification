package pico.erp.product.specification.content;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface ProductSpecificationContentService {

  void commit(@Valid @NotNull ProductSpecificationContentRequests.CommitRequest request);

  ProductSpecificationContentData create(
    @Valid @NotNull ProductSpecificationContentRequests.CreateRequest request);

  boolean exists(@Valid @NotNull ProductSpecificationContentId id);

  ProductSpecificationContentData get(@Valid @NotNull ProductSpecificationContentId id);

  void update(@Valid @NotNull ProductSpecificationContentRequests.UpdateRequest request);

}
