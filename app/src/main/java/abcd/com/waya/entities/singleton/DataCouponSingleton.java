package abcd.com.waya.entities.singleton;

import java.util.ArrayList;
import java.util.List;

import abcd.com.waya.entities.DataBar;
import abcd.com.waya.entities.DataCoupon;

/**
 * Created by PERSONAL on 24/04/2017.
 */

public class DataCouponSingleton {

    List<DataCoupon> data;

    private static DataCouponSingleton instance = null;
    protected DataCouponSingleton() {
        data = new ArrayList<>();
    }
    public static DataCouponSingleton getInstance() {
        if(instance == null) {
            instance = new DataCouponSingleton();
        }
        return instance;
    }

    public void setData(List<DataCoupon> data) {
        this.data = data;
    }

    public List<DataCoupon> getData() {
        return data;
    }
}
