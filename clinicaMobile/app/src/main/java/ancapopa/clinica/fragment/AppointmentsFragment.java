package ancapopa.clinica.fragment;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ancapopa.clinica.R;
import ancapopa.clinica.http.Api;
import ancapopa.clinica.http.methods.Appointments;
import ancapopa.clinica.model.Appointment;
import ancapopa.clinica.model.AppointmentsResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by anca.popa on 27/07/2017.
 */

public class AppointmentsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Appointment> appointmentsList = new ArrayList<Appointment>();
    RecyclerView recyclerView = null;
    SwipeRefreshLayout mSwipeRefreshLayout = null;
    AppointmentListAdapter mAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appoinments, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.itemsRecyclerView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new AppointmentListAdapter(appointmentsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        populateData();
    }

    public void populateData() {
        Appointments appointmentService = (new Api()).getAppointmentsService();
        Call<AppointmentsResponse> appointmentsByUser = appointmentService.getAppointmentsByUserId(getContext().getSharedPreferences("login", 0).getInt("user_id",0));
        Log.d("CLINICA","appointments call");
        appointmentsByUser.enqueue(new Callback<AppointmentsResponse>() {

            @Override
            public void onResponse(Response<AppointmentsResponse> response, Retrofit retrofit) {
                appointmentsList.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("CLINICA - appointments", "Error");
            }
        });
    }

    @Override
    public void onRefresh() {
        appointmentsList.clear();
        populateData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.MyViewHolder> {
        private List<Appointment> appointmentList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView appointmentId,appointmentMedicName,appointmentClinicName, appointmentSectionName, appointmentReason, appointmentDate, appointmentStatus;

            public MyViewHolder(View view) {
                super(view);
                appointmentId = (TextView) view.findViewById(R.id.appointment_id);
                appointmentMedicName = (TextView) view.findViewById(R.id.appointment_medic_name);
                appointmentClinicName = (TextView) view.findViewById(R.id.appointment_clinic_name);
                appointmentSectionName = (TextView) view.findViewById(R.id.appointment_section_name);
                appointmentReason = (TextView) view.findViewById(R.id.appointment_reason);
                appointmentDate = (TextView) view.findViewById(R.id.appointment_date);
                appointmentStatus = (TextView) view.findViewById(R.id.appointment_status);
            }
        }


        public AppointmentListAdapter(List<Appointment> appointmentList) {
            this.appointmentList = appointmentList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Appointment appointment = appointmentList.get(position);
            holder.appointmentId.setText(String.valueOf(appointment.getId()));
            holder.appointmentMedicName.setText(appointment.getMedic_name());
            holder.appointmentClinicName.setText(appointment.getClinic_name());
            holder.appointmentSectionName.setText(appointment.getSection_name());
            holder.appointmentReason.setText(appointment.getReason());
            Log.d("test", appointment.getDate()+"");
            holder.appointmentDate.setText(appointment.getDate());
            String status = "In procesare";
            if (appointment.getStatus() == 2) {
                status = "Aprobata";
            }
            holder.appointmentStatus.setText(status);
        }

        @Override
        public int getItemCount() {
            return appointmentList.size();
        }
    }
}
