package hu.bme.myapplication;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MatchActivity extends AppCompatActivity {
    MatchService matchService = null;
    Match match = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchadd_activity);

        EditText owner;
        EditText users;
        TextView currowner;
        TextView currusers;
        TextView ownerText;
        TextView userText;
        TextView currUsersVariable;
        TextView currOwnerVariable;


        Button btn = findViewById(R.id.btnuser);
        Button btn2 = findViewById(R.id.btnowner);
        currowner = findViewById(R.id.textcurrentowner);
        currusers = findViewById(R.id.textcurrentuser);
        owner = findViewById(R.id.owner);
        users = findViewById(R.id.users);


    }
    private Match getMatch(int id) {
        Match match = null;
        if (this.matchService.exists(id)) {
            match = this.matchService.getMatch(id);
        } else {
            match = new Match();
        }
        return match;
    }

    private void addMatch(String user, String owner, int id){
        this.matchService.addMatch();
    }



}
