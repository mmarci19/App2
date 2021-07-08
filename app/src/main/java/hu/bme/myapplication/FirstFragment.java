package hu.bme.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.WIFI_SERVICE;

public class FirstFragment extends Fragment {

    private static final int YES_NO_CALL = 1;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(getActivity(), CameraActivity.class);
                FirstFragment.this.startActivity(myIntent);

            }
        });


        view.findViewById(R.id.add_match).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });

        view.findViewById(R.id.view_all_matches).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MatchAdapterActivity.class);
                FirstFragment.this.startActivity(intent);

            }
        });

        view.findViewById(R.id.addcamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCameraDialog();

            }
        });
    }


    public void openDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        alertDialog.setTitle("Enter Match ID");

        final EditText input = new EditText(this.getContext());

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);


        String text = "";

        alertDialog.setPositiveButton("ADD",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String text = input.getText().toString();
                        Intent intent = new Intent(getActivity(), MatchActivity.class);
                        FirstFragment.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("BACK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    public void openCameraDialog(){
        Intent intent = new Intent(getActivity(), CamerasActivity.class);
        WifiManager wm = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String port = "1234";
        intent.putExtra("src", ip + ":" + port);
        FirstFragment.this.startActivity(intent);
    }


}
