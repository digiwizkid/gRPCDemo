package com.example.grpcdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.grpcdemo.actions.GetScore;
import com.example.grpcdemo.actions.TutorialActionServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String host = "10.248.50.194";
        int port = 8080;

        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Res: " + new GrpcTask().execute(host), Toast.LENGTH_LONG).show()
        );

    }

    private static class GrpcTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            ManagedChannel channel = ManagedChannelBuilder.forAddress(strings[0], 8080).usePlaintext().build();

            TutorialActionServiceGrpc.TutorialActionServiceBlockingStub stub = TutorialActionServiceGrpc.newBlockingStub(channel);

            GetScore.GetScoreRequest request = GetScore.GetScoreRequest.newBuilder().setName("Subha").build();

            GetScore.GetScoreResponse response = null;
            try {
                response = stub.getScore(request);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response.getMessage();
        }
    }
}