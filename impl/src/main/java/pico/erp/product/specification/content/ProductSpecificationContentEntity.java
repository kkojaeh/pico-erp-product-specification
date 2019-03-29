package pico.erp.product.specification.content;


import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pico.erp.attachment.AttachmentId;
import pico.erp.document.DocumentId;
import pico.erp.product.specification.ProductSpecificationId;
import pico.erp.shared.TypeDefinitions;
import pico.erp.shared.data.Auditor;
import pico.erp.user.UserId;

@Entity(name = "ProductSpecificationContent")
@Table(name = "PSP_PRODUCT_SPECIFICATION_CONTENT", indexes = {
  @Index(columnList = "SPECIFICATION_ID")
})
@Data
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSpecificationContentEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @EmbeddedId
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProductSpecificationContentId id;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "SPECIFICATION_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  ProductSpecificationId specificationId;

  int revision;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "IMAGE_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  AttachmentId imageId;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "BLUE_PRINT_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  AttachmentId bluePrintId;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "CREATED_BY_ID", updatable = false, length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "CREATED_BY_NAME", updatable = false, length = TypeDefinitions.NAME_LENGTH))
  })
  @CreatedBy
  Auditor createdBy;

  @CreatedDate
  @Column(updatable = false)
  LocalDateTime createdDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "id", column = @Column(name = "LAST_MODIFIED_BY_ID", length = TypeDefinitions.ID_LENGTH)),
    @AttributeOverride(name = "name", column = @Column(name = "LAST_MODIFIED_BY_NAME", length = TypeDefinitions.NAME_LENGTH))
  })
  @LastModifiedBy
  Auditor lastModifiedBy;

  @LastModifiedDate
  LocalDateTime lastModifiedDate;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "COMMITTER_ID", length = TypeDefinitions.ID_LENGTH)),
  })
  UserId committerId;

  @Column
  LocalDateTime committedDate;

  @Lob
  @Column(length = TypeDefinitions.CLOB_LENGTH)
  String description;

  @AttributeOverrides({
    @AttributeOverride(name = "value", column = @Column(name = "DOCUMENT_ID", length = TypeDefinitions.UUID_BINARY_LENGTH))
  })
  DocumentId documentId;

}
