package camera.highlight.highlight;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import camera.highlight.highlight.adapter.ScreenshotAdapter;

public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = SearchResultActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        String search = getIntent().getStringExtra(MainActivity.KEY_SEARCH);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(search);

        RecyclerView rv = (RecyclerView) findViewById(R.id.screenshot_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(new ScreenshotAdapter(ScreenshotAdapter.getScreenshotList(search)));
    }
}
