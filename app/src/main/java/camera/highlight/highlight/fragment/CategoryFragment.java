package camera.highlight.highlight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import camera.highlight.highlight.CaptureObserverIntentService;
import camera.highlight.highlight.R;
import camera.highlight.highlight.adapter.ScreenshotAdapter;
import camera.highlight.highlight.model.Screenshot;

public class CategoryFragment extends Fragment {

    private static final String TAG = CategoryFragment.class.getName();

    private static final String KEY_TAG = "TAG";

    public static CategoryFragment newInstance(String tag) {

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TAG, tag);

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();

        Log.e(TAG, args.getString(KEY_TAG));
        String category = args.getString(KEY_TAG);

        TextView tag = (TextView) view.findViewById(R.id.tag);
        tag.setText(category);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.screenshot_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(new ScreenshotAdapter(ScreenshotAdapter.getScreenshotList(category)));
    }
}
