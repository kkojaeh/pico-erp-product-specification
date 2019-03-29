package pico.erp.product.specification.content;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.attachment.AttachmentId;
import pico.erp.document.DocumentId;
import pico.erp.product.specification.ProductSpecification;
import pico.erp.product.specification.ProductSpecificationStatusKind;
import pico.erp.user.UserId;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSpecificationContent implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProductSpecificationContentId id;

  ProductSpecification specification;

  int revision;

  AttachmentId imageId;

  AttachmentId bluePrintId;

  String description;

  UserId committerId;

  LocalDateTime committedDate;

  DocumentId documentId;

  public ProductSpecificationContent() {

  }

  public ProductSpecificationContentMessages.Create.Response apply(
    ProductSpecificationContentMessages.Create.Request request) {
    this.id = request.getId();
    this.specification = request.getSpecification();
    this.revision = request.getRevision();
    return new ProductSpecificationContentMessages.Create.Response(
      Arrays.asList(new ProductSpecificationContentEvents.CreatedEvent(this.id))
    );
  }

  public ProductSpecificationContentMessages.Update.Response apply(
    ProductSpecificationContentMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new ProductSpecificationContentExceptions.CannotUpdateException();
    }
    this.imageId = request.getImageId();
    this.bluePrintId = request.getBluePrintId();
    this.description = request.getDescription();

    return new ProductSpecificationContentMessages.Update.Response(
      Arrays.asList(new ProductSpecificationContentEvents.UpdatedEvent(this.id))
    );
  }

  public ProductSpecificationContentMessages.Commit.Response apply(
    ProductSpecificationContentMessages.Commit.Request request) {
    if (!isCommittable()) {
      throw new ProductSpecificationContentExceptions.CannotCommitException();
    }
    documentId = request.getDocumentId();
    committerId = request.getCommitterId();
    committedDate = LocalDateTime.now();
    return new ProductSpecificationContentMessages.Commit.Response(
      Arrays.asList(new ProductSpecificationContentEvents.CommittedEvent(this.id))
    );
  }

  public boolean isCommittable() {
    return committerId == null
      && specification.getStatus() == ProductSpecificationStatusKind.COMMITTED;
  }

  public boolean isUpdatable() {
    return specification.getStatus() == ProductSpecificationStatusKind.DRAFTING;
  }

}
