package abcd.com.waya.entities.singleton;

import java.util.ArrayList;
import java.util.List;

import abcd.com.waya.entities.DataBar;

/**
 * Created by PERSONAL on 24/04/2017.
 */

public class DataBarSingleton {

    List<DataBar> data;

    private static DataBarSingleton instance = null;
    protected DataBarSingleton() {
        data = new ArrayList<>();
    }
    public static DataBarSingleton getInstance() {
        if(instance == null) {
            instance = new DataBarSingleton();
        }
        return instance;
    }

    public void setData(List<DataBar> data) {
        this.data = data;
    }

    public List<DataBar> getData() {
        return data;
    }
}
