package pico.erp.product.specification;

import kkojaeh.spring.boot.component.ComponentBean;
import kkojaeh.spring.boot.component.SpringBootComponent;
import kkojaeh.spring.boot.component.SpringBootComponentBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pico.erp.ComponentDefinition;
import pico.erp.attachment.category.AttachmentCategory;
import pico.erp.attachment.category.AttachmentCategory.AttachmentCategoryImpl;
import pico.erp.attachment.category.AttachmentCategoryId;
import pico.erp.product.specification.ProductSpecificationApi.Roles;
import pico.erp.shared.SharedConfiguration;
import pico.erp.shared.data.Role;

@Slf4j
@SpringBootComponent("product-specification")
@EntityScan
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
@SpringBootApplication
@Import(value = {
  SharedConfiguration.class
})
public class ProductSpecificationApplication implements ComponentDefinition {


  public static void main(String[] args) {
    new SpringBootComponentBuilder()
      .component(ProductSpecificationApplication.class)
      .run(args);
  }

  @Bean
  @ComponentBean(host = false)
  public AttachmentCategory bluePrintAttachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("product-specification-blue-print"),
      "품목 사양서 도면");
  }

  @Override
  public Class<?> getComponentClass() {
    return ProductSpecificationApplication.class;
  }

  @Bean
  @ComponentBean(host = false)
  public AttachmentCategory imageAttachmentCategory() {
    return new AttachmentCategoryImpl(AttachmentCategoryId.from("product-specification-image"),
      "품목 사양서 이미지");
  }

  @Bean
  @ComponentBean(host = false)
  public Role productSpecificationAccessorRole() {
    return Roles.PRODUCT_SPECIFICATION_ACCESSOR;
  }

  @Bean
  @ComponentBean(host = false)
  public Role productSpecificationManagerRole() {
    return Roles.PRODUCT_SPECIFICATION_MANAGER;
  }

  @Bean
  @ComponentBean(host = false)
  public Role productSpecificationWriterRole() {
    return Roles.PRODUCT_SPECIFICATION_WRITER;
  }

}
