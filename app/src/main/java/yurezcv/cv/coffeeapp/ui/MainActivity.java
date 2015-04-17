package yurezcv.cv.coffeeapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import yurezcv.cv.coffeeapp.R;
import yurezcv.cv.coffeeapp.Values;
import yurezcv.cv.coffeeapp.api.RequestsManager;
import yurezcv.cv.coffeeapp.bus.BusProvider;
import yurezcv.cv.coffeeapp.bus.ServerErrorEvent;
import yurezcv.cv.coffeeapp.bus.ServerResponseEvent;
import yurezcv.cv.coffeeapp.types.Coffee;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView mCoffeeList;
    private List<Coffee> mData;

    // SwipeRefreshLayout allows the user to swipe the screen down to trigger a manual refresh
    private SwipeRefreshLayout mSwipeRefreshLayout;

    RequestsManager mManager = new RequestsManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoffeeList = (ListView) findViewById(R.id.lvCoffeeList);
        mCoffeeList.setOnItemClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.primary,
                R.color.primary_dark,
                R.color.primary_dark);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mData = new ArrayList<>();

        mManager.getCoffeeList();
    }

    @Subscribe
    public void onServerResponse(ServerResponseEvent event) {
        enableDisableSwipeRefresh(true);
        onRefreshingStateChanged(false);
        // clear previous data
        mData.clear();
        mData.addAll(event.getData());
        // update UI
        CoffeeListAdapter adapter = (CoffeeListAdapter) mCoffeeList.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            mCoffeeList.setAdapter(new CoffeeListAdapter(this, mData));
        }
    }

    @Subscribe
    public void onServerError(ServerErrorEvent event) {
        enableDisableSwipeRefresh(true);
        onRefreshingStateChanged(false);
        mCoffeeList.setAdapter(null);
        TextView emptyText = new TextView(this);
        emptyText.setText(R.string.error_text);
        emptyText.setTextColor(getResources().getColor(R.color.dark_grey));
        mCoffeeList.setEmptyView(emptyText);
        Toast.makeText(this, event.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Values.TAG_EXTRA_COFFEE, mData.get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        enableDisableSwipeRefresh(false);
        mManager.getCoffeeList();
    }

    protected void onRefreshingStateChanged(boolean refreshing) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(refreshing);
        }
    }

    protected void enableDisableSwipeRefresh(boolean enable) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setEnabled(enable);
        }
    }
}
