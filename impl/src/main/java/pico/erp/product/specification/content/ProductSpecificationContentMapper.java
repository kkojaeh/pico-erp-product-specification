package pico.erp.product.specification.content;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.AuditorAware;
import pico.erp.product.specification.ProductSpecification;
import pico.erp.product.specification.ProductSpecificationId;
import pico.erp.product.specification.ProductSpecificationMapper;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class ProductSpecificationContentMapper {

  @Autowired
  protected AuditorAware<Auditor> auditorAware;

  @Lazy
  @Autowired
  private ProductSpecificationContentRepository productSpecificationContentRepository;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private ProductSpecificationMapper specificationMapper;

  @Mappings({
    @Mapping(target = "specificationId", source = "specification.id"),
    @Mapping(target = "committerId", source = "committer.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProductSpecificationContentEntity jpa(ProductSpecificationContent data);

  public ProductSpecificationContent jpa(ProductSpecificationContentEntity entity) {
    return ProductSpecificationContent.builder()
      .id(entity.getId())
      .specification(map(entity.getSpecificationId()))
      .revision(entity.getRevision())
      .imageId(entity.getImageId())
      .bluePrintId(entity.getBluePrintId())
      .description(entity.getDescription())
      .committer(map(entity.getCommitterId()))
      .committedDate(entity.getCommittedDate())
      .barcodeNumber(entity.getBarcodeNumber())
      .build();
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  protected ProductSpecification map(ProductSpecificationId specificationId) {
    return specificationMapper.map(specificationId);
  }

  public ProductSpecificationContent map(
    ProductSpecificationContentId productSpecificationContentId) {
    return Optional.ofNullable(productSpecificationContentId)
      .map(id -> productSpecificationContentRepository.findBy(id)
        .orElseThrow(ProductSpecificationContentExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "specificationId", source = "specification.id"),
    @Mapping(target = "committerId", source = "committer.id")
  })
  public abstract ProductSpecificationContentData map(ProductSpecificationContent domain);

  @Mappings({
    @Mapping(target = "committer", source = "committerId")
  })
  public abstract ProductSpecificationContentMessages.Commit.Request map(
    ProductSpecificationContentRequests.CommitRequest request);


  @Mappings({
    @Mapping(target = "specification", source = "specificationId")
  })
  public abstract ProductSpecificationContentMessages.Create.Request map(
    ProductSpecificationContentRequests.CreateRequest request);

  @Mappings({
  })
  public abstract ProductSpecificationContentMessages.Update.Request map(
    ProductSpecificationContentRequests.UpdateRequest request);

  public abstract void pass(ProductSpecificationContentEntity from,
    @MappingTarget ProductSpecificationContentEntity to);


}


