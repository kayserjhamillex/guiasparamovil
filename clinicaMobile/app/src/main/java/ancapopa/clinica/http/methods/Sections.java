package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.ClinicsResponse;
import ancapopa.clinica.model.SectionsResponse;
import retrofit.Call;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Sections {
    @GET("/api/sections")
    Call<SectionsResponse> listSections();

    @GET("/api/clinics/{id}/sections")
    Call<SectionsResponse> getSectionsByClinicId(@Path("id") int id);

}