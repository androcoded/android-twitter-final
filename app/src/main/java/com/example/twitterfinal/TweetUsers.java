package com.example.twitterfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class TweetUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar mToolbar;
    private ListView mListView;
    private ArrayList<String> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_users);
        mListView = findViewById(R.id.listVIew);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        gettingAllUserFromParse();
        mListView.setOnItemClickListener(this);
    }

    private void gettingAllUserFromParse() {
        users = new ArrayList<>();
        ParseQuery<ParseUser> allUser = ParseUser.getQuery();
        allUser.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        allUser.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                try {
                    if (objects.size()>0 && e==null){
                        for (ParseUser user: objects){
                            users.add(user.getUsername());
                        }
                    }else{
                        Toast.makeText(TweetUsers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        ParseUser.logOut();
                    }
                    mListView.setAdapter(new ArrayAdapter<String>(TweetUsers.this,android.R.layout.simple_list_item_checked,
                            android.R.id.text1, users));
                    mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                    for (String mUser : users){
                        if (ParseUser.getCurrentUser().getList("fanOf")!=null) {

                            if (ParseUser.getCurrentUser().getList("fanOf").contains(mUser)) {
                                mListView.setItemChecked(users.indexOf(mUser), true);
                            }

                        }

                    }
                }catch (Exception i){
                    i.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mntLogout:
                ParseUser.logOut();
                startActivity(new Intent(TweetUsers.this,LoginActivity.class));
                finish();
                break;
            case R.id.mntSendTweet:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()){
            Toast.makeText(this, users.get(position)+" is followed", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().add("fanOf",users.get(position));
        }else{
            Toast.makeText(this, users.get(position)+" is unfollowed", Toast.LENGTH_SHORT).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(users.get(position));
            List fanFollowedList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",fanFollowedList);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(TweetUsers.this, "Saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TweetUsers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
