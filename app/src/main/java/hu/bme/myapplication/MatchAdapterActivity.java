package hu.bme.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MatchAdapterActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    ArrayList<Match> matches = new ArrayList<Match>();
    MatchService matchService = null;
    MyRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_adapter);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.matchesall);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adapter = new MyRecyclerViewAdapter(this, matches);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void setMatchService(MatchService ms) {
        this.matchService = ms;
    }


    public ArrayList<Match> getMatches(){
        return this.matchService.getMatches();
    }


}
