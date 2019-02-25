package pico.erp.product.specification;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.item.ItemId;
import pico.erp.product.specification.content.ProductSpecificationContentId;
import pico.erp.shared.event.Event;
import pico.erp.user.UserId;

public interface ProductSpecificationMessages {

  interface Draft {

    @Data
    class Request {

      @Valid
      @NotNull
      ProductSpecificationId id;

      @Valid
      @NotNull
      ItemId itemId;

    }

    @Value
    class Response {

      ProductSpecificationContentId contentId;

      Collection<Event> events;

    }

  }

  interface NextDraft {

    @Data
    class Request {

    }

    @Value
    class Response {

      ProductSpecificationContentId previousContentId;

      ProductSpecificationContentId nextContentId;

      Collection<Event> events;

    }

  }

  interface Commit {

    @Data
    class Request {

      @NotNull
      UserId committerId;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

}
