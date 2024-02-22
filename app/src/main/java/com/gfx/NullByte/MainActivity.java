package com.gfx.NullByte;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.CursorWindowAllocationException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gfx.NullByte.engine.aow;
import com.gfx.NullByte.engine.vbox;
import com.gfx.NullByte.gfxlibrary.Game;
import com.gfx.NullByte.shell.utility;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {


    SeekBar slider;
    LinearLayout image1,image2,image3,image4,image5,save,smooth,balanced,hd,hdr,ultrahd,uhd,low,medium,high,ultra,extreme,maxfps;
    TextView tppval,status;

    boolean isaowengine = false;

    Game gfxobj;

    ImageView discord,info;

    private Spinner spinner;
    private List<String> packageList;

    String Currentpkg;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.Statusbar));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        image1 = findViewById(R.id.classic);
        image2 = findViewById(R.id.colorful);
        image3 = findViewById(R.id.realistic);
        image4 = findViewById(R.id.soft);
        image5 = findViewById(R.id.movie);
        tppval = findViewById(R.id.tppval);
        save = findViewById(R.id.save);

        status = findViewById(R.id.status);

        low = findViewById(R.id.low);
        medium = findViewById(R.id.medium);
        high = findViewById(R.id.high);
        ultra = findViewById(R.id.ultra);
        extreme = findViewById(R.id.extreme);
        maxfps = findViewById(R.id.maxfps);

        smooth = findViewById(R.id.smooth);
        balanced = findViewById(R.id.balanced);
        hd = findViewById(R.id.hd);
        hdr = findViewById(R.id.hdr);
        ultrahd = findViewById(R.id.ultrahd);
        uhd = findViewById(R.id.uhd);

        info = findViewById(R.id.info);

        discord = findViewById(R.id.discoed);
        spinner = findViewById(R.id.spinner);
        packageList = new ArrayList<>();

        String[] predefinedPackages = {"com.pubg.imobile", "com.tencent.ig", "com.pubg.krmobile","com.vng.pubgmobile","com.rekoo.pubgm"};
        for (String packageName : predefinedPackages) {
            if (utility.appInstalledOrNot(packageName,MainActivity.this)) {
                packageList.add(packageName);
            }
        }


        if(utility.isRootGiven()){

            isaowengine = false;
            status.setText("Status : Vbox engine Detected..");

        }else{

            isaowengine = true;
            status.setText("Status : Aow engine Detected..");

        }


        Glide.with(this)
                .load(R.drawable.discord)
                .into(discord);

        Glide.with(this)
                .load(R.drawable.info)
                .into(info);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, packageList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Optional: Handle spinner item selection if needed
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Currentpkg = parent.getItemAtPosition(position).toString();
                 status.setText("Status : Package  "+Currentpkg);

                if(isaowengine){

                    aow.get_graphics_file(MainActivity.this,Currentpkg,"Active.sav","old.abenkgfx");
                    aow.save_graphics_file(MainActivity.this,"old.abenkgfx","new.abenkgfx");

                }else{
                    vbox.get_graphics_file(MainActivity.this,Currentpkg,"Active.sav","old.abenkgfx","new.abenkgfx");
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InitializeUI();
                        Log.i("Thread","called");
                    }
                }, 3000);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("pkg", Currentpkg);
            }
        });



        discord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/Hq2M7PhTXk"));
                startActivity(browserIntent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AestheticDialog.Builder(MainActivity.this, DialogStyle.FLAT, DialogType.INFO)
                        .setTitle("info")
                        .setMessage("Developped by : NullByte" +
                                "\n" +"Credit : idea by LMTY"+"\n"+"Join Our Discord For more Updates.")
                        .setCancelable(false)
                        .setDarkMode(true)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(new OnDialogClickListener() {
                            @Override
                            public void onClick(AestheticDialog.Builder dialog) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discord.gg/Hq2M7PhTXk"));
                                startActivity(browserIntent);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        smooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(smooth);



                gfxobj.setGraphicsSettings("Smooth");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(balanced);
                deselectButton(hd);
                deselectButton(hdr);
                deselectButton(ultrahd);
                deselectButton(uhd);

            }
        });

        balanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(balanced);

                gfxobj.setGraphicsSettings("Balanced");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(smooth);
                deselectButton(hd);
                deselectButton(hdr);
                deselectButton(ultrahd);
                deselectButton(uhd);
            }
        });

        hd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(hd);

                gfxobj.setGraphicsSettings("HD");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(balanced);
                deselectButton(smooth);
                deselectButton(hdr);
                deselectButton(ultrahd);
                deselectButton(uhd);
            }
        });

        hdr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(hdr);

                gfxobj.setGraphicsSettings("HDR");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(balanced);
                deselectButton(hd);
                deselectButton(smooth);
                deselectButton(ultrahd);
                deselectButton(uhd);
            }
        });

        ultrahd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(ultrahd);

                gfxobj.setGraphicsSettings("Ultra HD");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(balanced);
                deselectButton(hd);
                deselectButton(hdr);
                deselectButton(smooth);
                deselectButton(uhd);
            }
        });

        uhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(uhd);

                gfxobj.setGraphicsSettings("Ultra HD");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(balanced);
                deselectButton(hd);
                deselectButton(hdr);
                deselectButton(ultrahd);
                deselectButton(smooth);
            }
        });

        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(low);

                gfxobj.setFps("Low");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(medium);
                deselectButton(high);
                deselectButton(ultra);
                deselectButton(extreme);
                deselectButton(maxfps);

            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(medium);

                gfxobj.setFps("Medium");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(low);
                deselectButton(high);
                deselectButton(ultra);
                deselectButton(extreme);
                deselectButton(maxfps);
            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(high);

                gfxobj.setFps("High");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(medium);
                deselectButton(low);
                deselectButton(ultra);
                deselectButton(extreme);
                deselectButton(maxfps);
            }
        });

        ultra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(ultra);

                gfxobj.setFps("Ultra");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(medium);
                deselectButton(high);
                deselectButton(low);
                deselectButton(extreme);
                deselectButton(maxfps);
            }
        });

        extreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(extreme);

                gfxobj.setFps("Extreme");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                deselectButton(medium);
                deselectButton(high);
                deselectButton(ultra);
                deselectButton(low);
                deselectButton(maxfps);
            }
        });

        maxfps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(gfxobj == null){
                    return;
                }

                toggleSelectionbutton(maxfps);


                gfxobj.setFps("90 fps");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                deselectButton(medium);
                deselectButton(high);
                deselectButton(ultra);
                deselectButton(extreme);
                deselectButton(low);
            }
        });


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gfxobj == null){
                    return;
                }

                toggleSelection(image1);

                gfxobj.setGraphicsStyle("Classic");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Deselect other images if needed
                deselectImage(image2);
                deselectImage(image3);
                deselectImage(image4);
                deselectImage(image5);

            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gfxobj == null){
                    return;
                }

                toggleSelection(image2);

                gfxobj.setGraphicsStyle("Colorful");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Deselect other images if needed
                deselectImage(image1);
                deselectImage(image3);
                deselectImage(image4);
                deselectImage(image5);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gfxobj == null){
                    return;
                }

                toggleSelection(image3);

                gfxobj.setGraphicsStyle("Realistic");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Deselect other images if needed
                deselectImage(image1);
                deselectImage(image2);
                deselectImage(image4);
                deselectImage(image5);
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gfxobj == null){
                    return;
                }

                toggleSelection(image4);

                gfxobj.setGraphicsStyle("Soft");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Deselect other images if needed
                deselectImage(image1);
                deselectImage(image2);
                deselectImage(image3);
                deselectImage(image5);
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gfxobj == null){
                    return;
                }

                toggleSelection(image5);

                gfxobj.setGraphicsStyle("Movie");

                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Deselect other images if needed
                deselectImage(image1);
                deselectImage(image2);
                deselectImage(image3);
                deselectImage(image4);
            }
        });

        slider = findViewById(R.id.slider);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gfxobj == null){
                    return;
                }

                if(Currentpkg.isEmpty()){
                    status.setText("Status : Package name is empty..");
                    return ;
                }

                toggleSelectionbutton(save);
                if(isaowengine){
                    aow.push_active_shadow_file(MainActivity.this,Currentpkg,"new.abenkgfx","Active.sav");
                }else{
                    vbox.push_active_shadow_file(MainActivity.this,Currentpkg,"new.abenkgfx","Active.sav");
                }
                status.setText("Status : File has been saved ..");
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void InitializeUI(){

        //=============================intializing gfxlibrary==============================================//
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

        if(gfxobj ==null){
            return ;
        }

       // slider.setProgress(0);
        slider.setProgress(gfxobj.getTPPintval());
        tppval.setText("TppView : "+gfxobj.getTPPval());
        slider.setMax(200);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Handle progress change
                gfxobj.SetTppView(progress);
                tppval.setText("TppView : "+gfxobj.getTPPval());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tppval.setText("TppView : "+gfxobj.getTPPval());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Called when the user releases the
                try {
                    gfxobj.saveActiveSavFile(getExternalFilesDir(null)+"/"+"new.abenkgfx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tppval.setText("TppView : "+gfxobj.getTPPval());}
        });

        //===============fps==============================//
        if(gfxobj.getFPS().equals("Low")){
            toggleSelectionbutton(low);
            deselectButton(medium);
            deselectButton(high);
            deselectButton(ultra);
            deselectButton(extreme);
            deselectButton(maxfps);

        }else if(gfxobj.getFPS().equals("Medium")){
            toggleSelectionbutton(medium);
            deselectButton(low);
            deselectButton(high);
            deselectButton(ultra);
            deselectButton(extreme);
            deselectButton(maxfps);

        }else if(gfxobj.getFPS().equals("High")){
            toggleSelectionbutton(high);
            deselectButton(medium);
            deselectButton(low);
            deselectButton(ultra);
            deselectButton(extreme);
            deselectButton(maxfps);

        }else if(gfxobj.getFPS().equals("Ultra")){
            toggleSelectionbutton(ultra);
            deselectButton(medium);
            deselectButton(high);
            deselectButton(low);
            deselectButton(extreme);
            deselectButton(maxfps);

        }else if(gfxobj.getFPS().equals("Extreme")){
            toggleSelectionbutton(extreme);
            deselectButton(medium);
            deselectButton(high);
            deselectButton(ultra);
            deselectButton(low);
            deselectButton(maxfps);

        }else if(gfxobj.getFPS().equals("90 fps")){
            toggleSelectionbutton(maxfps);
            deselectButton(medium);
            deselectButton(high);
            deselectButton(ultra);
            deselectButton(extreme);
            deselectButton(low);

        }else if(gfxobj.getFPS().equals("Not Found")){
            status.setText("Status : Frame Rate is out of Range");

        }else{
            status.setText("Status : Frame Rate is out of Range");

        }

        //===============================style=====================================//
        if(gfxobj.getGraphicsStyle().equals("Classic")){
            toggleSelection(image1);
            deselectImage(image2);
            deselectImage(image3);
            deselectImage(image4);
            deselectImage(image5);

        }else if(gfxobj.getGraphicsStyle().equals("Colorful")){
            toggleSelection(image2);
            deselectImage(image1);
            deselectImage(image3);
            deselectImage(image4);
            deselectImage(image5);

        }else if(gfxobj.getGraphicsStyle().equals("Realistic")){
            toggleSelection(image3);
            deselectImage(image1);
            deselectImage(image2);
            deselectImage(image4);
            deselectImage(image5);

        }else if(gfxobj.getGraphicsStyle().equals("Soft")){
            toggleSelection(image4);
            deselectImage(image1);
            deselectImage(image3);
            deselectImage(image2);
            deselectImage(image5);

        }else if(gfxobj.getGraphicsStyle().equals("Movie")){
            toggleSelection(image5);
            deselectImage(image1);
            deselectImage(image3);
            deselectImage(image4);
            deselectImage(image2);

        }else if(gfxobj.getGraphicsStyle().equals("Not Found")){
            status.setText("Status : Style is out of Range");

        }else{
            status.setText("Status : Style is out of Range");

        }

//=====================================graphics======================================================//
        if(gfxobj.getGraphicsSettings().equals("Smooth")){
            toggleSelectionbutton(smooth);
            deselectButton(balanced);
            deselectButton(hd);
            deselectButton(hdr);
            deselectButton(ultrahd);

        }else if(gfxobj.getGraphicsSettings().equals("Balanced")){
            toggleSelectionbutton(balanced);
            deselectButton(smooth);
            deselectButton(hd);
            deselectButton(hdr);
            deselectButton(ultrahd);

        }else if(gfxobj.getGraphicsSettings().equals("HD")){
            toggleSelectionbutton(hd);
            deselectButton(smooth);
            deselectButton(balanced);
            deselectButton(hdr);
            deselectButton(ultrahd);


        }else if(gfxobj.getGraphicsSettings().equals("HDR")){
            toggleSelectionbutton(hdr);
            deselectButton(smooth);
            deselectButton(hd);
            deselectButton(balanced);
            deselectButton(ultrahd);


        }else if(gfxobj.getGraphicsSettings().equals("Ultra HD")){
            toggleSelectionbutton(ultrahd);
            deselectButton(smooth);
            deselectButton(hd);
            deselectButton(hdr);
            deselectButton(balanced);


        }else if(gfxobj.getGraphicsSettings().equals("Not Found")){
            status.setText("Status : Graphics Quality is out of Range");

        }else{
            status.setText("Status : Graphics Quality is out of Range");

        }

    }

    private void toggleSelection(LinearLayout imageView) {
        imageView.setSelected(true);
    }

    private void deselectImage(LinearLayout imageView) {
        imageView.setSelected(false);
    }

    private void toggleSelectionbutton(LinearLayout imageView) {

        imageView.setSelected(true);
        imageView.setBackgroundResource(R.drawable.fps_ckecked);

    }
    private void deselectButton(LinearLayout imageView) {
        imageView.setSelected(false);
        imageView.setBackgroundResource(R.drawable.fps);
    }
}