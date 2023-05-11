package timesheet.controller;

import org.aspectj.apache.bcel.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import timesheet.ControllerRoute;
import timesheet.libs.BaseController;
import timesheet.orm.entity.Project;
import timesheet.orm.repository.ProjectRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = ControllerRoute.PROJECT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProjectContoller extends BaseController<Project, ProjectRepository> {

    @Autowired
    protected ProjectRepository repository;
    @Override
    protected Project createEntity() {
        return new Project();
    }

    @Override
    protected Long getCount(Project filter) {
        return repository.count(filter);
    }

    @Override
    protected String getEntityName() {
        return Project.class.getSimpleName();
    }

    @Override
    protected List<Project> getFindAll(Project filter) {
        return repository.findAll(filter);
    }

    @Override
    protected ProjectRepository getRepository() {
        return repository;
    }

}
