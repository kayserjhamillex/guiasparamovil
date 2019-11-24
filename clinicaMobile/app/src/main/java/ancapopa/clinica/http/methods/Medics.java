package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.MedicsResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Medics {
    @GET("/api/clinics/{clinic_id}/sections/{section_id}/medics")
    Call<MedicsResponse> listMedics(@Path("clinic_id") int clinic_id, @Path("section_id") int section_id);
//2nd param?


//    @GET("/api/cities/{id}")
//    Response getCityById(@Path("id") int id);
}