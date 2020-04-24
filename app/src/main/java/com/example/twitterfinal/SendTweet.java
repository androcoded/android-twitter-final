package com.example.twitterfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTweet extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText edtSendTweet;
    private Button btnSentTweet;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        edtSendTweet = findViewById(R.id.edtSendTweet);
        btnSentTweet = findViewById(R.id.btnSendTweet);
        btnSentTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweetToParse();
            }
        });

        mListView = findViewById(R.id.listView);
        fentchingAllTweets();
    }

    private void fentchingAllTweets() {
        final ArrayList<HashMap<String,String>> users = new ArrayList<>();
        ParseQuery<ParseObject> tweetQuery = ParseQuery.getQuery("MyTweet");
        tweetQuery.whereEqualTo("user",ParseUser.getCurrentUser().getUsername());
        tweetQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                try {
                    if (objects.size()>0 && e == null){
                        for (ParseObject user: objects){
                            HashMap<String,String> stringHashMap = new HashMap<>();
                            stringHashMap.put("userName",user.getString("user"));
                            stringHashMap.put("tweet",user.getString("tweet"));
                            users.add(stringHashMap);
                        }

                    }else{
                        Toast.makeText(SendTweet.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        mListView.setVisibility(View.INVISIBLE);
                    }
                }catch (Exception i){
                    i.printStackTrace();
                }
                mListView.setAdapter(new SimpleAdapter(getApplicationContext(),users,android.R.layout.simple_list_item_2
                ,new String[]{"userName","tweet"},new int[]{android.R.id.text1, android.R.id.text2}));
            }
        });

    }

    private void sendTweetToParse() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait...");
        progressDialog.show();
        ParseObject myTweet = new ParseObject("MyTweet");
        myTweet.put("tweet",edtSendTweet.getText().toString());
        myTweet.put("user", ParseUser.getCurrentUser().getUsername());
        myTweet.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(SendTweet.this, ParseUser.getCurrentUser().getUsername()+
                            "'s tweet uploaded", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SendTweet.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
