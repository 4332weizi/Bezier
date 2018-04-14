package io.auxo.bezier;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private RecyclerViewAdapter mAdapter = new RecyclerViewAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("标题");
        //替换系统的actionBar
        setSupportActionBar(toolbar);

        initView();

        BezierView view = findViewById(R.id.bezier);
        view.setUpWithAppBarLayout((AppBarLayout) findViewById(R.id.appbar), R.id.tab_layout);
    }

    private List<String> getData(int pager) {
        List<String> data = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            data.add("pager" + pager + " 第" + i + "个item");
        }
        return data;
    }

    private void initView() {

        //设置TabLayout
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        for (int i = 1; i < 4; i++) {
            tabLayout.addTab(tabLayout.newTab().setText("TAB" + i));
        }

        //TabLayout的切换监听
        tabLayout.addOnTabSelectedListener(this);

        //设置RecycleView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setData(getData(1));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //切换的时候更新RecyclerView
        mAdapter.setData(getData(tab.getPosition() + 1));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
