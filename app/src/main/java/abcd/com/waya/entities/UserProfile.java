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
    public String cellphone;
    public String profileDownloadUrl;
    public Bitmap profilePicture;
    public String rank;
    public String contributions;
    public String stats;
    public ArrayList<Bitmap> collaborations;

    public UserProfile(String name, String lname, String status, Bitmap profilePicture){
        this.name = name;
        this.lname = lname;
        this.about = status;
        this.profilePicture = profilePicture;
    }

    public UserProfile(String name, String lname, String status, String cellphone, String profileDownloadUrl){
        this.name = name;
        this.lname = lname;
        this.about = status;
        this.cellphone = cellphone;
        this.profileDownloadUrl = profileDownloadUrl;
    }
}
