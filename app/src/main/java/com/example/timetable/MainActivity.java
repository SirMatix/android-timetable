package com.example.timetable;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    Button timetable, logout;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    CollectionReference userRef = fStore.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(fAuth.getCurrentUser() == null){
            startActivity(new Intent(getApplicationContext(), Register.class));
            finish();
        }

        timetable = findViewById(R.id.timetableButton);
        logout = findViewById(R.id.logout);

        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = fAuth.getCurrentUser().getUid();
                userRef.document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        String group = user.getGroup();
                        String mode = user.getMode();
                        Intent intent = new Intent(getApplicationContext(), Timetable.class);
                        intent.putExtra("group", group);
                        intent.putExtra("mode", mode);
                        startActivity(intent);
                    }
                });


            }
        });

    }


    public void logout (View view){
        FirebaseAuth.getInstance().signOut(); //logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}

