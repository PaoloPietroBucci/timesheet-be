package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timesheet.ControllerRoute;
import timesheet.libs.BaseController;
import timesheet.orm.entity.ProjectType;
import timesheet.orm.repository.ProjectTypeRepository;

import java.util.List;

@RestController
@RequestMapping(
        value = ControllerRoute.PROJECT_TYPE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProjectTypeContoller extends BaseController<ProjectType,ProjectTypeRepository> {

    @Autowired
    protected ProjectTypeRepository repository;

    @Override
    protected ProjectType createEntity() {
        return new ProjectType();
    }

    @Override
    protected Long getCount(ProjectType filter) {
        return repository.count(filter);
    }

    @Override
    protected String getEntityName() {
        return ProjectType.class.getSimpleName();
    }

    @Override
    protected List<ProjectType> getFindAll(ProjectType filter) {
        return repository.findAll(filter);
    }

    @Override
    protected ProjectTypeRepository getRepository() {
        return repository;
    }
}
