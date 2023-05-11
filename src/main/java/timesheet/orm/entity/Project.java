package timesheet.orm.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import timesheet.orm.base.ProjectBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity(name = "Project")
@Table(name = "project")
@DynamicInsert
@Where(clause = "deleted = false")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Project  extends ProjectBaseEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public Project clean() {
        Project out= new Project();
        out.setId(this.id);
        out.setName(this.name);
        out.setType(this.type);
        out.setUsersList(new ArrayList<>());

        if (this.getUsersList()!=null){
            if(!this.getUsersList().isEmpty()){
                for (Users u : this.getUsersList()){
                    out.getUsersList().add(u.lite());
                }
            }
        }
        return out;
    }

    @Override
    public Project lite() {
    Project out= new Project();
        out.setId(this.id);
        out.setName(this.name);
        out.setType(this.type);

        return out;
    }
}
