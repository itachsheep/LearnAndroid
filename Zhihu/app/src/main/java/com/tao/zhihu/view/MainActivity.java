package com.tao.zhihu.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.tao.zhihu.R;
import com.tao.zhihu.databinding.ActivityMainBinding;
import com.tao.zhihu.fragment.NewsListFragment;
import com.tao.zhihu.messenger.Messenger;
import com.tao.zhihu.utils.LogUtils;
import com.tao.zhihu.utils.ViewUtils;
import com.tao.zhihu.viewmodel.MainViewModel;
import com.trello.rxlifecycle.FragmentEvent;

import rx.subjects.BehaviorSubject;

/**
 * Created by SDT14324 on 2017/10/18.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding mbinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //enable actionbar
        setSupportActionBar(mbinding.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        actionbarHeighChangeListener();
        //drawerLayout enable toggle
        enableAndShowToggle();

        //naviagetion listener
        mbinding.mainNaviView.setNavigationItemSelectedListener(this);

        // Indicator must setViewPager after setAdapter,but data for ViewPager is load in other ViewModel
        Messenger.getDefault().register(this, MainViewModel.TOKEN_UPDATE_INDICATOR,
                () -> mbinding.mainCircleIndicator.setViewPager(mbinding.mainViewpager));

        //get data
        NewsListFragment fragment = new NewsListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();
    }

    private void enableAndShowToggle() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mbinding.mainDrawerLayout,
                mbinding.mainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        //set actionBarDrawer icon, click cannot show navigator view
        /*toggle.setDrawerIndicatorEnabled(false);
        mbinding.mainToolbar.setNavigationIcon(R.mipmap.zhihu);
        mbinding.mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Haha");
            }
        });*/

        mbinding.mainDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void actionbarHeighChangeListener() {
        //when AppBarLayout height changed ,this callback called
        mbinding.mainAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int height = appBarLayout.getHeight() - getSupportActionBar().getHeight() -
                ViewUtils.getStatusBarHeight(MainActivity.this);

                LogUtils.i(" onoffsetchanged statusbar height: "+height);
                /*int alpha = 255 * (0 - verticalOffset) / height;
                mbinding.mainCollapsingLayout.setExpandedTitleColor(Color.argb(0, 255, 255, 255));
                mbinding.mainCollapsingLayout.setCollapsedTitleTextColor(Color.argb(alpha, 255, 255, 255));*/
            }
        });
    }

    private void showToast(String mes){
        Toast.makeText(this,mes,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.nav_gallery :
                showToast("今日日报!!");
                break;
            case R.id.nav_test:
                showToast("test!");
                break;
            default:
                break;
        }

        mbinding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Messenger.getDefault().unregister(this);
    }

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();


}
