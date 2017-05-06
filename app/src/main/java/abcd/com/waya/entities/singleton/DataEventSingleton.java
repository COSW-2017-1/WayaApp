package abcd.com.waya.entities.singleton;

import java.util.ArrayList;
import java.util.List;

import abcd.com.waya.entities.DataBar;
import abcd.com.waya.entities.DataEvent;

/**
 * Created by PERSONAL on 24/04/2017.
 */

public class DataEventSingleton {

    List<DataEvent> data;

    private static DataEventSingleton instance = null;
    protected DataEventSingleton() {
        data = new ArrayList<>();
    }
    public static DataEventSingleton getInstance() {
        if(instance == null) {
            instance = new DataEventSingleton();
        }
        return instance;
    }

    public void setData(List<DataEvent> data) {
        this.data = data;
    }

    public List<DataEvent> getData() {
        return data;
    }
}
