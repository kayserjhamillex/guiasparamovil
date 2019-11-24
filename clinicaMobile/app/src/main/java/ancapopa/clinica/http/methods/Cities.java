package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.CitiesResponse;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Cities {
    @GET("/api/cities")
    Call<CitiesResponse> listCities();

//    @GET("/api/cities/{id}")
//    Response getCityById(@Path("id") int id);
}