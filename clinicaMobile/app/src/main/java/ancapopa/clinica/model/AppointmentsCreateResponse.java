package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class AppointmentsCreateResponse {


    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Appointment> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Appointment> getData() {
        return data;
    }

    public void setData(List<Appointment> data) {
        this.data = data;
    }

}
