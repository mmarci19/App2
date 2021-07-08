package hu.bme.myapplication;

import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

//Class to get information from backend using HTTP requests
public class MatchService  {



    private class DeleteRunnable implements Runnable {
        String result = "";


        public String deleteMatchHttp(){
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

                final int id = 1;
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", id);

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


        public String addMatchHttp() {
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
                jsonParam.put("id", id);
                jsonParam.put("owner", owner);
                jsonParam.put("users", user);

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

        public String getMatchHttp(){
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

                final int id = 1;
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id", id);

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


    public boolean exists(int id){
        if(this.getMatch(id) != null) {
            return true;
        }
        return false;
    }

    public Match getMatch(int id){

        String result = this.getMatchRunnable.getMatchHttp();
        return new Match();
        //return 1 match from Backend using API
    }

    public ArrayList<Match> getMatches(){
        //return all matches from Backend
        String result = this.getMatchesRunnable.getAllMatchesHttp();
        return new ArrayList<Match>();
    }

    public void addMatch(){
        String result = this.addMatchRunnable.addMatchHttp();
    }



    public void deleteMatch(int id){
       String result = this.deleteRunnable.deleteMatchHttp();
    }

}
