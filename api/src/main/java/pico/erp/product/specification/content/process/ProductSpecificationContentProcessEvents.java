package pico.erp.product.specification.content.process;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.shared.event.Event;

public interface ProductSpecificationContentProcessEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CreatedEvent implements Event {

    public final static String CHANNEL = "event.product-specification-content-process.created";

    private ProductSpecificationContentProcessId productSpecificationContentProcessId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class UpdatedEvent implements Event {

    public final static String CHANNEL = "event.product-specification-content-process.updated";

    private ProductSpecificationContentProcessId productSpecificationContentProcessId;

    public String channel() {
      return CHANNEL;
    }

  }

}
