package camera.highlight.highlight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by osojin on 2016. 3. 23..
 */
public class Category implements Serializable{

    @SerializedName("tag")
    @Expose
    public String tag;
}
