package pico.erp.product.specification;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import pico.erp.attachment.category.AttachmentCategory;
import pico.erp.attachment.category.AttachmentCategory.AttachmentCategoryImpl;
import pico.erp.attachment.category.AttachmentCategoryId;
import pico.erp.audit.AuditConfiguration;
import pico.erp.company.CompanyApi;
import pico.erp.item.ItemApi;
import pico.erp.process.ProcessApi;
import pico.erp.product.specification.ProductSpecificationApi.Roles;
import pico.erp.shared.ApplicationId;
import pico.erp.shared.ApplicationStarter;
import pico.erp.shared.Public;
import pico.erp.shared.SpringBootConfigs;
import pico.erp.shared.data.Contact;
import pico.erp.shared.data.Role;
import pico.erp.shared.impl.ApplicationImpl;

@Slf4j
@SpringBootConfigs
public class ProductSpecificationApplication implements ApplicationStarter {

  public static final String CONFIG_NAME = "product-specification/application";

  public static final Properties DEFAULT_PROPERTIES = new Properties();

  static {
    DEFAULT_PROPERTIES.put("spring.config.name", CONFIG_NAME);
  }

  public static SpringApplication application() {
    return new SpringApplicationBuilder(ProductSpecificationApplication.class)
      .properties(DEFAULT_PROPERTIES)
      .web(false)
      .build();
  }

  public static void main(String[] args) {
    application().run(args);
  }

  @Bean
  @Public
  public AuditConfiguration auditConfiguration() {
    return AuditConfiguration.builder()
      .packageToScan("pico.erp.production.request")
      .entity(Roles.class)
      .valueObject(Contact.class)
      .build();
  }

  @Override
  public Set<ApplicationId> getDependencies() {
    return Stream.of(
      ItemApi.ID,
      ProcessApi.ID,
      CompanyApi.ID
    ).collect(Collectors.toSet());
  }

  @Public
  @Bean
  public AttachmentCategory bluePrintAttachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("product-specification-blue-print"),
      "품목 사양서 도면");
  }

  @Public
  @Bean
  public AttachmentCategory imageAttachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("product-specification-image"),
      "품목 사양서 이미지");
  }

  @Override
  public ApplicationId getId() {
    return ProductSpecificationApi.ID;
  }

  @Override
  public boolean isWeb() {
    return false;
  }

  @Bean
  @Public
  public Role productSpecificationAccessorRole() {
    return Roles.PRODUCT_SPECIFICATION_ACCESSOR;
  }

  @Bean
  @Public
  public Role productSpecificationManagerRole() {
    return Roles.PRODUCT_SPECIFICATION_MANAGER;
  }

  @Bean
  @Public
  public Role productSpecificationWriterRole() {
    return Roles.PRODUCT_SPECIFICATION_WRITER;
  }

  @Override
  public pico.erp.shared.Application start(String... args) {
    return new ApplicationImpl(application().run(args));
  }


}
