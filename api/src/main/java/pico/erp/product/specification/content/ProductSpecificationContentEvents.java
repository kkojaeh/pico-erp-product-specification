package pico.erp.product.specification.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface ProductSpecificationContentEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.product-specification-content.created";

    private ProductSpecificationContentId productSpecificationContentId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.product-specification-content.updated";

    private ProductSpecificationContentId productSpecificationContentId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CommittedEvent implements Event {

    public final static String CHANNEL = "event.product-specification-content.committed";


    private ProductSpecificationContentId productSpecificationContentId;

    public String channel() {
      return CHANNEL;
    }

  }
}
