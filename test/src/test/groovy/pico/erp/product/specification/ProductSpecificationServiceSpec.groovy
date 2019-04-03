package pico.erp.product.specification

import kkojaeh.spring.boot.component.SpringBootTestComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.item.ItemId
import pico.erp.shared.ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier
import pico.erp.shared.TestParentApplication
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [ProductSpecificationApplication, TestConfig])
@SpringBootTestComponent(parent = TestParentApplication, siblingsSupplier = ComponentDefinitionServiceLoaderTestComponentSiblingsSupplier.class)
@Transactional
@Rollback
@ActiveProfiles("test")
class ProductSpecificationServiceSpec extends Specification {

  @Autowired
  ProductSpecificationService productSpecificationService

  def id = ProductSpecificationId.from("toothbrush-0-specification-1")

  def unknownId = ProductSpecificationId.from("unknown")

  def itemId = ItemId.from("toothbrush-0")

  def committerId = UserId.from("kjh")


  def setup() {
    productSpecificationService.draft(
      new ProductSpecificationRequests.DraftRequest(
        id: id,
        itemId: itemId
      )
    )
  }

  def nextDraft() {
    productSpecificationService.draft(
      new ProductSpecificationRequests.DraftRequest(
        id: id,
        itemId: itemId
      )
    )
  }


  def commitRequest() {
    productSpecificationService.commit(
      new ProductSpecificationRequests.CommitRequest(
        id: id,
        committerId: committerId
      )
    )
  }


  def get() {
    productSpecificationService.get(id)
  }

  def "존재 - 아이디로 존재 확인"() {
    when:
    def exists = productSpecificationService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = productSpecificationService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def specification = get()

    then:
    specification.itemId == itemId
    specification.revision == 1
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    productSpecificationService.get(unknownId)

    then:
    thrown(ProductSpecificationExceptions.NotFoundException)
  }

  def "재작성 - 제출 전 재작성"() {
    when:
    nextDraft()

    then:
    thrown(ProductSpecificationExceptions.CannotNextDraftException)
  }

  def "재작성 - 제출 후 재작성"() {
    when:
    commitRequest()
    nextDraft()
    def specification = get()

    then:
    specification.revision == 2
  }


}
