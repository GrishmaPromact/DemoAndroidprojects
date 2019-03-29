package com.promact.camerademo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.otaliastudios.cameraview.AspectRatio;
import com.otaliastudios.cameraview.CameraUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class PicturePreviewActivity extends Activity {
    List<ImageModel> imageModelList = new ArrayList<>();
    private static WeakReference<byte[]> image;
    private ImageView doneIV;
    private RelativeLayout transparentOverlayTop;

    public static void setImage(@Nullable byte[] im) {
        image = im != null ? new WeakReference<>(im) : null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        final ImageView imageView = findViewById(R.id.image);
        doneIV = (ImageView) findViewById(R.id.done);
        transparentOverlayTop = (RelativeLayout)findViewById(R.id.transparentOverlayTop);

        // final MessageView actualResolution = findViewById(R.id.actualResolution);
        // final MessageView approxUncompressedSize = findViewById(R.id.approxUncompressedSize);
        final long delay = getIntent().getLongExtra("delay", 0);
        final int nativeWidth = getIntent().getIntExtra("nativeWidth", 0);
        final int nativeHeight = getIntent().getIntExtra("nativeHeight", 0);
        byte[] b = image == null ? null : image.get();
        if (b == null) {
            finish();
            return;
        }
        doneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("Hello", image.getAbsolutePath());
                intent.putExtra("Hi", (Serializable) imageModelList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        CameraUtils.decodeBitmap(b, 1000, 1000, new CameraUtils.BitmapCallback() {
            @Override
            public void onBitmapReady(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);

                // approxUncompressedSize.setTitle("Approx. uncompressed size");
                // approxUncompressedSize.setMessage(getApproximateFileMegabytes(bitmap) + "MB");

                // ncr and ar might be different when cropOutput is true.
                AspectRatio nativeRatio = AspectRatio.of(nativeWidth, nativeHeight);
                //mCapturingPicture = false;
                //long callbackTime = System.currentTimeMillis();
                String mFileName = getFilesDir() + "/Images";

                String mVideoFileName = String.valueOf("TempFolder" + System.currentTimeMillis());
                if (!new File(mFileName).exists()) {
                    (new File(mFileName)).mkdir();
                }

                File image = new File(mFileName, mVideoFileName + ".jpg");
                String imagePath = image.getAbsolutePath();
                ImageModel imageModel = new ImageModel();
                imageModel.setImagePath(imagePath);
                imageModelList.add(imageModel);
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    Log.e("CameraActivity", e.getMessage(), e);
                }
                Log.e("CameraActivity::", "Image File:" + image.getAbsolutePath());

                // AspectRatio finalRatio = AspectRatio.of(bitmap.getWidth(), bitmap.getHeight());
                // actualResolution.setTitle("Actual resolution");
                // actualResolution.setMessage(bitmap.getWidth() + "x" + bitmap.getHeight() + " (" + finalRatio + ")");
            }
        });

    }

    private static float getApproximateFileMegabytes(Bitmap bitmap) {
        return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024 / 1024;
    }

}
