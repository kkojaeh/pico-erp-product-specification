package pico.erp.product.specification.content.process;

import java.util.Optional;
import lombok.val;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.process.ProcessData;
import pico.erp.process.ProcessId;
import pico.erp.process.ProcessService;
import pico.erp.process.info.ProcessInfoLifecycler;
import pico.erp.process.type.ProcessTypeService;
import pico.erp.product.specification.content.ProductSpecificationContent;
import pico.erp.product.specification.content.ProductSpecificationContentId;
import pico.erp.product.specification.content.ProductSpecificationContentMapper;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class ProductSpecificationContentProcessMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @Lazy
  @Autowired
  protected ProcessInfoLifecycler processInfoLifecycler;

  @Lazy
  @Autowired
  private ProductSpecificationContentProcessRepository productSpecificationContentProcessRepository;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private ProcessService processService;

  @Lazy
  @Autowired
  private ProcessTypeService processTypeService;

  @Lazy
  @Autowired
  private ProductSpecificationContentMapper specificationContentMapper;

  @AfterMapping
  protected void afterMapping(ProductSpecificationContentProcess domain,
    @MappingTarget ProductSpecificationContentProcessEntity entity) {
    val processType = processTypeService.get(domain.getProcess().getTypeId());
    entity.setProcessInfo(
      processInfoLifecycler.stringify(processType.getInfoTypeId(), domain.getProcessInfo())
    );
  }

  @Mappings({
    @Mapping(target = "contentId", source = "content.id"),
    @Mapping(target = "processId", source = "process.id"),
    @Mapping(target = "processInfo", ignore = true),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProductSpecificationContentProcessEntity jpa(
    ProductSpecificationContentProcess data);

  public ProductSpecificationContentProcess jpa(ProductSpecificationContentProcessEntity entity) {
    val process = map(entity.getProcessId());
    val processType = processTypeService.get(process.getTypeId());
    val processInfo = processInfoLifecycler
      .parse(processType.getInfoTypeId(), entity.getProcessInfo());
    return ProductSpecificationContentProcess.builder()
      .id(entity.getId())
      .content(map(entity.getContentId()))
      .process(process)
      .processInfo(processInfo)
      .build();
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected ProcessData map(ProcessId processId) {
    return Optional.ofNullable(processId)
      .map(processService::get)
      .orElse(null);
  }

  protected ProductSpecificationContent map(ProductSpecificationContentId specificationContentId) {
    return specificationContentMapper.map(specificationContentId);
  }

  public ProductSpecificationContentProcess map(
    ProductSpecificationContentProcessId productSpecificationContentProcessId) {
    return Optional.ofNullable(productSpecificationContentProcessId)
      .map(id -> productSpecificationContentProcessRepository.findBy(id)
        .orElseThrow(ProductSpecificationContentProcessExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "contentId", source = "content.id"),
    @Mapping(target = "processId", source = "process.id")
  })
  public abstract ProductSpecificationContentProcessData map(
    ProductSpecificationContentProcess domain);

  @Mappings({
    @Mapping(target = "content", source = "contentId"),
    @Mapping(target = "process", source = "processId")
  })
  public abstract ProductSpecificationContentProcessMessages.Create.Request map(
    ProductSpecificationContentProcessRequests.CreateRequest request);

  @Mappings({
  })
  public abstract ProductSpecificationContentProcessMessages.Update.Request map(
    ProductSpecificationContentProcessRequests.UpdateRequest request);

  public abstract void pass(ProductSpecificationContentProcessEntity from,
    @MappingTarget ProductSpecificationContentProcessEntity to);


}


