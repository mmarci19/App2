package hu.bme.myapplication;

import java.util.ArrayList;

public class Match {
    private String id ="";
    private String owner = "";
    private ArrayList<String> users = new ArrayList<String>();

    public String getId(){
        return id;
    }
    public String getOwner(){
        return owner;
    }
    public ArrayList<String> getUsers(){
        return users;
    }


    public void setId(String _id){
        id=_id;
    }
    public void setOwner(String _owner){
        owner = _owner;
    }
    public void setUsers(ArrayList<String> _users){
        users=_users;
    }
    public void addUser(String user){
        this.users.add(user);
    }

}
