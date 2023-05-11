package timesheet.util.exceptions;

public class ClientUnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClientUnauthorizedException() {
        super("Client Unauthorized");
    }

}
