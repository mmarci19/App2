package hu.bme.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//Class to get information from backend using HTTP requests
public class MatchService  {



    private class DeleteRunnable implements Runnable {
        String result = "";
        int _id;

        public String deleteMatchHttp(int id){
            Thread thread = new Thread(this);
            thread.start();
            _id=id;
            try {
                thread.join();
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }




        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.0.248:5000/delete_match");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);


                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", _id);

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                result = conn.getResponseMessage();
                JSONObject jsonResponse = new JSONObject(result.toString());
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private class AddMatchRunnable implements Runnable {
        String result = "";
        int _id;
        String _owner;
        ArrayList<String> _users;

        public String addMatchHttp(int id, String owner, ArrayList<String> users) {
            Thread thread = new Thread(this);
            thread.start();
            try {
                _id=id;
                _owner=owner;
                _users=users;
                thread.join();
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.0.248:5000/create_match");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                final int id = 1;
                final String owner = "OWNER";
                final String user = "USER";

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("id", _id);
                jsonParam.put("owner", _owner);
                JSONArray arr = new JSONArray();
                for(int i=0; i<_users.size();i++){
                    arr.put(_users.get(i));
                }
                jsonParam.put("users",arr);

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                result = conn.getResponseMessage();
                JSONObject jsonResponse = new JSONObject(result.toString());
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class GetMatchRunnable implements Runnable {
        String result = "";
        int _id;
        public String getMatchHttp(int id){
            Thread thread = new Thread(this);
            _id = id;
            thread.start();
            try {
                thread.join();
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.0.248:5000/get_match");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);


                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", _id);

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                result = conn.getResponseMessage();

                JSONObject jsonResponse = new JSONObject(result.toString());
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetMatchesRunnable implements Runnable {


        public String getAllMatchesHttp() {
            Thread thread = new Thread(this);
            thread.start();
            try {
                thread.join();
                return result;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }

        String result = "";
        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.0.248:5000/list_matches");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());
                result = conn.getResponseMessage();
                JSONObject jsonResponse = new JSONObject(result.toString());
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private DeleteRunnable deleteRunnable = new DeleteRunnable();
    private GetMatchRunnable getMatchRunnable = new GetMatchRunnable();
    private GetMatchesRunnable getMatchesRunnable = new GetMatchesRunnable();
    private AddMatchRunnable addMatchRunnable = new AddMatchRunnable();


    public boolean exists(String id){
        if(this.getMatch(new Integer(id)) != null) {
            return true;
        }
        return false;
    }

    public Match getMatch(int id){

        String result = this.getMatchRunnable.getMatchHttp(id);
        return new Match();
        //return 1 match from Backend using API
    }

    public ArrayList<Match> getMatches(){
        //return all matches from Backend
        String result = this.getMatchesRunnable.getAllMatchesHttp();

        return new ArrayList<Match>();
    }

    public void addMatch(int id, String owner, ArrayList<String> users){
        String result = this.addMatchRunnable.addMatchHttp(id,owner,users);
    }

    public void deleteMatch(int id)
    {
       String result = this.deleteRunnable.deleteMatchHttp(id);
    }

}
