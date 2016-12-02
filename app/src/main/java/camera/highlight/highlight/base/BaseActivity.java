package camera.highlight.highlight.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import camera.highlight.highlight.CaptureObserverIntentService;
import camera.highlight.highlight.model.Category;
import camera.highlight.highlight.model.CategoryList;
import camera.highlight.highlight.model.Screenshot;

/**
 * Created by hosungkim on 2016. 3. 23..
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();

    protected List<Category> getCategoryList() {

        CategoryList categoryList = new CategoryList();
        return categoryList.items;
    }
}
