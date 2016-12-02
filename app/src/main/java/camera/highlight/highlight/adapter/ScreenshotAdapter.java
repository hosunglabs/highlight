package camera.highlight.highlight.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import camera.highlight.highlight.model.Screenshot;

/**
 * Created by hosungkim on 2016. 3. 23..
 */
public class ScreenshotAdapter extends RecyclerView.Adapter<ScreenshotAdapter.ScreenshotViewHolder> {

    public static final String KEY_DETAIL = "KEY_DETAIL";

    private List<Screenshot> screenshotList;

    public class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView date;

        public ScreenshotViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

    public ScreenshotAdapter(List<Screenshot> screenshotList) {
        this.screenshotList = screenshotList;
    }

    @Override
    public ScreenshotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_screenshot, parent, false);
        return new ScreenshotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ScreenshotViewHolder holder, int position) {
        final Screenshot screenshot = screenshotList.get(position);

        Glide.with(holder.image.getContext())
                .load(screenshot.uriString)
                .fitCenter()
                .into(holder.image);

        holder.name.setText(screenshot.name);
        holder.date.setText(screenshot.date);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(KEY_DETAIL, screenshot.uriString);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return screenshotList.size();
    }

    public static List<Screenshot> getScreenshotList(String category) {
        List<Screenshot> screenshotList = new ArrayList<>();
        String path = CaptureObserverIntentService.CAPTURE_PATH;

        File files = new File(path);
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

        if(files.listFiles().length > 0) {
            for(File file: files.listFiles()) {

                Screenshot screenshot = new Screenshot();

                if(category.equals("") || file.getName().contains(category)) {
                    screenshot.uriString = path + file.getName();
                    screenshot.name = file.getName();
                    screenshot.date = dateFormat.format(new Date(file.lastModified()));
                    screenshotList.add(screenshot);
                }
            }
        }
        Collections.reverse(screenshotList);
        return screenshotList;
    }
}