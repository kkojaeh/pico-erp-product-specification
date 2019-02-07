package pico.erp.product.specification;

import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.item.ItemId;

@Repository
public interface ProductSpecificationRepository {

  ProductSpecification create(@NotNull ProductSpecification productSpecification);

  void deleteBy(@NotNull ProductSpecificationId id);

  boolean exists(@NotNull ProductSpecificationId id);

  boolean exists(@NotNull ItemId itemId);

  Optional<ProductSpecification> findBy(@NotNull ProductSpecificationId id);

  Optional<ProductSpecification> findBy(@NotNull ItemId itemId);

  void update(@NotNull ProductSpecification productSpecification);

}
