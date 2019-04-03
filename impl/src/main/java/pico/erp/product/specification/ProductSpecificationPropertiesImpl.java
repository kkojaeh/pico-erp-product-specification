package pico.erp.product.specification;

import kkojaeh.spring.boot.component.ComponentBean;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ComponentBean
@Data
@Configuration
@ConfigurationProperties("product-specification")
public class ProductSpecificationPropertiesImpl implements ProductSpecificationProperties {

}
