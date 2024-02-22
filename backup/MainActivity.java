package com.gfx.NullByte;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gfx.NullByte.engine.aow;
import com.gfx.NullByte.engine.vbox;
import com.gfx.NullByte.gfxlibrary.Game;
import com.gfx.NullByte.shell.utility;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView status;
    Button getFps,setFps,Save,getgraphicStyle,setgraphicstyle;
    Game gfxobj;

    boolean isaowengine = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.statusMessage);
        getFps = findViewById(R.id.GetFps);
        setFps = findViewById(R.id.SetFps);
        Save = findViewById(R.id.Save);
        getgraphicStyle = findViewById(R.id.GetGraphicsStyle);
        setgraphicstyle = findViewById(R.id.SetGraphicstyle);

        if(utility.isRootGiven()){

            vbox.get_graphics_file(MainActivity.this,"com.pubg.imobile","Active.sav","old.abenkgfx","new.abenkgfx");
          //  Toast.makeText(MainActivity.this,"ldplayer",Toast.LENGTH_LONG).show();
            isaowengine = false;

        }else{
            aow.get_graphics_file(MainActivity.this,"com.pubg.imobile","Active.sav","old.abenkgfx");
            aow.save_graphics_file(MainActivity.this,"old.abenkgfx","new.abenkgfx");

            Toast.makeText(MainActivity.this,"Gameloop",Toast.LENGTH_LONG).show();
            isaowengine = true;
        }


        try {
            File file = new File(getExternalFilesDir(null)+"/"+"old.abenkgfx");
            if(file.exists()){
                gfxobj= new Game(getExternalFilesDir(null)+"/"+"old.abenkgfx");
            }else {
                Log.i("AnimeTone","File cant be found");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        getFps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gfxobj != null){
                    status.setText("Status : "+gfxobj.getFPS());
                }

            }
        });

        setFps.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                gfxobj.setFps("90 fps");
                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                status.setText("Status : "+gfxobj.getFPS());
            }
        });

        getgraphicStyle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if(gfxobj!=null){
                    status.setText("Status : "+gfxobj.getGraphicsStyle());
                }
            }
        });

        setgraphicstyle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                gfxobj.setGraphicsStyle("Colorful");
                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                status.setText("Status : "+gfxobj.getGraphicsStyle());
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isaowengine){
                    aow.push_active_shadow_file(MainActivity.this,"Com.pubg.imobile","new.abenkgfx","Active.sav");
                }else{
                    vbox.push_active_shadow_file(MainActivity.this,"Com.pubg.imobile","new.abenkgfx","Active.sav");
                }

            }
        });
    }
}