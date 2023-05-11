package timesheet.util.exceptions;

public class TokenExpiredException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenExpiredException() {
        super("Token Expired");
    }

}