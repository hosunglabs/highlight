package camera.highlight.highlight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by osojin on 2016. 3. 23..
 */
public class Screenshot implements Serializable{

    @SerializedName("uri_string")
    @Expose
    public String uriString;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("date")
    @Expose
    public String date;
}
