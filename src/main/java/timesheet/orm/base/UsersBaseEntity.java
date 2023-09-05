package timesheet.orm.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import timesheet.UsersRoleEnum;
import timesheet.libs.BaseAuditedEntity;
import timesheet.orm.entity.Project;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@MappedSuperclass
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class UsersBaseEntity extends BaseAuditedEntity {
    protected static final long serialVersionUID = 1L;

    @Column(name = "mail")
    @NotNull
    protected String mail;

    @Column(name = "username")
    @NotNull
    protected String username;

    @Column(name = "password")
    @NotNull
    protected String password;

    @Column(name = "name")
    @NotNull
    protected String name;

    @Column(name = "surname")
    @NotNull
    protected String surname;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    protected UsersRoleEnum role;

    @Column(name = "active")
    @NotNull
    protected Boolean active;

    @Column(name = "info")
    protected String info;

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id")}
    )
    protected List<Project> projectList;

}
