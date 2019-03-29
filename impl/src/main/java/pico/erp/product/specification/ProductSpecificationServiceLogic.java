package pico.erp.product.specification;

import kkojaeh.spring.boot.component.Give;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.item.ItemId;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Give
@Transactional
@Validated
public class ProductSpecificationServiceLogic implements ProductSpecificationService {

  @Autowired
  private ProductSpecificationRepository productSpecificationRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProductSpecificationMapper mapper;

  @Override
  public void commit(ProductSpecificationRequests.CommitRequest request) {
    val productSpecification = productSpecificationRepository.findBy(request.getId())
      .orElseThrow(ProductSpecificationExceptions.NotFoundException::new);
    val response = productSpecification.apply(mapper.map(request));
    productSpecificationRepository.update(productSpecification);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public ProductSpecificationData draft(ProductSpecificationRequests.DraftRequest request) {
    val exists = productSpecificationRepository.exists(request.getItemId());
    if (exists) {
      val productSpecification = productSpecificationRepository.findBy(request.getItemId()).get();
      val response = productSpecification.apply(mapper.mapNext(request));
      productSpecificationRepository.update(productSpecification);
      eventPublisher.publishEvents(response.getEvents());
      return mapper.map(productSpecification);
    } else {
      val productSpecification = new ProductSpecification();
      val response = productSpecification.apply(mapper.map(request));
      if (productSpecificationRepository.exists(productSpecification.getId())) {
        throw new ProductSpecificationExceptions.AlreadyExistsException();
      }
      val created = productSpecificationRepository.create(productSpecification);
      eventPublisher.publishEvents(response.getEvents());
      return mapper.map(created);
    }
  }


  @Override
  public boolean exists(ProductSpecificationId id) {
    return productSpecificationRepository.exists(id);
  }

  @Override
  public boolean exists(ItemId itemId) {
    return productSpecificationRepository.exists(itemId);
  }

  @Override
  public ProductSpecificationData get(ProductSpecificationId id) {
    return productSpecificationRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProductSpecificationExceptions.NotFoundException::new);
  }

  @Override
  public ProductSpecificationData get(ItemId itemId) {
    return productSpecificationRepository.findBy(itemId)
      .map(mapper::map)
      .orElseThrow(ProductSpecificationExceptions.NotFoundException::new);
  }

}
