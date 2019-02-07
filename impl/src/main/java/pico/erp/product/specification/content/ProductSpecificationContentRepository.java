package pico.erp.product.specification.content;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecificationContentRepository {

  ProductSpecificationContent create(
    @NotNull ProductSpecificationContent productSpecificationContent);

  void deleteBy(@NotNull ProductSpecificationContentId id);

  boolean exists(@NotNull ProductSpecificationContentId id);

  Optional<ProductSpecificationContent> findBy(@NotNull ProductSpecificationContentId id);

  void update(@NotNull ProductSpecificationContent productSpecificationContent);

}
