package ancapopa.clinica.http;

import ancapopa.clinica.http.methods.Appointments;
import ancapopa.clinica.http.methods.Cities;
import ancapopa.clinica.http.methods.Clinics;
import ancapopa.clinica.http.methods.Logins;
import ancapopa.clinica.http.methods.Medics;
import ancapopa.clinica.http.methods.Records;
import ancapopa.clinica.http.methods.Registers;
import ancapopa.clinica.http.methods.Sections;
import ancapopa.clinica.model.Appointment;
import ancapopa.clinica.model.Login;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by anca.popa on 7/20/2017.
 */
public class Api {

    Retrofit retrofit;

    public Api() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Cities getCitiesService() {
        return retrofit.create(Cities.class);
    }

    public Clinics getClinicsService() {
        return retrofit.create(Clinics.class);
    }


    public Sections getSectionsService() {
        return retrofit.create(Sections.class);
    }


    public Records getRecordsService() {
        return retrofit.create(Records.class);
    }
    public Appointments getAppointmentsService() { return retrofit.create(Appointments.class); }

    public Medics getMedicsService() {
        return retrofit.create(Medics.class);
    }
    public Logins getLoginService() {
        return retrofit.create(Logins.class);
    }
    public Registers getRegisterService() { return retrofit.create(Registers.class);}

}
