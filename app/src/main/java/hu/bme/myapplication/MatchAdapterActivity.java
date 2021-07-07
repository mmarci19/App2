package hu.bme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MatchAdapterActivity extends AppCompatActivity {
    ArrayList<Match> matches = new ArrayList<Match>();
    MatchService matchService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_adapter);
    }

    public void setMatchService(MatchService ms) {
        this.matchService = ms;
    }

    public ArrayList<Match> getMatches(){
        return this.matchService.getMatches();
    }
}
