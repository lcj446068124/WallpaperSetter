package com.deitel.recycleviewtest.ui.watch;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.deitel.recycleviewtest.R;
import com.deitel.recycleviewtest.base.BaseActivity;
import com.deitel.recycleviewtest.glide.GlideApp;
import com.deitel.recycleviewtest.util.FileUtils;
import com.deitel.recycleviewtest.util.SnackbarUtils;
import com.github.chrisbanes.photoview.PhotoView;
import com.john.waveview.WaveView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class WatchActivity extends BaseActivity {

    @BindView(R.id.button_download)
    Button mButtonDownload;
    @BindView(R.id.photo_view_image)
    PhotoView mPhotoViewImage;
    @BindView(R.id.wave_view_loading)
    WaveView mWaveViewLoading;
    @BindView(R.id.text_view_progress)
    TextView mTextViewProgress;
    @BindView(R.id.frame_layout_progress)
    FrameLayout mFrameLayoutProgress;
    @BindView(R.id.linear_layout_bottom_sheet)
    LinearLayout mLinearLayoutBottomSheet;
    @BindView(R.id.image_view_indicator)
    ImageView mImageViewIndicator;
    @BindView(R.id.text_view_description)
    TextView mTextViewDescription;
    @BindView(R.id.scroll_view_container)
    ScrollView mScrollViewContainer;
    private BottomSheetBehavior<LinearLayout> mBehavior;
    private DownloadListener mDownloadListener;
    private DownloadTask mDownloadTask;
    private boolean isDownload = true;
    private boolean isClick;
    private OkDownload mOkDownload;
    private String mTag;
    private File f;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission
                .WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = WatchActivity.this;
        isClick = false;
        mBehavior = BottomSheetBehavior.from(mLinearLayoutBottomSheet);
        mOkDownload = OkDownload.getInstance();
        mTag = UUID.randomUUID().toString();
        mButtonDownload.setEnabled(false);
    }

    @OnClick({R.id.button_download, R.id.image_view_indicator})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_download:
                isClick = true;
                mButtonDownload.setEnabled(false);
                mButtonDownload.setClickable(false);
                mButtonDownload.setText("loading。。。");
                exchangeBehavior();
                setWallPaper(f);
                break;
            case R.id.image_view_indicator:
                exchangeBehavior();
                break;
        }
    }

    private void exchangeBehavior() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    protected void handleIntent(Intent intent) {
        verifyStoragePermissions(this);
        String description = intent.getStringExtra("image_description");
        String url = intent.getStringExtra("image_url");
        String preview = intent.getStringExtra("image_preview");
        String folder = intent.getStringExtra("image_source");
        mTextViewDescription.setText(description);
        mDownloadListener = new DownloadListener(mTag) {
            @Override
            public void onStart(Progress progress) {

            }

            @Override
            public void onProgress(Progress progress) {
                mWaveViewLoading.setProgress(100 - (int) (progress.fraction * 100));
                mTextViewProgress.setText(String.valueOf((int) (progress.fraction * 100) + " %"));
            }

            @Override
            public void onError(Progress progress) {

            }

            @Override
            public void onFinish(File file, Progress progress) {
                mFrameLayoutProgress.setVisibility(View.GONE);
                if (file != null && file.exists()) {
                    f=file;
                    mButtonDownload.setEnabled(true);
                    GlideApp.with(mContext)
                            .load(file)
                            .dontAnimate()
                            .into(mPhotoViewImage);
                }


            }

            @Override
            public void onRemove(Progress progress) {

            }
        };
        if (mOkDownload.hasTask(url)) {
            mDownloadTask = mOkDownload.getTask(url);
            mDownloadTask.start();
        } else {
            GetRequest<File> request = OkGo.<File>get(url);
            mDownloadTask = OkDownload.request(url, request)
                    .extra1(preview)
                    .extra2(folder)
                    .folder(FileUtils.getDownloadDir() + folder)
                    .save();
            mDownloadTask.start();
        }
        mDownloadTask.register(mDownloadListener);
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_watch;
    }

    @Override
    protected void onResume() {
        mDownloadTask.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mDownloadListener != null) {
            mDownloadTask.unRegister(mDownloadListener);
        }
        if (!isDownload) {
            mDownloadTask.remove(true);
        }
        super.onDestroy();
    }

    protected void setWallPaper(File file){
        try {
//            WallpaperManager wpm = (WallpaperManager) this.getSystemService(
//                    Context.WALLPAPER_SERVICE);
            WallpaperManager wpm = WallpaperManager.getInstance(WatchActivity.this);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            if (bitmap != null) {
                wpm.setBitmap(bitmap);
                mButtonDownload.setText("设置成功");
                SnackbarUtils.create(this, "设置成功");
                Log.i("setwallpaper", "wallpaper not null");
            }

        } catch (IOException e) {
            SnackbarUtils.create(this, "哎呀，设置失败了...");
            mButtonDownload.setText("再来一次？");
            Log.e("setwallpaper", "Failed to set wallpaper: " + e);
        }
    }
}
