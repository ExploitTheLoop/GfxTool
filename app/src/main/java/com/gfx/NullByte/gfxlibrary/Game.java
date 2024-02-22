package com.gfx.NullByte.gfxlibrary;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private byte[] activeSavContent;

    public Game(String filePath) throws IOException {
        InputStream inputStream = null;
        Log.i("AnimeTone",filePath);
        try {
            inputStream = new BufferedInputStream(new FileInputStream(filePath));
            activeSavContent = readAllBytes(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public void saveActiveSavFile(String filePath) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            outputStream.write(activeSavContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
        }
    }

    private byte[] readAllBytes(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192]; // Read in chunks of 8KB
        int bytesRead;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getGraphicsStyle() {
        byte[] battleStyleHex = null;
        battleStyleHex = readHex("BattleRenderStyle");
        String GraphiStyle = null;

        if (battleStyleHex != null && battleStyleHex.length > 0) {
            switch (battleStyleHex[0]) {
                case 1:
                    GraphiStyle = "Classic";
                    break;
                case 2:
                    GraphiStyle = "Colorful";
                    break;
                case 3:
                    GraphiStyle = "Realistic";
                    break;
                case 4:
                    GraphiStyle = "Soft";
                    break;
                case 6:
                    GraphiStyle = "Movie";
                    break;
                default:
                    GraphiStyle = "Not Found";
                    // Handle unknown FPS value
                    break;
            }
        } else {
            GraphiStyle = "Not Found";
        }

        return GraphiStyle;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getGraphicsSettings() {
        byte[] battleStyleHex = null;
        battleStyleHex = readHex("BattleRenderQuality");
        String GraphiStyle = null;

        if (battleStyleHex != null && battleStyleHex.length > 0) {
            switch (battleStyleHex[0]) {
                case 1:
                    GraphiStyle = "Smooth";
                    break;
                case 2:
                    GraphiStyle = "Balanced";
                    break;
                case 3:
                    GraphiStyle = "HD";
                    break;
                case 4:
                    GraphiStyle = "HDR";
                    break;
                case 5:
                    GraphiStyle = "Ultra HD";
                    break;
                default:
                    GraphiStyle = "Not Found";
                    // Handle unknown FPS value
                    break;
            }
        } else {
            GraphiStyle = "Not Found";
        }

        return GraphiStyle;

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setGraphicsStyle(String style) {

        // Define FPS mappings
        Map<String, byte[]> battleStyleMapping = new HashMap<>();
        battleStyleMapping.put("Classic", new byte[]{0x01});
        battleStyleMapping.put("Colorful", new byte[]{0x02});
        battleStyleMapping.put("Realistic", new byte[]{0x03});
        battleStyleMapping.put("Soft", new byte[]{0x04});
        battleStyleMapping.put("Movie", new byte[]{0x06});

        // Get the byte value corresponding to the provided FPS value
        byte[] battleStyleValue = battleStyleMapping.get(style);

        // Define FPS properties
        String[] battleStyleValueProperties = {"BattleRenderStyle","LobbyRenderStyle"};

        // Update FPS value in Active.sav content if FPS value is valid
        if (battleStyleValue != null) {
            for (String prop : battleStyleValueProperties) {
                // Convert property string to bytes using UTF-8 encoding
                byte[] header = (prop + "\u0000\u000C\u0000\u0000\u0000IntProperty\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000").getBytes(StandardCharsets.UTF_8);

                // Find the index of the header in the Active.sav content
                int index = findHeaderIndex(activeSavContent, header);
                if (index != -1) {
                    // Update the FPS value at the appropriate position in the content
                    activeSavContent[index + header.length] = battleStyleValue[0];
                }
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setGraphicsSettings(String style) {

        // Define FPS mappings
        Map<String, byte[]> battleStyleMapping = new HashMap<>();
        battleStyleMapping.put("Smooth", new byte[]{0x01});
        battleStyleMapping.put("Balanced", new byte[]{0x02});
        battleStyleMapping.put("HD", new byte[]{0x03});
        battleStyleMapping.put("HDR", new byte[]{0x04});
        battleStyleMapping.put("Ultra HD", new byte[]{0x05});

        // Get the byte value corresponding to the provided FPS value
        byte[] battleStyleValue = battleStyleMapping.get(style);

        // Define FPS properties
        String[] battleStyleValueProperties = {"ArtQuality", "LobbyRenderQuality", "BattleRenderQuality"};

        // Update FPS value in Active.sav content if FPS value is valid
        if (battleStyleValue != null) {
            for (String prop : battleStyleValueProperties) {
                // Convert property string to bytes using UTF-8 encoding
                byte[] header = (prop + "\u0000\u000C\u0000\u0000\u0000IntProperty\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000").getBytes(StandardCharsets.UTF_8);

                // Find the index of the header in the Active.sav content
                int index = findHeaderIndex(activeSavContent, header);
                if (index != -1) {
                    // Update the FPS value at the appropriate position in the content
                    activeSavContent[index + header.length] = battleStyleValue[0];
                }
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setFps(String val) {
        // Define FPS mappings
        Map<String, byte[]> fpsMapping = new HashMap<>();
        fpsMapping.put("Low", new byte[]{0x02});
        fpsMapping.put("Medium", new byte[]{0x03});
        fpsMapping.put("High", new byte[]{0x04});
        fpsMapping.put("Ultra", new byte[]{0x05});
        fpsMapping.put("Extreme", new byte[]{0x06});
        fpsMapping.put("90 fps", new byte[]{0x07});

        // Get the byte value corresponding to the provided FPS value
        byte[] fpsValue = fpsMapping.get(val);

        // Define FPS properties
        String[] fpsProperties = {"FPSLevel", "BattleFPS", "LobbyFPS"};

        // Update FPS value in Active.sav content if FPS value is valid
        if (fpsValue != null) {
            for (String prop : fpsProperties) {
                // Convert property string to bytes using UTF-8 encoding
                byte[] header = (prop + "\u0000\u000C\u0000\u0000\u0000IntProperty\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000").getBytes(StandardCharsets.UTF_8);

                // Find the index of the header in the Active.sav content
                int index = findHeaderIndex(activeSavContent, header);
                if (index != -1) {
                    // Update the FPS value at the appropriate position in the content
                    activeSavContent[index + header.length] = fpsValue[0];
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void SetTppView(int TpViewValue) {

        byte[] TpViewValuehex =convertToHexBytes(TpViewValue);

        Log.i("tppsetval : ",hexToIntegerString(TpViewValuehex));

        // Define FPS properties
        String[] fpsProperties = {"TpViewValue"};

        // Update FPS value in Active.sav content if FPS value is valid
        if (TpViewValuehex != null) {
            for (String prop : fpsProperties) {
                // Convert property string to bytes using UTF-8 encoding
                byte[] header = (prop + "\u0000\u000C\u0000\u0000\u0000IntProperty\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000").getBytes(StandardCharsets.UTF_8);

                // Find the index of the header in the Active.sav content
                int index = findHeaderIndex(activeSavContent, header);
                if (index != -1) {
                    // Update the FPS value at the appropriate position in the content
                    activeSavContent[index + header.length] = TpViewValuehex[0];
                }
            }
        }else{
            Log.i("error","tppval is null");
        }
    }

    public String getTPPval() {

        byte[] TppHex = readHex("TpViewValue");
        if (TppHex != null && TppHex.length > 0) {

            Log.i("tppstringval",hexToIntegerString(TppHex));
            return hexToIntegerString(TppHex);

        }else{
            return"Not Found";
        }

    }

    public int getTPPintval() {

        byte[] TppHex = readHex("TpViewValue");
        if (TppHex != null && TppHex.length > 0) {

            Log.i("tppintval", String.valueOf(convertHexToInt(convertByteArrayToString(TppHex))));
            return convertHexToInt(convertByteArrayToString(TppHex));

        }else{
            return 0;
        }

    }




    public String getFPS() {

        byte[] fpsHex = null;
        fpsHex = readHex("BattleFPS");
        String fpsValue = null;

        if (fpsHex != null && fpsHex.length > 0) {
            switch (fpsHex[0]) {
                case 2:
                    fpsValue = "Low";
                    break;
                case 3:
                    fpsValue = "Medium";
                    break;
                case 4:
                    fpsValue = "High";
                    break;
                case 5:
                    fpsValue = "Ultra";
                    break;
                case 6:
                    fpsValue = "Extreme";
                    break;
                case 7:
                    fpsValue = "90 fps";
                    break;
                default:
                    fpsValue = "Not Found";
                    break;
            }
        } else {
            fpsValue = "Not Found";
        }

        return fpsValue;
    }


    private byte[] readHex(String name) {

        String headerStr = name + "\u0000\u000C\u0000\u0000\u0000IntProperty\u0000\u0004\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";
        byte[] header = headerStr.getBytes();
        byte[] content = new byte[0];

        for (int i = 0; i < activeSavContent.length - header.length; i++) {
            boolean isMatch = true;
            for (int j = 0; j < header.length; j++) {
                if (activeSavContent[i + j] != header[j]) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                Log.i("Match","found");
                content = new byte[]{activeSavContent[i + header.length]};
                break;
            }
        }

        return content;
    }

    private int findHeaderIndex(byte[] content, byte[] header) {
        for (int i = 0; i <= content.length - header.length; i++) {
            boolean found = true;
            for (int j = 0; j < header.length; j++) {
                if (content[i + j] != header[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                Log.i("Match index","found");

                return i;
            }
        }
        return -1;
    }

    public static byte[] convertToHexBytes(int intValue) {
        // Ensure the value doesn't exceed one byte (8 bits)
        if (intValue < 0 || intValue > 255) {
            throw new IllegalArgumentException("Value must be between 0 and 255.");
        }

        // Allocate space for 1 byte (8 bits) to store the integer value
        byte[] bytes = new byte[1];

        // Convert the integer value to a byte
        bytes[0] = (byte) intValue;

        return bytes;
    }

    public static String hexToIntegerString(byte[] tppHex) {
        // Convert the byte array to a hexadecimal string
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : tppHex) {
            hexStringBuilder.append(String.format("%02X", b));
        }
        String hexString = hexStringBuilder.toString();

        // Parse the hexadecimal string to an integer
        int intValue = Integer.parseInt(hexString, 16);

        // Convert the integer value to string
        String intValueString = String.valueOf(intValue);

        return intValueString;
    }

    public static int convertHexToInt(String hexString) {
        // Parse the hexadecimal string to an integer
        int intValue = Integer.parseInt(hexString, 16);
        return intValue;
    }

    public static String convertByteArrayToString(byte[] TppHex) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : TppHex) {
            // Convert each byte to its hexadecimal representation
            stringBuilder.append(String.format("%02X", b));
        }
        // Return the hexadecimal string
        return stringBuilder.toString();
    }

}


