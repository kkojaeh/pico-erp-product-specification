package pico.erp.product.specification.content.process;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import pico.erp.process.ProcessData;
import pico.erp.process.info.ProcessInfo;
import pico.erp.product.specification.content.ProductSpecificationContent;

/**
 * 주문 접수
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductSpecificationContentProcess implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  ProductSpecificationContentProcessId id;

  ProductSpecificationContent content;

  ProcessData process;

  ProcessInfo processInfo;

  public ProductSpecificationContentProcess() {

  }

  public ProductSpecificationContentProcessMessages.Create.Response apply(
    ProductSpecificationContentProcessMessages.Create.Request request) {
    this.id = request.getId();
    this.content = request.getContent();
    this.process = request.getProcess();
    this.processInfo = request.getProcessInfo();
    return new ProductSpecificationContentProcessMessages.Create.Response(
      Arrays.asList(new ProductSpecificationContentProcessEvents.CreatedEvent(this.id))
    );
  }

  public ProductSpecificationContentProcessMessages.Update.Response apply(
    ProductSpecificationContentProcessMessages.Update.Request request) {
    if (!isUpdatable()) {
      throw new ProductSpecificationContentProcessExceptions.CannotUpdateException();
    }
    this.processInfo = request.getProcessInfo();
    return new ProductSpecificationContentProcessMessages.Update.Response(
      Arrays.asList(new ProductSpecificationContentProcessEvents.UpdatedEvent(this.id))
    );
  }


  public boolean isUpdatable() {
    return content.isUpdatable();
  }

}
