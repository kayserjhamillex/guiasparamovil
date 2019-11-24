package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class AppointmentCreate {

    @SerializedName("user_id")
    @Expose
    private Integer user_id;

    @SerializedName("medic_id")
    @Expose
    private Integer medic_id;

    @SerializedName("clinic_id")
    @Expose
    private Integer clinic_id;

    @SerializedName("section_id")
    @Expose
    private Integer section_id;

//    @SerializedName("city_id")
//    @Expose
//    private Integer city_id;
//
//    @SerializedName("reason")
//    @Expose
//    private String reason;

    @SerializedName("date")
    @Expose
    private String date;

    public AppointmentCreate(Integer user_id, Integer medic_id, Integer clinic_id, Integer section_id, String date) {
        this.user_id = user_id;
//        this.city_id = city_id;
        this.clinic_id = clinic_id;
        this.section_id = section_id;
        this.medic_id = medic_id;
//        this.reason = reason;
        this.date = date;
    }



//    public Integer getUser_id() {
//        return user_id;
//    }
//
//    public void setUser_id(Integer user_id) {
//        this.user_id = user_id;
//    }
//
//    public Integer getMedic_id() {
//        return medic_id;
//    }
//
//    public void setMedic_id(Integer medic_name) {
//        this.medic_id = medic_id;
//    }
//
//    public Integer getClinic_id() {
//        return clinic_id;
//    }
//
//    public void setClinic_id(Integer clinic_id) {
//        this.clinic_id = clinic_id;
//    }
//
//    public Integer getSection_id() { return section_id; }
//
//    public void setSection_name(Integer section_id) {
//        this.section_id = section_id;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//
//    public String getDate() {return date;}
//
//    public void setDate() {this.date = date;}
//


}