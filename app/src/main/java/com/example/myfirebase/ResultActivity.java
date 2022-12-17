package com.example.myfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText1;
    private TextView textView;
    private Button button, newButton;
    DatabaseReference databaseReference;

    private ImageView imageViewAdd, imageView;

    private Uri imageUri;
    private static final int image_request = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        databaseReference = FirebaseDatabase.getInstance().getReference("Data");

        editText1 = findViewById(R.id.text1Id);
        textView = findViewById(R.id.text2Id);
        button = findViewById(R.id.saveId);
        newButton = findViewById(R.id.newId);
        button.setOnClickListener(this);
        newButton.setOnClickListener(this);

        imageViewAdd = findViewById(R.id.addImageId);
        imageViewAdd.setOnClickListener(this);
        imageView = findViewById(R.id.imageId);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.outId) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.saveId) {

            String detailsText = editText1.getText().toString().trim();

            // String key=databaseReference.push().getKey();
            TextGetSet textGetSet = new TextGetSet(detailsText);
            databaseReference.setValue(textGetSet);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.newId) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String str = snapshot.child("text").getValue(String.class);
                    textView.setText(str);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ResultActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();

                }
            });
        }
        if (v.getId() == R.id.addImageId) {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, image_request);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == image_request && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUri=data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }

    }
}