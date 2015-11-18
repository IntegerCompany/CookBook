package com.integerukraine.cookbook;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by andrew on 17.11.15.
 */
public class CookbookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "m1AWmUSnQIjstai2E9plenU1jJvwHJofbTSx7Xgj", "lOkMaYrQHlmJIbQBzGCrULllioh9GvBxLiZWjs37");
    }
}
