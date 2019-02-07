package pico.erp.product.specification.content;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.product.specification.content.ProductSpecificationContentRequests.CommitRequest;
import pico.erp.shared.Public;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@Public
@Transactional
@Validated
public class ProductSpecificationContentServiceLogic implements ProductSpecificationContentService {

  @Autowired
  private ProductSpecificationContentRepository productSpecificationContentRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProductSpecificationContentMapper mapper;

  @Override
  public void commit(CommitRequest request) {
    val content = productSpecificationContentRepository.findBy(request.getId())
      .orElseThrow(ProductSpecificationContentExceptions.NotFoundException::new);
    val response = content.apply(mapper.map(request));
    productSpecificationContentRepository.update(content);
    eventPublisher.publishEvents(response.getEvents());
  }

  @Override
  public ProductSpecificationContentData create(
    ProductSpecificationContentRequests.CreateRequest request) {
    val content = new ProductSpecificationContent();
    val response = content.apply(mapper.map(request));
    if (productSpecificationContentRepository.exists(content.getId())) {
      throw new ProductSpecificationContentExceptions.AlreadyExistsException();
    }
    val created = productSpecificationContentRepository.create(content);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public boolean exists(ProductSpecificationContentId id) {
    return productSpecificationContentRepository.exists(id);
  }

  @Override
  public ProductSpecificationContentData get(ProductSpecificationContentId id) {
    return productSpecificationContentRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProductSpecificationContentExceptions.NotFoundException::new);
  }

  @Override
  public void update(ProductSpecificationContentRequests.UpdateRequest request) {
    val content = productSpecificationContentRepository.findBy(request.getId())
      .orElseThrow(ProductSpecificationContentExceptions.NotFoundException::new);
    val response = content.apply(mapper.map(request));
    productSpecificationContentRepository.update(content);
    eventPublisher.publishEvents(response.getEvents());
  }
}
