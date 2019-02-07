package pico.erp.product.specification;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.item.ItemData;
import pico.erp.product.specification.content.ProductSpecificationContentId;
import pico.erp.shared.event.Event;
import pico.erp.user.UserData;

public interface ProductSpecificationMessages {

  interface Draft {

    @Data
    class Request {

      @Valid
      @NotNull
      ProductSpecificationId id;

      @Valid
      @NotNull
      ItemData item;

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
      UserData committer;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

}
