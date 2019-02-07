package pico.erp.product.specification;

import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import pico.erp.item.ItemData;
import pico.erp.item.ItemId;
import pico.erp.item.ItemService;
import pico.erp.user.UserData;
import pico.erp.user.UserId;
import pico.erp.user.UserService;

@Mapper
public abstract class ProductSpecificationMapper {

  @Lazy
  @Autowired
  protected ItemService itemService;

  @Lazy
  @Autowired
  private UserService userService;

  @Lazy
  @Autowired
  private ProductSpecificationRepository productSpecificationRepository;

  @Mappings({
    @Mapping(target = "itemId", source = "item.id"),
    @Mapping(target = "createdBy", ignore = true),
    @Mapping(target = "createdDate", ignore = true),
    @Mapping(target = "lastModifiedBy", ignore = true),
    @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract ProductSpecificationEntity jpa(ProductSpecification data);

  public ProductSpecification jpa(ProductSpecificationEntity entity) {
    return ProductSpecification.builder()
      .id(entity.getId())
      .item(map(entity.getItemId()))
      .status(entity.getStatus())
      .contentId(entity.getContentId())
      .revision(entity.getRevision())
      .build();
  }

  protected UserData map(UserId userId) {
    return Optional.ofNullable(userId)
      .map(userService::get)
      .orElse(null);
  }

  public ProductSpecification map(ProductSpecificationId productSpecificationId) {
    return Optional.ofNullable(productSpecificationId)
      .map(id -> productSpecificationRepository.findBy(id)
        .orElseThrow(ProductSpecificationExceptions.NotFoundException::new)
      )
      .orElse(null);
  }

  protected ItemData map(ItemId itemId) {
    return Optional.ofNullable(itemId)
      .map(itemService::get)
      .orElse(null);
  }

  @Mappings({
    @Mapping(target = "itemId", source = "item.id")
  })
  public abstract ProductSpecificationData map(ProductSpecification domain);

  @Mappings({
    @Mapping(target = "committer", source = "committerId")
  })
  public abstract ProductSpecificationMessages.Commit.Request map(
    ProductSpecificationRequests.CommitRequest request);


  @Mappings({
    @Mapping(target = "item", source = "itemId")
  })
  public abstract ProductSpecificationMessages.Draft.Request map(
    ProductSpecificationRequests.DraftRequest request);

  @Mappings({
  })
  public abstract ProductSpecificationMessages.NextDraft.Request mapNext(
    ProductSpecificationRequests.DraftRequest request);


  public abstract void pass(ProductSpecificationEntity from,
    @MappingTarget ProductSpecificationEntity to);


}


