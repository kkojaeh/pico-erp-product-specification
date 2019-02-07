package pico.erp.product.specification.content.process;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Value;
import pico.erp.process.ProcessData;
import pico.erp.process.info.ProcessInfo;
import pico.erp.product.specification.content.ProductSpecificationContent;
import pico.erp.shared.event.Event;

public interface ProductSpecificationContentProcessMessages {

  interface Create {

    @Data
    class Request {

      @Valid
      @NotNull
      ProductSpecificationContentProcessId id;

      @NotNull
      ProductSpecificationContent content;

      @NotNull
      ProcessData process;

      @NotNull
      ProcessInfo processInfo;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Update {

    @Data
    class Request {

      @NotNull
      ProcessInfo processInfo;
    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

}
