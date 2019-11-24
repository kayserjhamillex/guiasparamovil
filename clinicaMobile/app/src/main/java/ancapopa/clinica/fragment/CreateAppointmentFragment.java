package ancapopa.clinica.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ancapopa.clinica.MainActivity;
import ancapopa.clinica.R;
import ancapopa.clinica.http.Api;
import ancapopa.clinica.http.methods.Appointments;
import ancapopa.clinica.http.methods.Cities;
import ancapopa.clinica.http.methods.Clinics;
import ancapopa.clinica.http.methods.Medics;
import ancapopa.clinica.http.methods.Sections;
import ancapopa.clinica.model.Appointment;
import ancapopa.clinica.model.AppointmentCreate;
import ancapopa.clinica.model.AppointmentsCreateResponse;
import ancapopa.clinica.model.AppointmentsResponse;
import ancapopa.clinica.model.CitiesResponse;
import ancapopa.clinica.model.City;
import ancapopa.clinica.model.Clinic;
import ancapopa.clinica.model.ClinicsResponse;
import ancapopa.clinica.model.Medic;
import ancapopa.clinica.model.MedicsResponse;
import ancapopa.clinica.model.Section;
import ancapopa.clinica.model.SectionsResponse;
import ancapopa.clinica.services.DialogService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by anca.popa on 27/07/2017.
 */

public class CreateAppointmentFragment extends Fragment {

    City selectedCity = null;
    Clinic selectedClinic = null;
    Section selectedSection = null;
    Medic selectedMedic = null;
    Spinner citySpinner = null;
    Spinner clinicsSpinner = null;
    Spinner sectionsSpinner = null;
    Spinner medicSpinner = null;
    EditText datePicker;
//    EditText reason;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment_create, container, false);
        citySpinner = (Spinner) view.findViewById(R.id.cities_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedCity = (City) adapterView.getItemAtPosition(pos);
                populateClinics();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedCity = null;
            }
        });

        clinicsSpinner = (Spinner) view.findViewById(R.id.clinics_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        clinicsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedClinic = (Clinic) adapterView.getItemAtPosition(pos);
                populateSections();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedClinic = null;
            }
        });

        sectionsSpinner = (Spinner) view.findViewById((R.id.sections_spinner));
        sectionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedSection = (Section) adapterView.getItemAtPosition(pos);
                populateMedics();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedSection = null;
            }
        });

        medicSpinner = (Spinner) view.findViewById((R.id.medic_spinner));
        medicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedMedic = (Medic) adapterView.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedMedic = null;
            }
        });

        datePicker = (EditText) view.findViewById(R.id.date_picker);
        String date = datePicker.getText().toString();
//        reason = (EditText) view.findViewById(R.id.reason);
        Button submitAppt = (Button) view.findViewById(R.id.submit_appointment);
        submitAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAppointment();
            }
        });

