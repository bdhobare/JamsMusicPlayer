package com.jams.music.player;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jams.music.player.Utils.Common;
import com.jams.music.player.WelcomeActivity.WelcomeActivity;

import java.io.File;

public class PermissionsActivity extends AppCompatActivity {
    Button grant;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        text=(TextView)findViewById(R.id.text);
        grant=(Button)findViewById(R.id.grant);
        grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(PermissionsActivity.this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},Common.WRITE_EXTERNAL_STORAGE);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case Common.WRITE_EXTERNAL_STORAGE:
                if (grantResults.length>0){
                    if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                        text.setText(getResources().getString(R.string.all_set));
                        grant.setVisibility(View.GONE);
                        //Create the default Playlists directory if it doesn't exist.
                        File playlistsDirectory = new File(Environment.getExternalStorageDirectory() + "/Playlists/");
                        if (!playlistsDirectory.exists() || !playlistsDirectory.isDirectory()) {
                            playlistsDirectory.mkdir();
                        }
                        //Send out a test broadcast to initialize the homescreen/lockscreen widgets.
                        sendBroadcast(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                        startActivity(new Intent(getApplicationContext(),WelcomeActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Permissions not granted", Toast.LENGTH_LONG).show();
                    }
                }
                return;
        }
    }
}
