package hu.bme.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    MatchService matchService = new MatchService();
    Match match = null;
    String matchID = "";
    String _owner = "";
    String _user = "";
    EditText owner;
    EditText users;
    private ArrayAdapter<String> listAdapter ;
    ListView usersListView;
    ArrayList<String> _users = new ArrayList<String>();

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);

    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestPermission();
        setContentView(R.layout.matchadd_activity);
        matchID = getIntent().getStringExtra("MATCHID");
        match = getMatch(Integer.valueOf(matchID));

        TextView currowner;

        TextView currusers;

        TextView ownerText;
        TextView userText;
        TextView currUsersVariable;
        TextView currOwnerVariable;

        Button btn = findViewById(R.id.btnuser);
        Button btn2 = findViewById(R.id.btnowner);
        Button btn3 = findViewById(R.id.btnadd);

        currowner = findViewById(R.id.textcurrentowner);
        currusers = findViewById(R.id.textcurrentuser);

        currOwnerVariable = findViewById(R.id.textcurrentownervariable);


        if(match.getOwner()!=""){
            currOwnerVariable.setText(match.getOwner());
        }
        else
        {
            currOwnerVariable.setText("No owner");
        }

        usersListView = (ListView) findViewById( R.id.mainListView );

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, _users);

        usersListView.setAdapter( listAdapter );


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _user = users.getText().toString();
                _users.add(_user);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _owner = owner.getText().toString();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatch();
            }
        });

    }


    public Match getMatch(int id) {

        owner = findViewById(R.id.owner);
        users = findViewById(R.id.users);

        Match match = null;

        if (this.matchService.exists(matchID)) {
            Toast.makeText(this,"This match already exists, cannot make changes.", Toast.LENGTH_SHORT);
            owner.setEnabled(true);
            users.setEnabled(true);
            match = this.matchService.getMatch(id);

        } else {
            Toast.makeText(this,"New match created, enter users and owner.", Toast.LENGTH_SHORT);
            match = new Match();
            match.setId(String.valueOf(id));
        }
        return match;
    }

    public void addMatch(){
        this.matchService.addMatch(Integer.valueOf(matchID), _owner, _users);
    }

    public void deleteMatch(){
        this.matchService.deleteMatch(Integer.valueOf(matchID));
    }

    public ArrayList<Match> getMatches(){
        return this.matchService.getMatches();
    }


}
