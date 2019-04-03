package pico.erp.product.specification.content.process;

import java.util.List;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.product.specification.content.ProductSpecificationContentId;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional
@Validated
public class ProductSpecificationContentProcessServiceLogic implements
  ProductSpecificationContentProcessService {

  @Autowired
  private ProductSpecificationContentProcessRepository productSpecificationContentProcessRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProductSpecificationContentProcessMapper mapper;

  @Override
  public ProductSpecificationContentProcessData create(
    ProductSpecificationContentProcessRequests.CreateRequest request) {
    val contentProcess = new ProductSpecificationContentProcess();
    val response = contentProcess.apply(mapper.map(request));
    if (productSpecificationContentProcessRepository.exists(contentProcess.getId())) {
      throw new ProductSpecificationContentProcessExceptions.AlreadyExistsException();
    }
    val created = productSpecificationContentProcessRepository.create(contentProcess);
    eventPublisher.publishEvents(response.getEvents());
    return mapper.map(created);
  }

  @Override
  public boolean exists(ProductSpecificationContentProcessId id) {
    return productSpecificationContentProcessRepository.exists(id);
  }

  @Override
  public ProductSpecificationContentProcessData get(ProductSpecificationContentProcessId id) {
    return productSpecificationContentProcessRepository.findBy(id)
      .map(mapper::map)
      .orElseThrow(ProductSpecificationContentProcessExceptions.NotFoundException::new);
  }

  @Override
  public List<ProductSpecificationContentProcessData> getAll(
    ProductSpecificationContentId contentId) {
    return productSpecificationContentProcessRepository.findAllBy(contentId)
      .map(mapper::map)
      .collect(Collectors.toList());
  }

  @Override
  public void update(ProductSpecificationContentProcessRequests.UpdateRequest request) {
    val contentProcess = productSpecificationContentProcessRepository.findBy(request.getId())
      .orElseThrow(ProductSpecificationContentProcessExceptions.NotFoundException::new);
    val response = contentProcess.apply(mapper.map(request));
    productSpecificationContentProcessRepository.update(contentProcess);
    eventPublisher.publishEvents(response.getEvents());
  }

}
