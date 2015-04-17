package yurezcv.cv.coffeeapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import yurezcv.cv.coffeeapp.R;
import yurezcv.cv.coffeeapp.Utils;
import yurezcv.cv.coffeeapp.Values;
import yurezcv.cv.coffeeapp.api.RequestsManager;
import yurezcv.cv.coffeeapp.bus.BusProvider;
import yurezcv.cv.coffeeapp.bus.ServerErrorEvent;
import yurezcv.cv.coffeeapp.bus.ServerResponseEvent;
import yurezcv.cv.coffeeapp.types.Coffee;


public class DetailsActivity extends ActionBarActivity {

    private TextView mTitleTextView;
    private TextView mDetailsTextView;
    private ImageView mImageView;
    private TextView mLastUpdateTextView;

    private Coffee mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Coffee item = getIntent().getParcelableExtra(Values.TAG_EXTRA_COFFEE);

        mTitleTextView = (TextView) findViewById(R.id.tvDetailsTitle);
        mDetailsTextView = (TextView) findViewById(R.id.tvDetailsDescription);
        mImageView = (ImageView) findViewById(R.id.ivDetailsImage);
        mLastUpdateTextView = (TextView) findViewById(R.id.tvDetailsLastUpdate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RequestsManager manager = new RequestsManager();
        manager.getCoffeeById(item.getId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Subscribe
    public void onServerResponse(ServerResponseEvent event) {
        mItem = event.getData().get(0);
        mTitleTextView.setText(mItem.getName());
        mDetailsTextView.setText(mItem.getDesc());

        if (Utils.isEmpty(mItem.getImageURL())) {
            mImageView.setVisibility(View.GONE);
        } else {
            mImageView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(mItem.getImageURL()) // .fit().centerCrop()
                    .into(mImageView);
        }

        mLastUpdateTextView.setText(calculateLastUpdateTime(mItem.getLastUpdate()));
    }

    private String calculateLastUpdateTime(String lastUpdate) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        DateTime lastDateTime = DateTime.parse(lastUpdate, fmt);
        DateTime currentDateTime = DateTime.now();
        Period period = new Period(lastDateTime, currentDateTime);
        PeriodFormatter formatter = new PeriodFormatterBuilder().appendYears().appendSuffix(" year ", " years ").
                appendMonths().appendSuffix(" month ", " months ").appendWeeks().appendSuffix(" week ", " weeks ").
                appendDays().appendSuffix(" day", " days").printZeroNever().toFormatter();

        return new StringBuilder("Updated ").append(formatter.print(period)).append(" ago").toString();
    }

    @Subscribe
    public void onServerError(ServerErrorEvent event) {
        Toast.makeText(this, event.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TITLE, mItem.getName());
            sendIntent.putExtra(Intent.EXTRA_TEXT, mItem.getDesc());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
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
}
