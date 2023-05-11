package timesheet.orm.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import timesheet.orm.base.UsersBaseEntity;
import timesheet.util.GenericUtil;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity(name = "Users")
@Table(name = "users")
@DynamicInsert
@Where(clause = "deleted = false")


public class Users extends UsersBaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Override
    public Users clean() {

        Users out = new Users();
        out.setId(this.id);
        out.setUsername(this.username);
        out.setName(this.name);
        out.setSurname(this.surname);
        out.setMail(this.mail);
        out.setRole(this.role);
        out.setProjectList(new ArrayList<>());

        if (this.getProjectList()!=null){
            if(!this.getProjectList().isEmpty()){
                for (Project p : this.getProjectList()) {
                    out.getProjectList().add(p.lite());
                }
            }
        }
        return out;
    }

    @Override
    public Users lite() {
        Users out = new Users();
        out.setId(this.id);
        out.setUsername(this.username);
        out.setName(this.name);
        out.setSurname(this.surname);
        out.setMail(this.mail);
        out.setRole(this.role);
        return out;
    }




    @Override
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<SimpleGrantedAuthority>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return GenericUtil.isNotNullOrEmpty(this.active) && this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return GenericUtil.isNotNullOrEmpty(this.active) && this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return GenericUtil.isNotNullOrEmpty(this.active) && this.active;
    }

    @Override
    public boolean isEnabled() {
        return GenericUtil.isNotNullOrEmpty(this.active) && this.active;
    }
}
