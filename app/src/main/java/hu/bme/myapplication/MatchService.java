package hu.bme.myapplication;

import java.util.ArrayList;

//Class to get information from backend using HTTP requests
public class MatchService {

    public boolean exists(int id){
        return true; //or false
    }

    public Match getMatch(int id){
        return new Match(); //return 1 match from Backend using API
    }

    public ArrayList<Match> getMatches(){
        //return all matches from Backend
        return new ArrayList<Match>();
    }

    public void deleteMatch(int id){
        //delete 1 match from Backend
    }
}
