package timesheet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import timesheet.libs.models.MappedError;
import timesheet.util.constants.ErrorConstants;

public class ManageException {
    final protected static Logger logger = LoggerFactory.getLogger(ManageException.class);

    /**
     *
     * @param e Exception
     * @return ResponseEntity<?>
     */
    public static ResponseEntity<?> manageControllerException(Exception e) {
        logger.error(e.getLocalizedMessage());
        return new ResponseEntity<>(new MappedError(ErrorConstants.ERROR_GENERAL), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
