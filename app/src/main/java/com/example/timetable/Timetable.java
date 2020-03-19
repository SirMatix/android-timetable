package com.example.timetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Timetable extends AppCompatActivity {

    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference dayRef = fStore.collection("Timetables");

    private DayAdapter adapter;
    String groupFeed, modeFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        // getting intent from Forum activity and getting extra string
        Intent intent = getIntent();
        groupFeed = intent.getStringExtra("group");
        modeFeed = intent.getStringExtra("mode");


        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        Query query = dayRef.whereEqualTo("group", groupFeed).whereEqualTo("mode", modeFeed).orderBy("order", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Day> options = new FirestoreRecyclerOptions.Builder<Day>()
                .setQuery(query, Day.class)
                .build();

        adapter = new DayAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}


/*


LinearLayoutManager layoutManager
    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

RecyclerView myList = (RecyclerView) findViewById(R.id.my_recycler_view);
myList.setLayoutManager(layoutManager);






 */