package hu.bme.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MatchActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    MatchService matchService = new MatchService();
    Match match = null;
    String matchID = "";
    String _owner = "";
    String _user = "";
    ImageButton imageButton;
    EditText owner;
    EditText users;
    Button btn;
    Button btn2;
    Button btn3 ;
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

        final TextView currusers;

        TextView ownerText;
        TextView userText;
        TextView currUsersVariable;
        final TextView currOwnerVariable;



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
        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow,
                R.id.textviewID, _users) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View inflatedView = super.getView(position, convertView, parent);

                // set a click listener
                // TODO change "R.id.buttonId" to reference the ID value you set for the button's android:id attribute in foodlist.xml
                inflatedView.findViewById(R.id.imagebtndelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    listAdapter.remove(listAdapter.getItem(position));
                    listAdapter.notifyDataSetChanged();}
                });
                return inflatedView;

            }
        };

        //listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, _users);

        usersListView.setAdapter( listAdapter );


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _user = users.getText().toString();
                _users.add(_user);
                listAdapter.notifyDataSetChanged();
                users.setText("");
            }

        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _owner = owner.getText().toString();
                owner.setText("");
                currOwnerVariable.setText(_owner);
                btn2.setAlpha(0.3f);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMatch();
                Toast.makeText(MatchActivity.this,"Match sent to server!",Toast.LENGTH_SHORT).show();
                users.setEnabled(false);
                owner.setEnabled(false);
                btn.setEnabled(false);
                btn2.setEnabled(false);
                listAdapter.clear();

                listAdapter.notifyDataSetChanged();
                usersListView.setAdapter(listAdapter);
                owner.setText("");
                _owner="";
                _users= new ArrayList<String>();
                _user="";

            }
        });



    }


    public Match getMatch(int id) {
        btn = findViewById(R.id.btnuser);
        btn2 = findViewById(R.id.btnowner);
        btn3 = findViewById(R.id.btnadd);
        owner = findViewById(R.id.owner);
        users = findViewById(R.id.users);

        if(owner.getText().toString()=="" && users.getText().toString()==""){
            btn3.setAlpha(.5f);
        }
        else{
            btn3.setAlpha(1f);
        }

        Match match = null;

        if (this.matchService.exists(matchID)) {
            Toast.makeText(this,"This match already exists, cannot make changes.", Toast.LENGTH_SHORT).show();
            //users.setEnabled(false);
            //owner.setEnabled(false);
            //btn.setEnabled(false);
            //btn2.setEnabled(false);
            //btn3.setEnabled(false);
            match = this.matchService.getMatch(id);

        } else {

            Toast.makeText(this,"New match created, enter users and owner.", Toast.LENGTH_SHORT).show();
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
