package pico.erp.product.specification.content;

import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSpecificationContentQuery {

  Page<ProductSpecificationContentView> retrieve(
    @NotNull ProductSpecificationContentView.Filter filter,
    @NotNull Pageable pageable);

}
