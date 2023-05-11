package timesheet.libs;

import timesheet.util.AuthUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class BaseAuditedEntity extends BaseEntity {

    protected static final long serialVersionUID = 1L;
    @Column(name = "id_user_insert", updatable = false)
    protected Long userInsert;
    @Column(name = "date_insert", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateInsert;
    @Column(name = "id_user_update")
    protected Long userUpdate;
    @Column(name = "date_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateUpdate;

    @PrePersist
    protected void prePersistEntity() {
        this.userInsert = AuthUtil.getLoggedUser() != null ? AuthUtil.getLoggedUserId() : null;
        this.userUpdate = AuthUtil.getLoggedUser() != null ? AuthUtil.getLoggedUserId() : null;
        this.dateInsert = new Date();
        this.dateUpdate = new Date();
    }

    @PreUpdate
    protected void preUpdateEntity() {
        this.userUpdate = AuthUtil.getLoggedUser() != null ? AuthUtil.getLoggedUserId() : null;
        this.dateUpdate = new Date();
    }



}
