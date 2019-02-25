package pico.erp.product.specification.content;

import java.util.Collection;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import pico.erp.attachment.AttachmentId;
import pico.erp.document.DocumentId;
import pico.erp.product.specification.ProductSpecification;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.event.Event;
import pico.erp.user.UserId;

public interface ProductSpecificationContentMessages {

  interface Create {

    @Data
    class Request {

      @Valid
      @NotNull
      ProductSpecificationContentId id;

      @NotNull
      ProductSpecification specification;

      @Min(1)
      int revision;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Update {

    @Data
    class Request {

      @Valid
      AttachmentId imageId;

      @Valid
      AttachmentId bluePrintId;

      @Size(max = TypeDefinitions.CLOB_LENGTH)
      String description;
    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

  interface Commit {

    @Data
    class Request {

      @NotNull
      UserId committerId;

      @NotNull
      DocumentId documentId;

    }

    @Value
    class Response {

      Collection<Event> events;

    }

  }

}
