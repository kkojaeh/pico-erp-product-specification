package pico.erp.product.specification.content;

import java.time.OffsetDateTime;
import lombok.Data;
import pico.erp.attachment.AttachmentId;
import pico.erp.product.specification.ProductSpecificationId;
import pico.erp.user.UserId;

@Data
public class ProductSpecificationContentData {

  ProductSpecificationContentId id;

  ProductSpecificationId specificationId;

  int revision;

  String barcodeNumber;

  AttachmentId imageId;

  AttachmentId bluePrintId;

  String description;

  UserId committerId;

  OffsetDateTime committedDate;

  boolean updatable;

}
