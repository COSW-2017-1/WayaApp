package abcd.com.waya.entities;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by PERSONAL on 9/05/2017.
 */

public class UserProfile {

    public String name;
    public String lname;
    public String about;
    public Bitmap profilePicture;
    public String rank;
    public String stats;
    public ArrayList<Bitmap> collaborations;

    public UserProfile(String name, String lname, String status, Bitmap profilePicture){
        this.name = name;
        this.lname = lname;
        this.about = status;
        this.profilePicture = profilePicture;
    }

    public UserProfile(String name, String lname, String status){
        this.name = name;
        this.lname = lname;
        this.about = status;
    }
}
