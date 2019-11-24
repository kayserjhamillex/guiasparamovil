package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class Record {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("users_name")
    @Expose
    private String users_name;

    @SerializedName("medic_name")
    @Expose
    private String medic_name;

    @SerializedName("clinic_name")
    @Expose
    private String clinic_name;

    @SerializedName("section_name")
    @Expose
    private String section_name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("created_at")
    @Expose
    private String createdAt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
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

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String clinic_name) {
        this.section_name = section_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


}