package org.example;

public class CheatMode {
    private static Boolean isCheatMode = false;

    public static boolean isCheatMode() {
        return isCheatMode;
    }
    
    public static void setCheatMode(Boolean isCheatMode) {
        CheatMode.isCheatMode = isCheatMode;
    }
}
