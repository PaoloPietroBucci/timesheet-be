package timesheet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import timesheet.ControllerRoute;
import timesheet.ManageException;
import timesheet.PaginationResponse;
import timesheet.service.excelService.WriteTotalHourPerProject;
import timesheet.orm.repository.TotalHourPerProjectRepository;
import timesheet.orm.view.TotalHourPerProject;
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
        value = ControllerRoute.TOTAL_HOUR_PER_PROJECT,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TotalHourPerProjectController {

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected TotalHourPerProjectRepository repository;

    protected TotalHourPerProject createEntity() {
        return new TotalHourPerProject();
    }

    protected Long getCount(TotalHourPerProject filter) {
        return repository.count(filter);
    }

    protected String getEntityName() {
        return TotalHourPerProject.class.getSimpleName();
    }

    protected List<TotalHourPerProject> getFindAll(TotalHourPerProject filter) {
        return repository.findAll(filter);
    }

    protected TotalHourPerProjectRepository getRepository() {
        return repository;
    }

    @HandleLog
    @PostMapping(value = ControllerRoute.ALL)
    protected ResponseEntity<?> findAll(
                                        @RequestBody(required = false) TotalHourPerProject filter) {
        try {
            if (filter == null) {
                filter = createEntity();
            }

            Long count = getCount(filter);
            List<TotalHourPerProject> list = count == null || count > 0 ? getFindAll(filter) : new ArrayList<>();
            List<TotalHourPerProject> out = new ArrayList<>();
            list.forEach(element ->{
                out.add(element.clean());
            });


            return new ResponseEntity<>(new PaginationResponse(count, out, GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null, null), HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }
    @HandleLog
    @PostMapping(value = ControllerRoute.EXCEL)
    protected ResponseEntity<?> createExcel(@RequestBody(required = false) TotalHourPerProject filter) {

        try {
            if (filter == null) {
                filter = createEntity();
            }

            List<TotalHourPerProject> list = repository.findAll(filter);

            String wb = WriteTotalHourPerProject.writeExcel(list);

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
