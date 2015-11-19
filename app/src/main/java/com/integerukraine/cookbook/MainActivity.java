package com.integerukraine.cookbook;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.integerukraine.cookbook.adapter.MainGridAdapter;
import com.integerukraine.cookbook.parse.ParseKey;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    // Views
    Toolbar toolbar;
    FloatingActionButton fab;
    RecyclerView mainGrid;

    //Stuff for lists
    MainGridAdapter mainGridAdapter;
    StaggeredGridLayoutManager gridLayoutManager;

    //Primitives

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setSupportActionBar(toolbar);
        initLists();
        setUiListeners();




    }

    /**
     * Used to create anything what need to main grid
     */
    private void initLists() {
        int columns = 2; // HARDCODED
        mainGridAdapter = new MainGridAdapter(columns /* TODO put cached recipes into adapter */);
        gridLayoutManager = new StaggeredGridLayoutManager(columns,1);
        mainGrid.setLayoutManager(gridLayoutManager);
        mainGrid.setAdapter(mainGridAdapter);
    }

    /**
     * Used to turn views in not null state, basically by using findViewById(int) method
     */
    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mainGrid = (RecyclerView) findViewById(R.id.main_grid);

    }

    /**
     * Used to create all kind of UI listeners - OnClickListener, OnScrollListener, etc...
     */
    private void setUiListeners() {
        View.OnClickListener buttonsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.fab:
                        queryForParseDate();
                        break;
                }

            }
        };

        fab.setOnClickListener(buttonsListener);
    }

    private void queryForParseDate(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseKey.RECIPE);
        query.include(ParseKey.Recipe.GRID_IMAGE);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    // RESULT - OK
                    ArrayList<ParseObject> arrayList = new ArrayList<>();
                    Log.d("score", "Retrieved: " + scoreList.size());

                    for (ParseObject parseObject : scoreList) {
                        arrayList.add(parseObject);
                    }
                    mainGridAdapter.add(arrayList);

                } else {
                    // ERROR HANDLING

                    Log.d("score", "Error: " + e.getMessage());
                }
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
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initFloatingActionBtn() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    private void initDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }



}
