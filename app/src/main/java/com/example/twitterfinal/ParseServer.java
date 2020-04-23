package com.example.twitterfinal;

import android.app.Application;

import com.parse.Parse;

public class ParseServer extends Application {
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("7C1f4ML0uD1x2JbeUcvEOBHB3gSv0z1HfuKqpi8h")
                .clientKey("udBLL74RjIlEbqVLyI3BkDnK7H3MKoItur6GVaJA")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
