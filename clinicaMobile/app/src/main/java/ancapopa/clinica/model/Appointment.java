package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class Appointment {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("medic_name")
    @Expose
    private String medic_name;

    @SerializedName("clinic_name")
    @Expose
    private String clinic_name;

    @SerializedName("section_name")
    @Expose
    private String section_name;

    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("date")
    @Expose
    private String date;


    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("status")
    @Expose
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMedic_name() {
        return medic_name;
    }

    public void setMedic_name(String medic_name) {
        this.medic_name = medic_name;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getSection_name() { return section_name; }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getStatus() { return status; }

    public void setStatus (Integer status) {
        this.status = status;
    }


}