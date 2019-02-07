package pico.erp.product.specification.content.process;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pico.erp.attachment.AttachmentService;
import pico.erp.process.ProcessService;
import pico.erp.product.specification.ProductSpecificationService;
import pico.erp.product.specification.content.ProductSpecificationContentEvents;
import pico.erp.product.specification.content.ProductSpecificationContentService;

@SuppressWarnings("unused")
@Component
public class ProductSpecificationContentProcessEventListener {

  private static final String LISTENER_NAME = "listener.product-specification-content-process-event-listener";

  @Lazy
  @Autowired
  private ProductSpecificationContentProcessService productSpecificationContentProcessService;

  @Lazy
  @Autowired
  private ProductSpecificationContentService productSpecificationContentService;

  @Lazy
  @Autowired
  private ProductSpecificationService productSpecificationService;

  @Lazy
  @Autowired
  private ProcessService processService;

  @Lazy
  @Autowired
  private AttachmentService attachmentService;

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + ProductSpecificationContentEvents.CommittedEvent.CHANNEL)
  public void onProductSpecificationContentCommitted(
    ProductSpecificationContentEvents.CommittedEvent event) {
    val content = productSpecificationContentService.get(event.getProductSpecificationContentId());
    val contentProcesses = productSpecificationContentProcessService
      .getAll(event.getProductSpecificationContentId());

    contentProcesses.forEach(contentProcess -> {
      val process = processService.get(contentProcess.getProcessId());
      if (process.isUpdatable()) {
        val updateRequest = process.toUpdate();
        updateRequest.setInfo(contentProcess.getProcessInfo());
        processService.update(updateRequest);
      }
    });
  }

  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + ProductSpecificationContentEvents.CreatedEvent.CHANNEL)
  public void onProductSpecificationContentCreated(
    ProductSpecificationContentEvents.CreatedEvent event) {
    val content = productSpecificationContentService.get(event.getProductSpecificationContentId());
    val specification = productSpecificationService.get(content.getSpecificationId());
    val processes = processService.getAll(specification.getItemId());

    processes.forEach(process -> {
      productSpecificationContentProcessService.create(
        ProductSpecificationContentProcessRequests.CreateRequest.builder()
          .id(ProductSpecificationContentProcessId.generate())
          .contentId(content.getId())
          .processId(process.getId())
          .processInfo(process.getInfo())
          .build()
      );
    });
  }

}
