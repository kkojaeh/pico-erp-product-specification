package pico.erp.product.specification;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.val;
import pico.erp.item.ItemId;
import pico.erp.product.specification.content.ProductSpecificationContentId;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSpecification implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProductSpecificationId id;

  ItemId itemId;

  ProductSpecificationContentId contentId;

  ProductSpecificationStatusKind status;

  int revision;

  public ProductSpecification() {
  }

  public ProductSpecificationMessages.Draft.Response apply(
    ProductSpecificationMessages.Draft.Request request) {
    this.id = request.getId();
    this.itemId = request.getItemId();
    this.status = ProductSpecificationStatusKind.DRAFTING;
    this.contentId = ProductSpecificationContentId.generate();
    this.revision = 1;
    return new ProductSpecificationMessages.Draft.Response(
      this.contentId,
      Arrays.asList(new ProductSpecificationEvents.DraftedEvent(this.id))
    );
  }

  public ProductSpecificationMessages.NextDraft.Response apply(
    ProductSpecificationMessages.NextDraft.Request request) {
    if (!isDraftable()) {
      throw new ProductSpecificationExceptions.CannotNextDraftException();
    }
    val previousContentId = this.contentId;
    this.status = ProductSpecificationStatusKind.DRAFTING;
    this.contentId = ProductSpecificationContentId.generate();
    this.revision++;
    return new ProductSpecificationMessages.NextDraft.Response(
      previousContentId,
      this.contentId,
      Arrays.asList(new ProductSpecificationEvents.NextDraftedEvent(this.id, previousContentId))
    );
  }

  public ProductSpecificationMessages.Commit.Response apply(
    ProductSpecificationMessages.Commit.Request request) {
    if (!isCommittable()) {
      throw new ProductSpecificationExceptions.CannotCommitException();
    }
    status = ProductSpecificationStatusKind.COMMITTED;
    return new ProductSpecificationMessages.Commit.Response(
      Arrays.asList(
        new ProductSpecificationEvents.CommittedEvent(this.id, request.getCommitterId()))
    );
  }

  public boolean isCommittable() {
    return status.isCommittable();
  }

  public boolean isDraftable() {
    return status.isDraftable();
  }

}
