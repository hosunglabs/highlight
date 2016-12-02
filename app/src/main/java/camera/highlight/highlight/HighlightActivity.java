package camera.highlight.highlight;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.theartofdev.edmodo.cropper.CropOverlayView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import camera.highlight.highlight.adapter.CategoryAdapter;
import camera.highlight.highlight.adapter.ScreenshotAdapter;
import camera.highlight.highlight.model.CategoryList;

public class HighlightActivity extends AppCompatActivity implements CropImageView.OnGetCroppedImageCompleteListener, CropImageView.OnOverlayTouchHappenListener {

    private static final String TAG = HighlightActivity.class.getName();

    private VisionServiceClient client;
    private Bitmap mCroppedImage;
    private String mCaptureImagePath;

    private EditText mTitleText;
    private CropImageView mCaptureImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight);

        mTitleText = (EditText) findViewById(R.id.title_text);
        mCaptureImage = (CropImageView) findViewById(R.id.capture_image);

        mCaptureImagePath = getIntent().getStringExtra(CaptureObserverIntentService.CAPTURE_RESULT_PATH);
        Bitmap bitmap = BitmapFactory.decodeFile(mCaptureImagePath);
        mCaptureImage.setImageBitmap(bitmap);
        mCaptureImage.setOnOverlayTouchHappenListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rename = mTitleText.getText().toString();
                File captureImageFile = new File(mCaptureImagePath);
                File recognizedImageFile = new File(CaptureObserverIntentService.CAPTURE_PATH, rename + ".png");
                if (captureImageFile.renameTo(recognizedImageFile)) {
                    Log.e(TAG, "Success");
                    Toast.makeText(getApplicationContext(), "변경된 이름으로 저장되었습니다", Toast.LENGTH_SHORT);
                    Intent intent = new Intent(HighlightActivity.this, CaptureObserverIntentService.class);
                    startService(intent);
                    finish();
                }
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.category_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(new CategoryAdapter(new CategoryList(), mTitleText));

        Log.e(TAG, mCaptureImagePath);

        if (client == null) {
            client = new VisionServiceRestClient(getString(R.string.subscription_key));
        }
    }

    @Override
    public void OnOverlayTouchHappen(CropImageView v, MotionEvent event) {

        Log.e(TAG, "ACTION_UP: Activity");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCaptureImage.getCroppedImageAsync(mCaptureImage.getCropShape(), 0, 0);
                final Handler innerHandler = new Handler();
                innerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRecognize();
                    }
                }, 50);
            }
        }, 50);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mCaptureImage.setOnGetCroppedImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCaptureImage.setOnGetCroppedImageCompleteListener(null);
    }

    @Override
    public void onGetCroppedImageComplete(CropImageView view, Bitmap bitmap, Exception error) {
        if (error == null) {
            mCroppedImage = bitmap;
        } else {
            Toast.makeText(mCaptureImage.getContext(), "Image crop failed: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void doRecognize() {
        mTitleText.setText("분석중...");

        try {
            new doRequest().execute();
        } catch (Exception e)
        {
            mTitleText.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mCroppedImage.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {
        // Store error message
        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence

            if (e != null) {
                mTitleText.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                OCR r = gson.fromJson(data, OCR.class);

                String result = "";
                for (Region reg : r.regions) {
                    for (Line line : reg.lines) {
                        for (Word word : line.words) {
                            result += "#" + word.text;
                        }
                    }
                }

                mTitleText.setText(result);
            }
        }
    }
}
