package pico.erp.product.specification.content

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import pico.erp.item.ItemId
import pico.erp.product.specification.ProductSpecificationExceptions
import pico.erp.product.specification.ProductSpecificationId
import pico.erp.product.specification.ProductSpecificationRequests
import pico.erp.product.specification.ProductSpecificationService
import pico.erp.shared.IntegrationConfiguration
import pico.erp.user.UserId
import spock.lang.Specification

@SpringBootTest(classes = [IntegrationConfiguration])
@Transactional
@Rollback
@ActiveProfiles("test")
@Configuration
@ComponentScan("pico.erp.config")
class ProductSpecificationContentServiceSpec extends Specification {

  @Autowired
  ProductSpecificationService productSpecificationService

  @Autowired
  ProductSpecificationContentService productSpecificationContentService

  def specificationId = ProductSpecificationId.from("toothbrush-05-specification-1")

  def id

  def unknownId = ProductSpecificationContentId.from("unknown")

  def itemId = ItemId.from("toothbrush-05")

  def committerId = UserId.from("kjh")

  def description = """
이 품목은 테스트 품목입니다
"""


  def setup() {
    id = productSpecificationService.get(specificationId).contentId
  }

  def nextDraft() {
    productSpecificationService.draft(
      new ProductSpecificationRequests.DraftRequest(
        id: specificationId,
        itemId: itemId
      )
    )
  }


  def commit() {
    productSpecificationService.commit(
      new ProductSpecificationRequests.CommitRequest(
        id: specificationId,
        committerId: committerId
      )
    )
  }

  def update() {
    productSpecificationContentService.update(
      new ProductSpecificationContentRequests.UpdateRequest(
        id: id,
        description: description
      )
    )
  }


  def get() {
    productSpecificationContentService.get(id)
  }

  def getCurrent() {
    def contentId = productSpecificationService.get(specificationId).contentId
    productSpecificationContentService.get(contentId)
  }

  def "존재 - 아이디로 존재 확인"() {
    when:
    def exists = productSpecificationContentService.exists(id)

    then:
    exists == true
  }

  def "존재 - 존재하지 않는 아이디로 확인"() {
    when:
    def exists = productSpecificationContentService.exists(unknownId)

    then:
    exists == false
  }

  def "조회 - 아이디로 조회"() {
    when:
    def content = get()

    then:
    content.specificationId == specificationId
    content.revision == 1
  }

  def "조회 - 존재하지 않는 아이디로 조회"() {
    when:
    productSpecificationContentService.get(unknownId)

    then:
    thrown(ProductSpecificationContentExceptions.NotFoundException)
  }

  def "재작성 - 제출 후 재작성"() {
    when:
    commit()
    nextDraft()
    def content = getCurrent()

    then:
    content.revision == 2
    content.id != id
  }

  def "수정 - 제출 전 수정"() {
    when:
    update()
    def content = get()

    then:
    content.description == description
  }

  def "수정 - 제출 후 수정"() {
    when:
    commit()
    update()

    then:
    thrown(ProductSpecificationContentExceptions.CannotUpdateException)
  }


}
