package hu.bme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CamerasActivity extends AppCompatActivity {
    CameraService cameraService = new CameraService();
    String cameraIP = "";
    String matchID = "";
    public CamerasActivity(){

    }

    public CamerasActivity(CameraService serviceInstance){

        this.cameraService = serviceInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cameras);
        String src = getIntent().getStringExtra("src");
        matchID = getIntent().getStringExtra("MATCHID");
        cameraIP = src;
        Button btn = findViewById(R.id.btnaddcamera);
        btn.setText("ADD CAMERA");
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                addCamera(cameraIP);
            }
        });

        TextView textView = findViewById(R.id.textViewMatchID);
        textView.setText(matchID);

    }

    public void addCamera(String src){
        this.cameraService.addCamera(src);
    }
}
