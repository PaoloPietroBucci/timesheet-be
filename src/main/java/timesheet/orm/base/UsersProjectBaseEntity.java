package timesheet.orm.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import timesheet.libs.BaseAuditedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
@Table(name = "user_project")
@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class UsersProjectBaseEntity extends BaseAuditedEntity {

    protected static final long serialVersionUID = 1L;

    @Column(name = "user_id")
    @NotNull
    protected String userId;
    @Column(name = "project_id")
    @NotNull
    protected String projectId;



}
