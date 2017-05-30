package com.ratings.doctors.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ratings.doctors.R;
import com.ratings.doctors.core.AppController;

public class MainActivity extends BaseActivity {
    DrawerLayout mDrawerLayout;
    CoordinatorLayout mCoordinatorLayout;
    ActionBar mActionBar;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_36dp);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (mAuth.getCurrentUser() != null)
            Log.d(AppController.TAG, getUid());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (mAuth.getCurrentUser() != null) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        } else {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_signin) {
            openActivity(SignInActivity.class);
            return true;
        } else if (i == R.id.action_signout) {
            signOut();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void delete() {
        mSpinnerDialog.show();
        mDatabase.child("users").child(getUid()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                mSpinnerDialog.hide();
            }
        });

    }

    private void update() {
        mSpinnerDialog.show();
        mDatabase.child("users").child(getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("age").setValue(10);
                mSpinnerDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(AppController.TAG, databaseError.getMessage());
            }
        });
    }

    private void signOut(){
        new MaterialDialog.Builder(MainActivity.this)
                .title(getResources().getString(R.string.btn_signout))
                .content(getResources().getString(R.string.txt_confirmation))
                .positiveText(getResources().getString(R.string.txt_positive_btn))
                .negativeText(getResources().getString(R.string.txt_negative_btn))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        FirebaseAuth.getInstance().signOut();
                        restartActivity(MainActivity.this);
                    }
                })
                .show();
    }
}
