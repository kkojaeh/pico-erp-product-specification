package pico.erp.product.specification;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import pico.erp.item.ItemId;

public interface ProductSpecificationService {

  void commit(@Valid @NotNull ProductSpecificationRequests.CommitRequest request);

  ProductSpecificationData draft(@Valid @NotNull ProductSpecificationRequests.DraftRequest request);

  boolean exists(@Valid @NotNull ProductSpecificationId id);

  boolean exists(@Valid @NotNull ItemId itemId);

  ProductSpecificationData get(@Valid @NotNull ProductSpecificationId id);

  ProductSpecificationData get(@Valid @NotNull ItemId itemId);

}
