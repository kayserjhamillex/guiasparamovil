package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class LoginResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user")
    @Expose
    private List<User> user = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<User> getData() {
        return user;
    }

    public void setData(List<User> data) {
        this.user = data;
    }

}
