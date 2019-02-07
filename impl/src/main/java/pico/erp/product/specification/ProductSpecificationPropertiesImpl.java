package pico.erp.product.specification;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import pico.erp.shared.Public;

@Public
@Data
@Configuration
@ConfigurationProperties("product-specification")
public class ProductSpecificationPropertiesImpl implements ProductSpecificationProperties {

}
