package abhishekwl.github.io.radar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraView;

import java.util.Arrays;

public class MainActivity extends Activity {

    private CameraView rearCameraView;
    private CameraView frontCameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeComponents();
    }

    private void initializeComponents() {

    }

    private void initializeViews() {
        rearCameraView = findViewById(R.id.mainRearCameraView);
        setupRearViewCamera();
        frontCameraView = findViewById(R.id.mainFrontCameraView);
        setupFrontViewCamera();
    }

    private void setupFrontViewCamera() {
        frontCameraView.addFrameProcessor(frame -> {
            byte[] frameByteArrayData = frame.getData();
            long frameTimeStamp = frame.getTime();
            Log.v("FRONT_DATA", Arrays.toString(frameByteArrayData));
            Log.v("FRONT_TIMESTAMP", String.valueOf(frameTimeStamp));
            Bitmap frameBitmap = BitmapFactory.decodeByteArray(frameByteArrayData, 0, frameByteArrayData.length);
        });
    }

    private void setupRearViewCamera() {
        rearCameraView.addFrameProcessor(frame -> {
            byte[] frameByteArrayData = frame.getData();
            long frameTimeStamp = frame.getTime();
            Log.v("REAR_DATA", Arrays.toString(frameByteArrayData));
            Log.v("REAR_TIMESTAMP", String.valueOf(frameTimeStamp));
            Bitmap frameBitmap = BitmapFactory.decodeByteArray(frameByteArrayData, 0, frameByteArrayData.length);
        });
    }

    private void notifyMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        rearCameraView.open();
        frontCameraView.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rearCameraView.close();
        frontCameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rearCameraView.destroy();
        frontCameraView.destroy();
    }
}
