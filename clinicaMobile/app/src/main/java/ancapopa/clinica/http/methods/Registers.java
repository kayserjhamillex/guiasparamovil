package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.Login;
import ancapopa.clinica.model.LoginResponse;
import ancapopa.clinica.model.Register;
import ancapopa.clinica.model.RegisterResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Registers {
    @POST("/api/register")
    Call<RegisterResponse> register(@Body Register register);

}