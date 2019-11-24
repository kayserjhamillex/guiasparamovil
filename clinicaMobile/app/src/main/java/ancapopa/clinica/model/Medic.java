package ancapopa.clinica.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anca.popa on 7/20/2017.
 */
public class Medic {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Medic setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

}