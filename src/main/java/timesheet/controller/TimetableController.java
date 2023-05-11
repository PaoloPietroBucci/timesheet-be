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
import timesheet.excelService.WriteTimetable;
import timesheet.libs.BaseController;
import timesheet.orm.entity.Timetable;
import timesheet.orm.repository.TimetableRepository;
import timesheet.util.annotations.HandleLog;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(
        value = ControllerRoute.TIMETABLE,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TimetableController extends BaseController<Timetable, TimetableRepository> {


    @Autowired
    protected TimetableRepository repository;

    @Override
    protected Timetable createEntity() {
        return new Timetable();
    }

    @Override
    protected Long getCount(Timetable filter) {
        return repository.count(filter);
    }

    @Override
    protected String getEntityName() {
        return Timetable.class.getSimpleName();
    }

    @Override
    protected List<Timetable> getFindAll(Timetable filter) {
        return repository.findAll(filter);
    }

    @Override
    protected TimetableRepository getRepository() {
        return repository;
    }

    @HandleLog
    @PostMapping(value = ControllerRoute.SUM)
    protected ResponseEntity<?> sumAll(@RequestBody(required = false) Timetable filter) {

        try {
            if (filter == null) {
                filter = createEntity();
            }
            filter.setDeleted(false);

            Long sum = this.getRepository().sum(filter);

            return new ResponseEntity<>(sum, HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        }

    }
    @HandleLog
    @PostMapping(value = ControllerRoute.SUM_WEEKLY)
    protected ResponseEntity<?> sumWeekly(@RequestBody(required = false) Timetable filter) {

        try {
            if (filter == null) {
                filter = createEntity();
            }
            filter.setDeleted(false);
            LocalDate today = LocalDate.now();
            LocalDate oneWeekAgo = today.minusWeeks(1);
            filter.setDateFrom(oneWeekAgo);
            filter.setDateTo(today);

            Long sum = this.getRepository().sum(filter);

            return new ResponseEntity<>(sum, HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        }

    }
    @HandleLog
    @PostMapping(value = ControllerRoute.EXCEL)
    protected ResponseEntity<?> createExcel(@RequestBody(required = false) Timetable filter) {

        try {
            if (filter == null) {
                filter = createEntity();
            }
            filter.setDeleted(false);

            List<Timetable> timetables = repository.findAll(filter);

            String wb = WriteTimetable.writeExcel(timetables);

            Path path = Paths.get(wb);
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            HttpHeaders header = new HttpHeaders();

            return  ResponseEntity
                    .ok()
                    .headers(header)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        }

    }
}
