package camera.highlight.highlight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osojin on 2016. 3. 23..
 */
public class CategoryList implements Serializable{

    private static final String tags[] = {"", "SNS", "맛집", "지도", "세일", "기념일"};
    @SerializedName("tags")
    @Expose
    public List<Category> items = new ArrayList<>();

    public CategoryList() {
        for(String tag: tags) {
            Category category = new Category();
            category.tag = tag;
            this.items.add(category);
        }
    }
}
