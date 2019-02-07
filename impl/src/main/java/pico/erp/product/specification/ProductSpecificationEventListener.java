package pico.erp.product.specification;

import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class ProductSpecificationEventListener {

  private static final String LISTENER_NAME = "listener.product-specification-event-listener";


/*
  @EventListener
  @JmsListener(destination = LISTENER_NAME + "."
    + ProductSpecificationEvents.AcceptedEvent.CHANNEL)
  public void onProductionRequestAccepted(ProductSpecificationEvents.AcceptedEvent event) {
    val request = productSpecificationService.get(event.getProductSpecificationId());

    val planId = ProductionPlanId.generate();
    productionPlanService.create(
      ProductionPlanRequests.CreateRequest.builder()
        .id(planId)
        .itemId(request.getItemId())
        .projectId(request.getProjectId())
        .quantity(request.getQuantity())
        .spareQuantity(request.getSpareQuantity())
        .dueDate(request.getDueDate())
        .build()
    );

    productSpecificationService.plan(
      ProductSpecificationRequests.PlanRequest.builder()
        .id(event.getProductSpecificationId())
        .planId(planId)
        .build()
    );
  }

  */


}
