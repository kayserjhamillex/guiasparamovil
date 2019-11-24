package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.CitiesResponse;
import ancapopa.clinica.model.ClinicsResponse;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Clinics {
    @GET("/api/cities/{id}/clinics")
    Call<ClinicsResponse> listClinicsByCityId(@Path("id") int id);

}