package camera.highlight.highlight.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import camera.highlight.highlight.CaptureObserverIntentService;
import camera.highlight.highlight.DetailActivity;
import camera.highlight.highlight.R;
import camera.highlight.highlight.model.Category;
import camera.highlight.highlight.model.CategoryList;

/**
 * Created by hosungkim on 2016. 3. 23..
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private CategoryList categoryList;
    private EditText titleText;

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        Button categoryButton;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryButton = (Button) itemView.findViewById(R.id.category_button);
        }
    }

    public CategoryAdapter(CategoryList categoryList, EditText textView) {
        this.categoryList = categoryList;
        this.titleText = textView;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        final Category category = categoryList.items.get(position);
        final String categoryTag = "#" + category.tag;

        holder.categoryButton.setText(categoryTag);

        holder.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String originText = titleText.getText().toString();
                titleText.setText(categoryTag + originText);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.items.size();
    }
}