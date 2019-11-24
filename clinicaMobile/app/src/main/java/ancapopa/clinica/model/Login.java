package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class Login {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

}