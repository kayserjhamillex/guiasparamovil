package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class RegisterResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("token")
    @Expose
    private String token = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
