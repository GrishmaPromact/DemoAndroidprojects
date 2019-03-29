package com.promact.camerademo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button openCamera;
    private ImageView imageDisplay;
    private TextView imagePath;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<ImageModel> imageModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openCamera = (Button)findViewById(R.id.openCamera);
        imageDisplay = (ImageView)findViewById(R.id.captureImage);
        imagePath = (TextView)findViewById(R.id.imagePath);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CameraActivity.class);
                startActivityForResult(i,0);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        imageAdapter = new ImageAdapter(MainActivity.this,imageModelList);
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode == RESULT_OK){
           // String imageUri= data.getExtras().getString("Hello");
            imageModelList= (List<ImageModel>) data.getExtras().getSerializable("Hi");
            imageAdapter.setItems(imageModelList);
            //imageAdapter = new ImageAdapter(MainActivity.this,imageModelList);
            //recyclerView.setAdapter(imageAdapter);
            imageDisplay.setImageURI(Uri.parse(imageModelList.get(0).getImagePath()));
            imagePath.setText(imageModelList.get(0).getImagePath());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