//        datePicker.setOnClickListener(DialogService.showDateChooserDialog());


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Cities citiesService = (new Api()).getCitiesService();
        Call<CitiesResponse> citiesCall = citiesService.listCities();
        Log.d("CLINICA","Something");
        citiesCall.enqueue(new Callback<CitiesResponse>() {

            @Override
            public void onResponse(Response<CitiesResponse> response, Retrofit retrofit) {

                List<City> results = new ArrayList<>();
                results.add((new City()).setName("--Alege--"));
                results.addAll(response.body().getData());

                ArrayAdapter<City> adapter =
                        new ArrayAdapter<City>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                citySpinner.setAdapter(adapter);
                //response.body().getData().;
                Log.d("CLINICA - cities call",String.valueOf(response.body().getStatus()));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("CLINICA - cities call","Error");
            }
        });






    }


    private void submitAppointment() {
        Integer user_id = getContext().getSharedPreferences("login", 0).getInt("user_id",0);
        String date = datePicker.getText().toString();
//        String appt_reason  = reason.getText().toString();
//        Integer city_id = selectedCity.getId();
        Integer clinic_id = selectedClinic.getId();
        Integer medic_id = selectedMedic.getId();
        Integer section_id = selectedSection.getId();

        Appointments appointmentsService = (new Api()).getAppointmentsService();
        AppointmentCreate appointment = new AppointmentCreate(user_id,medic_id, clinic_id,section_id,date);
        Call<AppointmentsCreateResponse> appointmentsCall = appointmentsService.saveAppointment(appointment);
        appointmentsCall.enqueue(new Callback<AppointmentsCreateResponse>() {
            @Override
            public void onResponse(Response<AppointmentsCreateResponse> response, Retrofit retrofit) {
                if (response.body().getStatus() == 201) {
                    Toast.makeText(getActivity(), "Programarea a fost creata cu succes!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getActivity(), AppointmentsFragment.class);
//                    getActivity().startActivity(intent);
                    ((MainActivity) getActivity()).goToAppointments();
                }
                else {
                    Toast.makeText(getActivity(), "Nu s-a putut crea programarea.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), "Nu s-a putut crea programarea."+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void populateClinics(){
        if(selectedCity != null && selectedCity.getId() != null) {
            Clinics clinicsService = (new Api()).getClinicsService();
            Call<ClinicsResponse> clinicsCall = clinicsService.listClinicsByCityId(selectedCity.getId());
            Log.d("CLINICA","Something");
            clinicsCall.enqueue(new Callback<ClinicsResponse>() {

                @Override
                public void onResponse(Response<ClinicsResponse> response, Retrofit retrofit) {
                    List<Clinic> results = new ArrayList<>();
                    results.add((new Clinic()).setName("--Alege--"));
                    results.addAll(response.body().getData());

                    ArrayAdapter<Clinic> adapter =
                            new ArrayAdapter<Clinic>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    clinicsSpinner.setAdapter(adapter);
                    //response.body().getData().;
                    Log.d("CLINICA - clinics call",String.valueOf(response.body().getStatus()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("CLINICA - clinics call","Error");
                }
            });
        } else {
            List<Clinic> results = new ArrayList<>();
            results.add((new Clinic()).setName("--Alege clinica--"));

            ArrayAdapter<Clinic> adapter =
                    new ArrayAdapter<Clinic>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            clinicsSpinner.setAdapter(adapter);
        }

    }


    public void populateSections(){
        if(selectedClinic != null && selectedClinic.getId() != null) {
            Sections sectionsService = (new Api()).getSectionsService();
            Call<SectionsResponse> sectionsCall = sectionsService.getSectionsByClinicId(selectedClinic.getId());
            Log.d("CLINICA","Something");
            sectionsCall.enqueue(new Callback<SectionsResponse>() {

                @Override
                public void onResponse(Response<SectionsResponse> response, Retrofit retrofit) {
                    List<Section> results = new ArrayList<>();
                    results.add((new Section()).setName("--Alege sectia--"));
                    results.addAll(response.body().getData());

                    ArrayAdapter<Section> adapter =
                            new ArrayAdapter<Section>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    sectionsSpinner.setAdapter(adapter);
                    //response.body().getData().;
                    Log.d("CLINICA - sections call",String.valueOf(response.body().getStatus()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("CLINICA - sections call","Error");
                }
            });
        } else {
            List<Section> results = new ArrayList<>();
            results.add((new Section()).setName("--Alege sectia--"));

            ArrayAdapter<Section> adapter =
                    new ArrayAdapter<Section>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            sectionsSpinner.setAdapter(adapter);
        }

    }


    public void populateMedics() {
        if (selectedSection != null && selectedSection.getId() != null) {
            Medics medicService = (new Api()).getMedicsService();
            Call<MedicsResponse> medicCall = medicService.listMedics(selectedClinic.getId(), selectedSection.getId());
            Log.d("CLINICA", "Something");
            medicCall.enqueue(new Callback<MedicsResponse>() {

                @Override
                public void onResponse(Response<MedicsResponse> response, Retrofit retrofit) {
                    List<Medic> results = new ArrayList<>();
                    results.add((new Medic()).setName("--Alege cadrul Medical--"));
                    results.addAll(response.body().getData());

                    ArrayAdapter<Medic> adapter =
                            new ArrayAdapter<Medic>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    medicSpinner.setAdapter(adapter);
                    //response.body().getData().;
                    Log.d("CLINICA - medic call", String.valueOf(response.body().getStatus()));
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("CLINICA - medic call", "Error");
                }
            });
        } else {
            List<Medic> results = new ArrayList<>();
            results.add((new Medic()).setName("--Alege cadrul medical--"));

            ArrayAdapter<Medic> adapter =
                    new ArrayAdapter<Medic>(getContext(), android.R.layout.simple_spinner_dropdown_item, results);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            medicSpinner.setAdapter(adapter);
        }

    }

}
