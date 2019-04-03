package pico.erp.product.specification.content;

import kkojaeh.spring.boot.component.ComponentAutowired;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import pico.erp.document.DocumentId;
import pico.erp.document.DocumentRequests;
import pico.erp.document.DocumentService;
import pico.erp.item.ItemService;
import pico.erp.product.specification.ProductSpecificationDocumentSubjectDefinition;
import pico.erp.product.specification.content.ProductSpecificationContentRequests.CommitRequest;
import pico.erp.shared.event.EventPublisher;

@SuppressWarnings("Duplicates")
@Service
@ComponentBean
@Transactional
@Validated
public class ProductSpecificationContentServiceLogic implements ProductSpecificationContentService {

  @Autowired
  private ProductSpecificationContentRepository productSpecificationContentRepository;

  @Autowired
  private EventPublisher eventPublisher;

  @Autowired
  private ProductSpecificationContentMapper mapper;

  @ComponentAutowired
  private DocumentService documentService;

  @ComponentAutowired
  private ItemService itemService;

  @Override
  public void commit(CommitRequest request) {
    val content = productSpecificationContentRepository.findBy(request.getId())
      .orElseThrow(ProductSpecificationContentExceptions.NotFoundException::new);
    val message = mapper.map(request);
    if (content.isCommittable()) {
      val documentId = DocumentId.generate();
      val item = itemService.get(content.getSpecification().getItemId());
      documentService.create(
        DocumentRequests.CreateRequest.builder()
          .id(documentId)
          .subjectId(ProductSpecificationDocumentSubjectDefinition.ID)
          .name(String.format("SPEC-%s-%s", item.getCode().getValue(), item.getName()))
          .key(content.getSpecification().getId())
          .creatorId(request.getCommitterId())
          .build()
      );
      message.setDocumentId(documentId);
    }
    val response = content.apply(message);
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
