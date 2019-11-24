package ancapopa.clinica.fragment;

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

import java.util.ArrayList;
import java.util.List;

import ancapopa.clinica.R;
import ancapopa.clinica.http.Api;

import ancapopa.clinica.http.methods.Records;
import ancapopa.clinica.model.Record;
import ancapopa.clinica.model.RecordsResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by anca.popa on 27/07/2017.
 */

public class RecordsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private List<Record> recordsList = new ArrayList<Record>();
    RecyclerView recyclerView = null;
    SwipeRefreshLayout mSwipeRefreshLayout = null;
    RecordsListAdapter mAdapter = null;

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
        mAdapter = new RecordsListAdapter(recordsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        populateData();
    }

    public void populateData() {
        Records recordsService = (new Api()).getRecordsService();
        Call<RecordsResponse> responseCall = recordsService.getRecordsByUserId(getContext().getSharedPreferences("login", 0).getInt("user_id",0));
        Log.d("CLINICA","records call");
        responseCall.enqueue(new Callback<RecordsResponse>() {

            @Override
            public void onResponse(Response<RecordsResponse> response, Retrofit retrofit) {
                recordsList.addAll(response.body().getData());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("CLINICA - records", "Error");
            }
        });
    }

    @Override
    public void onRefresh() {
        recordsList.clear();
        populateData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public class RecordsListAdapter extends RecyclerView.Adapter<RecordsListAdapter.MyViewHolder> {
        private List<Record> recordList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView recordId,recordsUserName, recordDescription,recordsMedicName, recordsClinicName, recordsSectionName, recordsCreatedAt;

            public MyViewHolder(View view) {
                super(view);
                recordId = (TextView) view.findViewById(R.id.record_id);
                recordsUserName = (TextView) view.findViewById(R.id.record_user_name);
                recordsMedicName = (TextView) view.findViewById(R.id.record_medic_name);
                recordsClinicName = (TextView) view.findViewById(R.id.record_clinic_name);
                recordsSectionName = (TextView) view.findViewById(R.id.record_section_name);
                recordDescription = (TextView) view.findViewById(R.id.record_description);
                recordsCreatedAt = (TextView) view.findViewById(R.id.record_created_at);
            }
        }


        public RecordsListAdapter(List<Record> recordList) {
            this.recordList = recordList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Record record = recordList.get(position);
            holder.recordId.setText(String.valueOf(record.getId()));
            holder.recordsUserName.setText(record.getUsers_name());
            holder.recordsMedicName.setText(record.getMedic_name());
            holder.recordsClinicName.setText(record.getClinic_name());
            holder.recordsSectionName.setText(record.getSection_name());
            holder.recordDescription.setText(record.getDescription());
            holder.recordsCreatedAt.setText(record.getCreatedAt());
        }

        @Override
        public int getItemCount() {
            return recordList.size();
        }
    }
}
