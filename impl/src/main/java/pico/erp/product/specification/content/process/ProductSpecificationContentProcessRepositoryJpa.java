package pico.erp.product.specification.content.process;

import java.util.Optional;
import java.util.stream.Stream;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pico.erp.product.specification.content.ProductSpecificationContentId;

@Repository
interface ProductSpecificationContentProcessEntityRepository extends
  CrudRepository<ProductSpecificationContentProcessEntity, ProductSpecificationContentProcessId> {

  @Query("SELECT p FROM ProductSpecificationContentProcess p WHERE p.contentId = :contentId")
  Stream<ProductSpecificationContentProcessEntity> findAllBy(
    @Param("contentId") ProductSpecificationContentId contentId);

}

@Repository
@Transactional
public class ProductSpecificationContentProcessRepositoryJpa implements
  ProductSpecificationContentProcessRepository {

  @Autowired
  private ProductSpecificationContentProcessEntityRepository repository;

  @Autowired
  private ProductSpecificationContentProcessMapper mapper;


  @Override
  public ProductSpecificationContentProcess create(
    ProductSpecificationContentProcess productSpecificationContentProcess) {
    val entity = mapper.jpa(productSpecificationContentProcess);
    val created = repository.save(entity);
    return mapper.jpa(created);
  }

  @Override
  public void deleteBy(ProductSpecificationContentProcessId id) {
    repository.delete(id);
  }

  @Override
  public boolean exists(ProductSpecificationContentProcessId id) {
    return repository.exists(id);
  }

  @Override
  public Stream<ProductSpecificationContentProcess> findAllBy(
    ProductSpecificationContentId contentId) {
    return repository.findAllBy(contentId)
      .map(mapper::jpa);
  }

  @Override
  public Optional<ProductSpecificationContentProcess> findBy(
    ProductSpecificationContentProcessId id) {
    return Optional.ofNullable(repository.findOne(id))
      .map(mapper::jpa);
  }

  @Override
  public void update(ProductSpecificationContentProcess productSpecificationContentProcess) {
    val entity = repository.findOne(productSpecificationContentProcess.getId());
    mapper.pass(mapper.jpa(productSpecificationContentProcess), entity);
    repository.save(entity);
  }

}
