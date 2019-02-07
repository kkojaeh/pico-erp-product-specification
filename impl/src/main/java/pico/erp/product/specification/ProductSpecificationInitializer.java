package pico.erp.product.specification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import pico.erp.shared.ApplicationInitializer;
import pico.erp.user.group.GroupService;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class ProductSpecificationInitializer implements ApplicationInitializer {

  @Lazy
  @Autowired
  GroupService groupService;

  @Autowired
  ProductSpecificationProperties properties;

  @Override
  public void initialize() {

  }
}
