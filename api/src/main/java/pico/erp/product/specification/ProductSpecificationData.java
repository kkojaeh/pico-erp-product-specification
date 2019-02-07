package pico.erp.product.specification;

import lombok.Data;
import pico.erp.item.ItemId;
import pico.erp.product.specification.content.ProductSpecificationContentId;

@Data
public class ProductSpecificationData {

  ProductSpecificationId id;

  ItemId itemId;

  int revision;

  ProductSpecificationContentId contentId;

  ProductSpecificationStatusKind status;

  boolean committable;

  boolean draftable;

}
