package com.example.quizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private RecyclerView recyclerView;
    private List<CategoryModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<CategoryModel>();


        final CategoryAdapter categoryAdapter=new CategoryAdapter(list);
        recyclerView.setAdapter(categoryAdapter);

        myRef.child("categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                   list.add(dataSnapshot.getValue(CategoryModel.class));
               }
               categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoriesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*
    @Override
    public void onBackPressed() {

        Intent categoryintent = new Intent(CategoriesActivity.this,MainActivity.class);
        //this.finish();
        startActivity(categoryintent);

        // list dummies
        list.add(new CategoryModel("file:///home/dibas/Pictures/garden.png","Example_Category"));
        list.add(new CategoryModel("","Science"));
        list.add(new CategoryModel("","Maths"));
        list.add(new CategoryModel("","Philosophy"));
        list.add(new CategoryModel("","Programming"));
        list.add(new CategoryModel("","OOP"));
        list.add(new CategoryModel("","Literature"));
        list.add(new CategoryModel("","Sports"));
        list.add(new CategoryModel("","Superaltives around The World"));
        list.add(new CategoryModel("","Who is who"));
        list.add(new CategoryModel("","English"));
        list.add(new CategoryModel("","Social Studies"));
        list.add(new CategoryModel("","Miscellaneous"));
    }
    */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
}