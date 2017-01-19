package com.android.thoughtworks.flows;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.thoughtworks.App;
import com.android.thoughtworks.R;
import com.android.thoughtworks.helpers.KingModelHelper;
import com.android.thoughtworks.model.King;
import com.android.thoughtworks.model.KingDb;
import com.android.thoughtworks.service.FetchDataService;
import com.android.thoughtworks.utils.AlertToastUtils;
import com.android.thoughtworks.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by akanksha on 8/1/17.
 */
public class MainListActivity extends AppCompatActivity implements View.OnClickListener, FilterDialogCallback {

    private ArrayList<King> kings = new ArrayList<>();
    private RadioGroup pageNosView;
    private SearchView searchBar;
    private int pageSize = 0;
    private int pageCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        fetchData();
        initViews();
    }

    private void initViews() {
        pageNosView = (RadioGroup) findViewById(R.id.page_nos);
        searchBar = (SearchView) findViewById(R.id.search_bar);
        searchBar.setOnClickListener(this);
        ImageView ivFilter = (ImageView) findViewById(R.id.iv_filter);
        ivFilter.setOnClickListener(this);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<King> kings = KingModelHelper.getKingsBySearchName(query);
                initList(kings);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    initList(kings);
                }
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.FETCHED_API_SUCCESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadSuccessReceiver, filter);
        filter = new IntentFilter(Constants.FETCHED_API_FAILURE);
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadFailureReceiver, filter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_filter:
                FilterDialog filterDialog = new FilterDialog(this, this);
                filterDialog.showDialog();
                break;
            case R.id.search_bar:
                searchBar.onActionViewExpanded();
                break;
        }
    }

    /**
     * Initializing the list and pages
     */
    private void initList(ArrayList<King> kings) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new KingsListAdapter(MainListActivity.this, kings));
        pageSize = kings.size() / 4;
        pageSize = kings.size() % 4 != 0 ? ++pageSize : pageSize;
        initPageUI();
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem % 4 == 0) {
//                    pageCount++;
//                    changePageUI();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void fetchData() {
        AlertToastUtils.showProgressDialog(this, "Loading...");
        if (App.getprefs().getBoolean(Constants.IS_FIRST_LOAD, false)) {
            new FetchDbAsyncTask().execute();
        } else {
            Intent intent = new Intent(this, FetchDataService.class);
            startService(intent);
        }
    }

    /**
     * Paging related
     */
    private void initPageUI() {
        pageNosView.removeAllViews();
        for (int i = 1; i <= pageSize; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_pageno, null, false);
            RadioButton tvPageNo = (RadioButton) view.findViewById(R.id.cb_page_no);
            tvPageNo.setTag(i);
            tvPageNo.setText("" + i);
            if (i == 1) {
                tvPageNo.setChecked(true);
            } else {
                tvPageNo.setChecked(false);
            }
            pageNosView.addView(view);
        }
    }

    private void changePageUI() {
        boolean isPage;
        for (int i = 1; i <= pageSize; i++) {
            if (i == pageCount) {
                isPage = true;
            } else {
                isPage = false;
            }
            ((RadioButton) (pageNosView.findViewWithTag(pageCount))).setChecked(isPage);
        }
    }

    private BroadcastReceiver downloadSuccessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                ArrayList<King> kings = intent.getParcelableArrayListExtra(Constants.FETCHED_API_RESULT);
                if (kings != null) {
                    MainListActivity.this.kings = kings;
                    initList(kings);
                }
            }
            AlertToastUtils.stopProgressDialog();
        }
    };

    private BroadcastReceiver downloadFailureReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AlertToastUtils.stopProgressDialog();
            String error = intent.getStringExtra(Constants.FETCHED_API_ERROR);
            onError(error);
        }
    };

    private void onError(String errMessage) {
        AlertToastUtils.createToast(this, errMessage);
    }

    @Override
    public void onFilterButtonClick(int type) {
        ArrayList<King> kings;
        if (type == 0) {
            kings = this.kings;
        } else {
            kings = KingModelHelper.getKingsBySearchType(type == 1 ? Constants.ATTACK : Constants.DEFENSE);
        }
        initList(kings);
    }

    /**
     * Fetch from Db
     */
    private class FetchDbAsyncTask extends AsyncTask<Void, Void, ArrayList<King>> {
        @Override
        protected ArrayList<King> doInBackground(Void... params) {
            RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
            Realm realm = Realm.getInstance(realmConfig);
            RealmResults<KingDb> list = realm.allObjects(KingDb.class);
            ArrayList<King> kings = new ArrayList<>();
            for (KingDb kingDb : list) {
                kings.add(new King().getKingFromDb(kingDb));
            }
            return kings;
        }

        @Override
        protected void onPostExecute(ArrayList<King> kings) {
            super.onPostExecute(kings);
            initList(kings);
            MainListActivity.this.kings = kings;
            AlertToastUtils.stopProgressDialog();
        }
    }

}
