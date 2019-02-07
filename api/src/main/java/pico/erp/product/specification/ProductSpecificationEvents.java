package pico.erp.product.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pico.erp.product.specification.content.ProductSpecificationContentId;
import pico.erp.shared.event.Event;
import pico.erp.user.UserId;

public interface ProductSpecificationEvents {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class DraftedEvent implements Event {

    public final static String CHANNEL = "event.product-specification.drafted";

    private ProductSpecificationId productSpecificationId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class NextDraftedEvent implements Event {

    public final static String CHANNEL = "event.product-specification.next-drafted";

    private ProductSpecificationId productSpecificationId;

    private ProductSpecificationContentId previousContentId;

    public String channel() {
      return CHANNEL;
    }

  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  class CommittedEvent implements Event {

    public final static String CHANNEL = "event.product-specification.committed";


    private ProductSpecificationId productSpecificationId;

    private UserId committerId;

    public String channel() {
      return CHANNEL;
    }

  }
}
