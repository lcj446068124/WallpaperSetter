package com.deitel.recycleviewtest;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.deitel.recycleviewtest.R;
import com.deitel.recycleviewtest.base.BaseActivity;
import com.deitel.recycleviewtest.base.BaseFragment;
import com.deitel.recycleviewtest.base.Contracts;
import com.deitel.recycleviewtest.base.RVBean;
import com.deitel.recycleviewtest.base.StringPresenter;
import com.deitel.recycleviewtest.broadcast.NetStatusBroadcastReceiver;
import com.deitel.recycleviewtest.ui.BingFragment;
import com.deitel.recycleviewtest.ui.BooruFragment;
import com.deitel.recycleviewtest.ui.main.MainActivityContract;
import com.deitel.recycleviewtest.util.ConfigUtils;
import com.deitel.recycleviewtest.util.SnackbarUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

import static com.deitel.recycleviewtest.R2.drawable.ic_filter_list_white_24dp;


public class MainActivity extends BaseActivity implements MainActivityContract.View{
    @BindView(R.id.frame_layout_fragment_container)
    FrameLayout mFrameLayoutFragmentContainer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    //private Drawer drawer;
    private NetStatusBroadcastReceiver mNetStatusBroadcastReceiver;

    //private MainActivityPresenter mPresenter;

    private boolean isRunning;
    private boolean isSafe;
    private TextView mTextViewHitokoto;
    private TextView mTextViewHitokotoSrc;
    private BaseFragment currentFragment;

    private void initNetBroadcastReceiver() {
        mNetStatusBroadcastReceiver = new NetStatusBroadcastReceiver();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetStatusBroadcastReceiver, mFilter);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initNetBroadcastReceiver();
        init();
    }
    private void init() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_how_many_time);
        mToolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(isSafe){
                    currentFragment = BooruFragment.newInstance(Contracts.Url.DANBOORU);
                    showFragment(currentFragment);
                    setTitle(Contracts.Menu.MENU_DANBOORU);
                    isSafe = false;
                    SnackbarUtils.create(MainActivity.this,"呀!(//▽//)");
                    return true;
                }
                else{
                    currentFragment = BingFragment.newInstance(Contracts.Url.BING);
                    showFragment(currentFragment);
                    setTitle(Contracts.Menu.MENU_BING);
                    isSafe = true;
                    return true;
                }

            }
        });
        //mPresenter = new MainActivityPresenter(this);
        isRunning = false;
    }

    @Override
    protected void setFirstFragment() {
        //currentFragment = BooruFragment.newInstance(Contracts.Url.DANBOORU);
        currentFragment = BingFragment.newInstance(Contracts.Url.BING);
        showFragment(currentFragment);
        isSafe = true;
        //setTitle(Contracts.Menu.MENU_DANBOORU);
        setTitle(Contracts.Menu.MENU_BING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                long openCount = ConfigUtils.getOpenCount(this);
                String s ="你已经打开应用"+openCount+"次了!";
                SnackbarUtils.create(this,s);
                break;
            case R.id.action_change_span_count:
                EventBus.getDefault().postSticky(new RVBean(true, false));
                break;
            case R.id.action_touch_to_top:
                EventBus.getDefault().postSticky(new RVBean(false, true));
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mNetStatusBroadcastReceiver != null) {
            unregisterReceiver(mNetStatusBroadcastReceiver);
        }
    }
    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.frame_layout_fragment_container;
    }

    @Override
    public void onBackPressed() {
        exit();
    }
}
