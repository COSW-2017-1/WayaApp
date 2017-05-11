package abcd.com.waya.entities.singleton;

import java.util.ArrayList;
import java.util.List;

import abcd.com.waya.entities.DataEvent;

/**
 * Created by PERSONAL on 10/05/2017.
 */

public class UserProfileSingleton {

    Boolean logged;
    List<DataEvent> data;

    private static UserProfileSingleton instance = null;

    protected UserProfileSingleton() {
        data = new ArrayList<>();
        logged = false;
    }

    public static UserProfileSingleton getInstance() {
        if(instance == null) {
            instance = new UserProfileSingleton();
        }
        return instance;
    }

    public void setData(List<DataEvent> data) {
        this.data = data;
    }

    public List<DataEvent> getData() {
        return data;
    }

    public Boolean getLogged(){
        return logged;
    }

    public void setLogged(Boolean logged){
        this.logged = logged;
    }
}
