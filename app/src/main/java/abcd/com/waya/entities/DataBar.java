package abcd.com.waya.entities;

import android.graphics.Bitmap;

/**
 * Created by PERSONAL on 23/04/2017.
 */

public class DataBar {
    public String title;
    public String imgs;
    public Bitmap imgBm = null;
    public String description;
    public String longitud;
    public String latitud;
    public String tipo;
    public String direccion;
    public String genero;
    public String horario;


    public Bitmap getImage(){
        return imgBm;
    }

    public void setImage(Bitmap bm){
        if(imgBm == null){
            imgBm = bm;
        }
    }
}
