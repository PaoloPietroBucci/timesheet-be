package timesheet.libs;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import timesheet.*;
import timesheet.libs.models.MappedError;
import timesheet.util.GenericUtil;
import timesheet.util.annotations.HandleAuthentication;
import timesheet.util.annotations.HandleLog;
import timesheet.util.constants.ErrorConstants;
import timesheet.util.constants.HeaderConstants;

public abstract class BaseController <E extends BaseEntity, R extends PagingAndSortingRepository<E, Long>> {

    final protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    @PersistenceContext
    protected EntityManager entityManager;


    protected abstract E createEntity();

    protected abstract Long getCount(E filter);

    protected abstract String getEntityName();

    protected abstract List<E> getFindAll(E filter);

    protected abstract R getRepository();

    @HandleLog
    @PostMapping(value = ControllerRoute.ALL)
    protected ResponseEntity<?> findAll(@RequestHeader(value = HeaderConstants.LANGUAGE, required = false) String language,
                                        @RequestBody(required = false) E filter) {
        Session session = entityManager.unwrap(Session.class);


        try {
            if (filter == null) {
                filter = createEntity();
            }
            filter.setDeleted(false);

            Long count = getCount(filter);
            List<E> list = count == null || count > 0 ? getFindAll(filter) : new ArrayList<>();
            List<E> out = new ArrayList<>();
            list.forEach(element -> {
                element.extract();
                element.translate();
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
    @PostMapping(value = ControllerRoute.ALL_LITE)
    protected ResponseEntity<?> findAllLite(@RequestHeader(value = HeaderConstants.LANGUAGE, required = false) String language,
                                            @RequestBody(required = false) E filter) {

        try {
            if (filter == null) {
                filter = createEntity();
            }
            filter.setDeleted(false);

            Long count = getCount(filter);
            List<E> list = count == null || count > 0 ? getFindAll(filter) : new ArrayList<>();
            List<E> out = new ArrayList<>();
            list.forEach(element -> {
                element.extract();
                element.translate();
                out.add(element.lite());
            });

            return new ResponseEntity<>(new PaginationResponse(count, out, GenericUtil.isNotNullOrEmpty(filter) ? filter.getPagination() : null, null), HttpStatus.OK);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }

    @HandleLog
    @GetMapping(value = ControllerRoute.BY_ID)
    protected ResponseEntity<?> findById(@PathVariable(ControllerRoute.PARAM_ID) Long id) {

        try {
            Optional<E> optional = getRepository().findById(id);
            if (optional.isPresent()) {
                E element = optional.get();
                E out=element.clean();
                element.translate();
                element.extract();
                return new ResponseEntity<>(out, HttpStatus.OK);
            } else {
                logger.error("{} {} not found", getEntityName(), +id);
                return new ResponseEntity<>(new MappedError(ErrorConstants.ERROR_ENTITY_NOT_FOUND), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }
    @HandleLog
    @HandleAuthentication
    @PostMapping
    protected ResponseEntity<?> insert(@RequestBody E input) {
        try {
            return new ResponseEntity<>(getRepository().save(input), HttpStatus.CREATED);
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }
    @HandleLog
    @DeleteMapping(value = ControllerRoute.BY_ID)
    protected ResponseEntity<?> delete(@PathVariable(ControllerRoute.PARAM_ID) Long id) {
        try {
            Optional<E> optional = getRepository().findById(id);
            if (optional.isPresent()) {
                E element = optional.get();
                element.setDeleted(Boolean.TRUE);
                element.setDateDelete(new Date());
                getRepository().save(element);
                return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
            } else {
                logger.error("{} {} not found", getEntityName(), +id);
                return new ResponseEntity<>(new MappedError(ErrorConstants.ERROR_ENTITY_NOT_FOUND), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return ManageException.manageControllerException(e);
        } finally {
            entityManager.close();
        }
    }
}
