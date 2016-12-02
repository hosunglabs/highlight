package camera.highlight.highlight.fragment;

/**
 * Created by hosungkim on 2016. 3. 24..
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import camera.highlight.highlight.CaptureObserverIntentService;
import camera.highlight.highlight.model.Category;
import camera.highlight.highlight.model.Screenshot;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = CategoryFragmentPagerAdapter.class.getName();

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private String mTitle;


    public CategoryFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Fragment createFragment(String title) {
        return CategoryFragment.newInstance(title);
    }

    public void setCategory(List<Category> categoryList) {

        for(Category category: categoryList) {
            mTitle = category.tag;
            mFragmentTitles.add("#" + mTitle);
            mFragments.add(createFragment(mTitle));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}