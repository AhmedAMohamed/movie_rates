package movierater.mal.ahmedalaa.com.movierates;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private FragmentRefreshListener fragmentRefreshListener;

    public boolean mTwoPane;

    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.details_frame) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
            /*
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_frame, new DetailsActivityFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            */
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        prefs = getSharedPreferences("preferences", 0);
        editor = prefs.edit();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.popularity) {
            editor.putInt("order_by", 0);
            editor.apply();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
        }
        else if (id == R.id.top_rated) {
            editor.putInt("order_by", 1);
            editor.apply();
            if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
        }
        else if (id == R.id.fav_item) {
            editor.putInt("order_by", 2);
            editor.apply();
            if (getFragmentRefreshListener() != null) {
                getFragmentRefreshListener().onRefresh();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public interface FragmentRefreshListener{
        void onRefresh();
    }
}
