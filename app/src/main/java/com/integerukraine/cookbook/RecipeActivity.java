package com.integerukraine.cookbook;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.integerukraine.cookbook.utils.Convertations;

public class RecipeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fabAtImage;
    private FloatingActionButton fabAtBottom;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        findViews();
        initViewsWithData();
        initToolBar();
        initLists();
        setUiListeners();


    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabAtImage = (FloatingActionButton) findViewById(R.id.fab);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.recipe_coordinator_layout);
        fabAtBottom = new FloatingActionButton(this);

    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.back);
        collapsingToolbarLayout.setExpandedTitleTypeface(Typeface.createFromAsset(getAssets(), "fonts/arial_black.ttf"));
        collapsingToolbarLayout.setTitle("Some dish name");

        // Orange status bar for Android L +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }

    private void initLists() {

    }

    private void setUiListeners() {
        // Listener for buttons
        View.OnClickListener buttonsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.fab:
                        Snackbar.make(fabAtBottom, "Go on write some listener for me!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null);
                    break;
                }

            }
        };
        // Listener which decides show fab at image or at bottom
        ((AppBarLayout) findViewById(R.id.app_bar)).addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    // Expanded
                    fabAtImage.show();
                    fabAtBottom.hide();
                } else {
                    // Collapsed
                    fabAtImage.hide();
                    fabAtBottom.show();
                }

            }
        });
        // Back button on toolbar handling
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        fabAtImage.setOnClickListener(buttonsListener);
    }

    private void initViewsWithData(){
        // Inits fab at bottom params
        fabAtBottom.setImageDrawable(fabAtImage.getDrawable());
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(Convertations.dpToPx(16, this), Convertations.dpToPx(16, this), Convertations.dpToPx(16, this), Convertations.dpToPx(16, this));
        params.gravity = Gravity.BOTTOM | Gravity.END;
        fabAtBottom.setLayoutParams(params);
        coordinatorLayout.addView(fabAtBottom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_expand) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
