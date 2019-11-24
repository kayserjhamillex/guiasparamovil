package ancapopa.clinica.events;

/**
 * Created by anca.popa on 26/07/2017.
 */

public class UserAuthEvent {
    private Boolean login;
    public UserAuthEvent(boolean login) {
        this.login = login;
    }

    public boolean getLogin() {
        return login;
    }
}
