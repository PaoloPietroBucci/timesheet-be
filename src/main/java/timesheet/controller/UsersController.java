package timesheet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timesheet.ControllerRoute;
import timesheet.libs.BaseController;
import timesheet.orm.entity.Users;
import timesheet.orm.repository.UsersRepository;

import java.util.List;

@RestController
@RequestMapping(
        value = ControllerRoute.USERS,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UsersController extends BaseController<Users, UsersRepository> {

    @Autowired
    protected UsersRepository repository;

    @Override
    protected Users createEntity() {
        return new Users();
    }

    @Override
    protected Long getCount(Users filter) {
        return repository.count(filter);
    }

    @Override
    protected String getEntityName() {
        return Users.class.getSimpleName();
    }

    @Override
    protected List<Users> getFindAll(Users filter) {
        return repository.findAll(filter);
    }

    @Override
    protected UsersRepository getRepository() {
        return repository;
    }

}
