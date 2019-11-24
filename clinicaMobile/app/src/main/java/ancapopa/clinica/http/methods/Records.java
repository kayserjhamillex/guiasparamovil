package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.MedicsResponse;
import ancapopa.clinica.model.RecordsResponse;
import retrofit.Call;
import retrofit.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Records {
    @GET("/api/records/{id}")
    Call<RecordsResponse> getRecordsByUserId(@Path("id") int id);

//    @GET("/api/cities/{id}")
//    Response getCityById(@Path("id") int id);
}