package com.example.iberia.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;
import java.util.Date;
import java.text.DateFormat;
import java.util.Random;

import static android.R.string.no;
import static com.example.iberia.myapplication.R.id.fab;
import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private CalendarView calendar;
    private DateFormat mdf = DateFormat.getDateInstance(DateFormat.MEDIUM);
    private Random mRnd = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long now = mRnd.nextLong();
                long curr = currentTimeMillis();
                if (now < 0) now = now * (-1);
                if (now >= curr) now = now % curr;
                String rDate = mdf.format(new Date(now));
                doWAlpha(rDate);
            }
        });

        calendar = (CalendarView) findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Integer m = month + 1;
                Integer d = day;
                Integer y = year;
                String date = m.toString() + "/" + d.toString() + "/" + y.toString();
                doWAlpha(date);
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void doWAlpha(String date) {
        Uri dateq = Uri.parse("wolframalpha:?").buildUpon()
                .appendQueryParameter("i", date)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage("com.wolfram.android.alpha");
        intent.setData(dateq);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + date + ", no receiving apps installed!");
        }
    }
}
