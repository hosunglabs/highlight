package camera.highlight.highlight;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Environment;
import android.os.FileObserver;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CaptureObserverIntentService extends IntentService {

    // TODO: Rename actions, choose action names that describe tasks that this
    private static final String TAG = CaptureObserverIntentService.class.getName();

    public static final String CAPTURE_RESULT_PATH = "CAPTURE RESULT PATH";
    public static final String CAPTURE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/Screenshots/";


    private FileObserver observer;

    public CaptureObserverIntentService() {
        super("CaptureObserverIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "start service");

        observer = new FileObserver(CAPTURE_PATH) {
            @Override
            public void onEvent(int event, String path) {
                Log.e(TAG, "File Changed : " + CAPTURE_PATH + path);
                Log.e(TAG, "File Changed : " + event);

                if (event == 8) {
                    Intent serviceIntent = new Intent(CaptureObserverIntentService.this, HighlightActivity.class);
                    serviceIntent.putExtra(CAPTURE_RESULT_PATH, CAPTURE_PATH+path);
                    serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(serviceIntent);
                }
            }
        };

        observer.startWatching();
    }
}
