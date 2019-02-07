package pico.erp.product.specification.content.process;

import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import pico.erp.product.specification.content.ProductSpecificationContentId;

@Repository
public interface ProductSpecificationContentProcessRepository {

  ProductSpecificationContentProcess create(
    @NotNull ProductSpecificationContentProcess productSpecificationContentProcess);

  void deleteBy(@NotNull ProductSpecificationContentProcessId id);

  boolean exists(@NotNull ProductSpecificationContentProcessId id);

  Stream<ProductSpecificationContentProcess> findAllBy(
    @NotNull ProductSpecificationContentId contentId);

  Optional<ProductSpecificationContentProcess> findBy(
    @NotNull ProductSpecificationContentProcessId id);

  void update(@NotNull ProductSpecificationContentProcess productSpecificationContentProcess);

}
