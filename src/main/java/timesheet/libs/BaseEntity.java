package timesheet.libs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import timesheet.libs.models.Pagination;
import timesheet.libs.models.Translation;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
@FilterDef(
        name = "languageFilter", parameters = {
        @ParamDef(name = "language", type = "java.lang.String")
},
        defaultCondition = "language = :language"
)
@FilterDef(
        name = "timetableFilter", parameters = {
        @ParamDef(name = "option", type = "java.lang.String"),
        @ParamDef(name = "weekday", type = "java.lang.String"),
        @ParamDef(name = "when", type = "java.sql.Time")
},
        defaultCondition = "option = :option AND weekday = :weekday AND :when BETWEEN time_from_date AND time_to_date"
)

public abstract class BaseEntity implements Serializable {
    public static final String DEFAULT_DATE_DELETED_STR = "1971-01-01 00:00:00";
    // public static final String ENTITY_GRAPH_LOAD = "javax.persistence.loadgraph";
    @Transient
    final protected static Logger logger = LoggerFactory.getLogger(BaseEntity.class);
    public static final Date DEFAULT_DATE_DELETED = getDefaultDateDeleted();

    protected static final long serialVersionUID = 1L;
    @Transient
    protected Translation translation;
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "date_delete")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    protected Date dateDelete = DEFAULT_DATE_DELETED;
    @Column(name = "deleted")
    @JsonIgnore
    protected boolean deleted;
    @Transient
    protected Pagination pagination;

    /**
     * @return Date
     */
    protected static Date getDefaultDateDeleted() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(DEFAULT_DATE_DELETED_STR);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public abstract <T> T clean();

    public <T> T clean(String language) {
        return clean();
    }

    public void extract() {
    }

    public abstract <T> T lite();

    public void translate() {
    }

    public Translation getTranslation() {
        translate();
        return translation;
    }

    public void setDateDelete(Date dateDelete) {
        if (dateDelete == null) {
            dateDelete = DEFAULT_DATE_DELETED;
        }
        this.dateDelete = dateDelete;
    }

    public void preDelete() {
        this.deleted = true;
        this.dateDelete = new Date();
    }
}
