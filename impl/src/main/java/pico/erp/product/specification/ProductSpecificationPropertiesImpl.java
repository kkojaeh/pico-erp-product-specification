package pico.erp.product.specification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pico.erp.shared.Public;
import pico.erp.user.group.GroupData;

@Public
@Data
@Configuration
@ConfigurationProperties("product-specification")
public class ProductSpecificationPropertiesImpl implements ProductSpecificationProperties {

  GroupData accepterGroup;

}
