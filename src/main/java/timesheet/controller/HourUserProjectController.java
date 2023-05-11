package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import timesheet.ControllerRoute;
import timesheet.ManageException;
import timesheet.PaginationResponse;
import timesheet.excelService.WriteHourUserProject;
import timesheet.orm.repository.HourUserProjectRepository;
import timesheet.orm.view.HourUserProject;
import timesheet.util.GenericUtil;
import timesheet.util.annotations.HandleLog;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(
        value = ControllerRoute.HOUR_USER_PROJECT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class HourUserProjectController {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected HourUserProjectRepository repository;

    protected HourUserProject createEntity() {
        return new HourUserProject();
    }

    protected Long getCount(HourUserProject filter) {
        return repository.count(filter);
    }

    protected String getEntityName() {
        return HourUserProject.class.getSimpleName();
    }

    protected List<HourUserProject> getFindAll(HourUserProject filter) {
        return repository.findAll(filter);
    }

    protected HourUserProjectRepository getRepository() {
        return repository;
    }

    @HandleLog
    @PostMapping(value = ControllerRoute.ALL)
    protected ResponseEntity<?> findAll(
            @RequestBody(required = false) HourUserProject filter) {
        try {
            if (filter == null) {
                filter = createEntity();
            }

            Long count = getCount(filter);
            List<HourUserProject> list = count == null || count > 0 ? getFindAll(filter) : new ArrayList<>();



            return new ResponseEntity<>(new PaginationResponse(count, list, GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null, null), HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }

    @HandleLog
    @PostMapping(value = ControllerRoute.EXCEL)
    protected ResponseEntity<?> createExcel(@RequestBody(required = false) HourUserProject filter) {

        try {
            if (filter == null) {
                filter = createEntity();
            }

            List<HourUserProject> list = repository.findAll(filter);

            String wb = WriteHourUserProject.writeExcel(list);

            Path path = Paths.get(wb);
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            HttpHeaders header = new HttpHeaders();

            return ResponseEntity
                    .ok()
                    .headers(header)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        }
    }
}
