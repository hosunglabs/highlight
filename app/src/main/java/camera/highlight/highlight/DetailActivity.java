package camera.highlight.highlight;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import camera.highlight.highlight.adapter.ScreenshotAdapter;
import camera.highlight.highlight.model.Screenshot;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getName();

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final String uriString = getIntent().getStringExtra(ScreenshotAdapter.KEY_DETAIL);

        ImageView detailImage = (ImageView) findViewById(R.id.detail_image);

        Glide.with(detailImage.getContext())
                .load(uriString)
                .fitCenter()
                .into(detailImage);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.fromFile(new File(uriString));

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "공유하기"));
            }
        });
    }
}
