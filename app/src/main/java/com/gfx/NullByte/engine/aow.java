package com.gfx.NullByte.engine;

import android.content.Context;

import com.gfx.NullByte.shell.ShellExecuter;

public class aow {

    public static boolean get_graphics_file(Context ctx, String gamename, String filename, String NewFilename){
        ShellExecuter exe = new ShellExecuter();
        exe.Executer("cp -rf /storage/emulated/0/Android/data/"+gamename+"/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/SaveGames/"+filename+" "+ctx.getExternalFilesDir(null)+"/"+NewFilename);
        return true;
    }

    public static boolean push_active_shadow_file(Context ctx,String gamename,String filename, String NewFilename){
        ShellExecuter exe = new ShellExecuter();

        exe.Executer("cp -rf "+ctx.getExternalFilesDir(null)+"/"+filename+" /storage/emulated/0/Android/data/"+gamename+"/files/UE4Game/ShadowTrackerExtra/ShadowTrackerExtra/Saved/SaveGames/"+NewFilename);

        return true;
    }

    public static boolean save_graphics_file(Context ctx,String filename, String NewFilename){
        ShellExecuter exe = new ShellExecuter();

        exe.Executer("cp -rf "+ctx.getExternalFilesDir(null)+"/"+filename+" "+ctx.getExternalFilesDir(null)+"/"+NewFilename);

        return true;
    }

}
