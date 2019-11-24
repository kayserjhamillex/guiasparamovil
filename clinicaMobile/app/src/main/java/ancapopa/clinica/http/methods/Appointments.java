package ancapopa.clinica.http.methods;

import ancapopa.clinica.model.Appointment;
import ancapopa.clinica.model.AppointmentCreate;
import ancapopa.clinica.model.AppointmentsCreateResponse;
import ancapopa.clinica.model.AppointmentsResponse;
import ancapopa.clinica.model.RecordsResponse;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by anca.popa on 7/20/2017.
 */
public interface Appointments {
    @GET("/api/user-appointments/{id}")
    Call<AppointmentsResponse> getAppointmentsByUserId(@Path("id") int id);


    @POST("/api/appointments")
    Call<AppointmentsCreateResponse> saveAppointment(@Body AppointmentCreate appointment);
}