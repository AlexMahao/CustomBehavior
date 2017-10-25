package com.spearbothy.custombehavior.alipay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spearbothy.custombehavior.R;
import com.spearbothy.custombehavior.refresh.SwipeRefreshLayout;
import com.spearbothy.custombehavior.refresh.SwipeRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahao on 17-9-5.
 */

public class AliPayActivity extends AppCompatActivity {

    private RecyclerView mRecycler;
    private List<String> mData = new ArrayList<>();
    private MyAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private CoordinatorLayout mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay);
        mRootView = (CoordinatorLayout) findViewById(R.id.root);
        initData();
        mRecycler = (RecyclerView) findViewById(R.id.content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter();
        mRecycler.setAdapter(mAdapter);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipeRefreshLayoutDirection direction) {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefreshing();
                    }
                }, 2000);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add(i + "");
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getLayoutInflater().inflate(R.layout.item_behavior, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
