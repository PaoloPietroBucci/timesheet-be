package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timesheet.ControllerRoute;
import timesheet.ManageException;
import timesheet.libs.BaseController;
import timesheet.orm.entity.UsersProject;
import timesheet.orm.repository.UsersProjectRepository;
import timesheet.util.annotations.HandleAuthentication;
import timesheet.util.annotations.HandleLog;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(
        value = ControllerRoute.USERS_PROJECT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UsersProjectContoller extends BaseController<UsersProject, UsersProjectRepository> {

    @Autowired
    protected UsersProjectRepository repository;

    @Override
    protected UsersProject createEntity() {
        return new UsersProject();
    }

    @Override
    protected Long getCount(UsersProject filter) {
        return repository.count(filter);
    }

    @Override
    protected String getEntityName() {
        return UsersProject.class.getSimpleName();
    }

    @Override
    protected List<UsersProject> getFindAll(UsersProject filter) {
        return repository.findAll(filter);
    }

    @Override
    protected UsersProjectRepository getRepository() {
        return repository;
    }


    @Override
    @HandleLog
    @HandleAuthentication
    @Transactional
    @PostMapping
    protected ResponseEntity<?> insert(@RequestBody UsersProject usersProject) {
        try {
            return new ResponseEntity<>(getRepository().save(usersProject), HttpStatus.CREATED);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }

}
