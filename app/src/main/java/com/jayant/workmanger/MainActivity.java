package com.jayant.workmanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jayant.workmanger.Workers.BlurWorker;
public class MainActivity extends AppCompatActivity {
    private Button click;
    public static ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.image2);
        click=findViewById(R.id.click);
        final OneTimeWorkRequest request=new OneTimeWorkRequest.Builder(BlurWorker.class)
                .build();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               WorkManager.getInstance(getApplicationContext()).enqueue(request);
                imageView.setImageURI(BlurWorker.outputUri);
            }
        });


    }

}
