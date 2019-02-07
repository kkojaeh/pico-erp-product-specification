package pico.erp.product.specification;

import java.util.Optional;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.item.ItemId;

@Repository
interface ProductionRequestEntityRepository extends
  CrudRepository<ProductSpecificationEntity, ProductSpecificationId> {

  @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM ProductSpecification s WHERE s.itemId = :itemId")
  boolean exists(@Param("itemId") ItemId itemId);

  @Query("SELECT s FROM ProductSpecification s WHERE s.itemId = :itemId")
  ProductSpecificationEntity findBy(@Param("itemId") ItemId itemId);

}

@Repository
@Transactional
public class ProductSpecificationRepositoryJpa implements ProductSpecificationRepository {

  @Autowired
  private ProductionRequestEntityRepository repository;

  @Autowired
  private ProductSpecificationMapper mapper;


  @Override
  public ProductSpecification create(ProductSpecification productSpecification) {
    val entity = mapper.jpa(productSpecification);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProductSpecificationId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProductSpecificationId id) {
    return repository.exists(id);
  }

  @Override
  public boolean exists(ItemId itemId) {
    return repository.exists(itemId);
  }

  @Override
  public Optional<ProductSpecification> findBy(ProductSpecificationId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public Optional<ProductSpecification> findBy(ItemId itemId) {
    return Optional.ofNullable(repository.findBy(itemId))
      .map(mapper::jpa);
  }

  @Override
  public void update(ProductSpecification productSpecification) {
    val entity = repository.findOne(productSpecification.getId());
    mapper.pass(mapper.jpa(productSpecification), entity);
    repository.save(entity);
  }

}
