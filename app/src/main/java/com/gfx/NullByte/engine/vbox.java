package com.gfx.NullByte.engine;

import android.content.Context;
import android.util.Log;


import java.io.DataOutputStream;
import java.io.IOException;

public class vbox {

    public static boolean get_graphics_file(Context ctx, String gamename, String filename, String NewFilename,String NewFilename2 ){

        try {
            Process suProcess = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

            os.writeBytes("adb shell" + "\n");

            os.flush();


            os.writeBytes("cp -rf /storage/emulated/0/Android/data/"+gamename+"/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/SaveGames/"+filename+" "+ctx.getExternalFilesDir(null)+"/"+NewFilename+"\n");
            os.writeBytes("cp -rf "+ctx.getExternalFilesDir(null)+"/"+NewFilename+" "+ctx.getExternalFilesDir(null)+"/"+NewFilename2+"\n");

            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean push_active_shadow_file(Context ctx,String gamename,String filename, String NewFilename){
        try {
            Process suProcess = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());

            os.writeBytes("adb shell" + "\n");

            os.flush();

            os.writeBytes("cp -rf "+ctx.getExternalFilesDir(null)+"/"+filename+" /storage/emulated/0/Android/data/"+gamename+"/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/SaveGames/"+NewFilename+"\n");

            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
