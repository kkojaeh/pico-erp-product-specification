package pico.erp.product.specification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kkojaeh.spring.boot.component.ComponentAutowired;
import kkojaeh.spring.boot.component.ComponentBean;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import pico.erp.attachment.item.AttachmentItemRequests;
import pico.erp.attachment.item.AttachmentItemService;
import pico.erp.company.CompanyService;
import pico.erp.document.context.DocumentContextFactory;
import pico.erp.document.subject.DocumentSubjectDefinition;
import pico.erp.document.subject.DocumentSubjectId;
import pico.erp.item.ItemService;
import pico.erp.process.ProcessService;
import pico.erp.product.specification.content.ProductSpecificationContentService;
import pico.erp.product.specification.content.process.ProductSpecificationContentProcessService;
import pico.erp.shared.data.ContentInputStream;

@ComponentBean
@Component
public class ProductSpecificationDocumentSubjectDefinition implements
  DocumentSubjectDefinition<ProductSpecificationId, Object> {

  public static DocumentSubjectId ID = DocumentSubjectId.from("product-specification");

  @Getter
  DocumentSubjectId id = ID;

  @Getter
  String name = "[product-specification] 제품 사양서";

  @ComponentAutowired
  private DocumentContextFactory contextFactory;

  @ComponentAutowired
  private CompanyService companyService;

  @Autowired
  private ProductSpecificationContentProcessService productSpecificationContentProcessService;

  @Autowired
  private ProductSpecificationService productSpecificationService;

  @Autowired
  private ProductSpecificationContentService productSpecificationContentService;

  @ComponentAutowired
  private AttachmentItemService attachmentItemService;

  @ComponentAutowired
  private ProcessService processService;

  @ComponentAutowired
  private ItemService itemService;


  @Override
  public Object getContext(ProductSpecificationId key) {
    val locale = LocaleContextHolder.getLocale();
    val context = contextFactory.factory();
    val data = context.getData();
    val spec = productSpecificationService.get(key);

    val content = productSpecificationContentService.get(spec.getContentId());

    if (content.getBluePrintId() != null) {
      List<Map<String, Object>> bluePrints = attachmentItemService.getAll(content.getBluePrintId())
        .stream()
        .filter(item -> item.getContentType().startsWith("image/"))
        .map(item -> {
          val encoded = context.getContentEncoder().apply(
            ContentInputStream.builder()
              .contentLength(item.getContentLength())
              .contentType(item.getContentType())
              .name(item.getName())
              .inputStream(
                attachmentItemService.access(
                  new AttachmentItemRequests.DirectAccessRequest(item.getId())
                )
              ).build()
          );
          val map = new HashMap<String, Object>();
          map.put("name", item.getName());
          map.put("data", encoded);
          return map;
        })
        .collect(Collectors.toList());
      data.put("bluePrints", bluePrints);
    }

    if (content.getImageId() != null) {
      List<Map<String, Object>> images = attachmentItemService.getAll(content.getImageId()).stream()
        .filter(item -> item.getContentType().startsWith("image/"))
        .map(item -> {
          val encoded = context.getContentEncoder().apply(
            ContentInputStream.builder()
              .contentLength(item.getContentLength())
              .contentType(item.getContentType())
              .name(item.getName())
              .inputStream(
                attachmentItemService.access(
                  new AttachmentItemRequests.DirectAccessRequest(item.getId())
                )
              ).build()
          );
          val map = new HashMap<String, Object>();
          map.put("name", item.getName());
          map.put("data", encoded);
          return map;
        })
        .collect(Collectors.toList());
      data.put("images", images);
    }

    List<Map<String, Object>> processes = productSpecificationContentProcessService
      .getAll(content.getId()).stream()
      .map(p -> {
        val map = new HashMap<String, Object>();
        map.put("process", processService.get(p.getProcessId()));
        map.put("properties", p.getProcessInfo().getDisplayProperties().entrySet());
        return map;
      })
      .collect(Collectors.toList());

    data.put("content", content);
    data.put("processes", processes);
    data.put("item", itemService.get(spec.getItemId()));
    return context;
  }

  @Override
  public ProductSpecificationId getKey(String key) {
    return ProductSpecificationId.from(key);
  }

}
