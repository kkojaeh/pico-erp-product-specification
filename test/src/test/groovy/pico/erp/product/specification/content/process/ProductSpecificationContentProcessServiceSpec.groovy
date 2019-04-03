package pico.erp.product.specification.content.process

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.product.specification.ProductSpecificationApplication
import pico.erp.product.specification.ProductSpecificationId
import pico.erp.product.specification.ProductSpecificationService
import pico.erp.product.specification.TestConfig
import pico.erp.product.specification.content.ProductSpecificationContentService
import pico.erp.shared.ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier
import pico.erp.shared.TestParentApplication
import spock.lang.Specification

@SpringBootTest(classes = [ProductSpecificationApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblingsSupplier = ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier.class)
@Transactional
@Rollback
@ActiveProfiles("test")
class ProductSpecificationContentProcessServiceSpec extends Specification {

  @Autowired
  ProductSpecificationContentProcessService productSpecificationContentProcessService

  @Autowired
  ProductSpecificationService productSpecificationService

  @Autowired
  ProductSpecificationContentService productSpecificationContentService

  def specificationId = ProductSpecificationId.from("toothbrush-05-specification-1")

  def contentId

  def id

  def unknownId = ProductSpecificationContentProcessId.from("unknown")

  def setup() {
    contentId = productSpecificationService.get(specificationId).contentId
    def processes = productSpecificationContentProcessService.getAll(contentId)
    println processes
    id = processes[0].id
  }

  def get() {
    productSpecificationContentProcessService.get(id)
  }


  def "존재 - 아이디로 존재 확인"() {
    when:
    def exists = productSpecificationContentProcessService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = productSpecificationContentProcessService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def process = get()

    then:
    process.processInfo != null
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    productSpecificationContentProcessService.get(unknownId)

    then:
    thrown(ProductSpecificationContentProcessExceptions.NotFoundException)
  }


}
