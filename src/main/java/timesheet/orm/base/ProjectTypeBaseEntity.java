package timesheet.orm.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import timesheet.libs.BaseAuditedEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
@Table(name = "project_type")
@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class ProjectTypeBaseEntity extends BaseAuditedEntity {

    protected static final long serialVersionUID = 1L;
        @Column(name = "type")
        @NotNull
        protected String type;
        @Column(name = "project_id")
        @NotNull
        protected String projectId;
    }
