package pico.erp.product.specification.content;

import java.time.LocalDateTime;
import lombok.Data;
import pico.erp.attachment.AttachmentId;
import pico.erp.document.DocumentId;
import pico.erp.product.specification.ProductSpecificationId;
import pico.erp.user.UserId;

@Data
public class ProductSpecificationContentData {

  ProductSpecificationContentId id;

  ProductSpecificationId specificationId;

  int revision;

  AttachmentId imageId;

  AttachmentId bluePrintId;

  String description;

  UserId committerId;

  LocalDateTime committedDate;

  boolean updatable;

  DocumentId documentId;

}
