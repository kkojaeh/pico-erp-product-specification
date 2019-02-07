package pico.erp.product.specification.content;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.attachment.AttachmentRequests;
import pico.erp.attachment.AttachmentService;
import pico.erp.product.specification.ProductSpecificationEvents;
import pico.erp.product.specification.ProductSpecificationService;

@SuppressWarnings("unused")
@Component
public class ProductSpecificationContentEventListener {

  private static final String LISTENER_NAME = "listener.product-specification-content-event-listener";

  @Lazy
  @Autowired
  private ProductSpecificationContentService productSpecificationContentService;

  @Lazy
  @Autowired
  private ProductSpecificationService productSpecificationService;

  @Lazy
  @Autowired
  private AttachmentService attachmentService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + ProductSpecificationEvents.DraftedEvent.CHANNEL)
  public void onProductSpecificationDrafted(ProductSpecificationEvents.DraftedEvent event) {
    val specification = productSpecificationService.get(event.getProductSpecificationId());

    productSpecificationContentService.create(
      ProductSpecificationContentRequests.CreateRequest.builder()
        .id(specification.getContentId())
        .revision(specification.getRevision())
        .specificationId(specification.getId())
        .build()
    );
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + ProductSpecificationEvents.NextDraftedEvent.CHANNEL)
  public void onProductSpecificationNextDrafted(ProductSpecificationEvents.NextDraftedEvent event) {
    val specification = productSpecificationService.get(event.getProductSpecificationId());

    val previous = productSpecificationContentService.get(event.getPreviousContentId());

    productSpecificationContentService.create(
      ProductSpecificationContentRequests.CreateRequest.builder()
        .id(specification.getContentId())
        .revision(specification.getRevision())
        .specificationId(specification.getId())
        .build()
    );

    val builder = ProductSpecificationContentRequests.UpdateRequest.builder()
      .id(specification.getContentId())
      .description(previous.getDescription());

    if (previous.getImageId() != null) {
      val image = attachmentService.copy(
        new AttachmentRequests.CopyRequest(previous.getImageId())
      );
      builder.imageId(image.getId());
    }

    if (previous.getBluePrintId() != null) {
      val bluePrint = attachmentService.copy(
        new AttachmentRequests.CopyRequest(previous.getBluePrintId())
      );
      builder.bluePrintId(bluePrint.getId());
    }
    productSpecificationContentService.update(
      builder.build()
    );
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + ProductSpecificationEvents.CommittedEvent.CHANNEL)
  public void onProductSpecificationCommitted(ProductSpecificationEvents.CommittedEvent event) {
    val specification = productSpecificationService.get(event.getProductSpecificationId());

    productSpecificationContentService.commit(
      ProductSpecificationContentRequests.CommitRequest.builder()
        .id(specification.getContentId())
        .committerId(event.getCommitterId())
        .build()
    );
  }

}
