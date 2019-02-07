package pico.erp.product.specification.content;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
interface ProductSpecificationContentEntityRepository extends
  CrudRepository<ProductSpecificationContentEntity, ProductSpecificationContentId> {

}

@Repository
@Transactional
public class ProductSpecificationContentRepositoryJpa implements
  ProductSpecificationContentRepository {

  @Autowired
  private ProductSpecificationContentEntityRepository repository;

  @Autowired
  private ProductSpecificationContentMapper mapper;


  @Override
  public ProductSpecificationContent create(
    ProductSpecificationContent productSpecificationContent) {
    val entity = mapper.jpa(productSpecificationContent);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProductSpecificationContentId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProductSpecificationContentId id) {
    return repository.exists(id);
  }

  @Override
  public Optional<ProductSpecificationContent> findBy(ProductSpecificationContentId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(ProductSpecificationContent productSpecificationContent) {
    val entity = repository.findOne(productSpecificationContent.getId());
    mapper.pass(mapper.jpa(productSpecificationContent), entity);
    repository.save(entity);
  }

}
