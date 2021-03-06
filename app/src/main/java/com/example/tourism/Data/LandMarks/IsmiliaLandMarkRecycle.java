package com.example.tourism.Data.LandMarks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourism.Data.ClickItem.DetailsActivity;
import com.example.tourism.Data.DataModel;
import com.example.tourism.Data.FirebaseViewDataHolder;
import com.example.tourism.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class IsmiliaLandMarkRecycle extends AppCompatActivity {

    /**
     * Created by : Ahmed Ramadan
     * date : 9 / 2019
     * ahmedtramadan4@gmail.com
     */

    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private DatabaseReference mdatabaseReference;
    private FirebaseRecyclerAdapter<DataModel, FirebaseViewDataHolder> firebaseRecyclerAdapter;
    private FirebaseRecyclerOptions<DataModel> options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ismilia_land_mark_recycle);


        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = findViewById(R.id.recycleLandMarks_ismalia);
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        mdatabaseReference = mFirebaseDatabase.getReference("data").child("ismailia").child("landMarks");

        showData();

    }

    private void showData() {
        options = new FirebaseRecyclerOptions.Builder<DataModel>().setQuery(mdatabaseReference, DataModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataModel, FirebaseViewDataHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewDataHolder Holder, int i, @NonNull DataModel dataModel) {

                Holder.setDetails(getApplicationContext(), dataModel.getName(), dataModel.getImg(), dataModel.getDes(), dataModel.getLocation());

            }

            @NonNull
            @Override
            public FirebaseViewDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_of_data, parent, false);

                FirebaseViewDataHolder viewDataHolder = new FirebaseViewDataHolder(itemView);
                viewDataHolder.setOnClickListener(new FirebaseViewDataHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int postion) {

                        //views
                        TextView mName = view.findViewById(R.id.nameforitem);
                        TextView mDescription = view.findViewById(R.id.descriptionforitem);
                        ImageView mImage = view.findViewById(R.id.imageforitem);
                        TextView location = view.findViewById(R.id.location);

                        //get Data from Views
                        String mTitle = mName.getText().toString();
                        String mDes = mDescription.getText().toString();
                        String mlocation = location.getText().toString();

                        //Pass This Data To new Activity
                        Intent intent = new Intent(view.getContext(), DetailsActivity.class);

                        //Put Image as Array of byte
                        intent.putExtra("name", mTitle);
                        intent.putExtra("des", mDes);
                        intent.putExtra("location", mlocation);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int postion) {

                        //TODO do your own implementation on long item click
                        Toast.makeText(IsmiliaLandMarkRecycle.this, "Long Click", Toast.LENGTH_SHORT).show();

                    }
                });

                return viewDataHolder;
            }
        };

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }
    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
    }
}
