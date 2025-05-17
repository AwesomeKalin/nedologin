package ru.marduk.nedologin;

import eu.midnightdust.lib.config.MidnightConfig;

import java.util.List;

public final class NLConfig extends MidnightConfig {
    @MidnightConfig.Server()
    @Entry(name = "Login Timeout(s)", min = 0, max = 1200, isSlider = true)
    public static int secs;

    @MidnightConfig.Server()
    @Entry(name = "Commands in whitelist can be executed before player login.")
    public static List<? extends String> whiteListCommands;

    @MidnightConfig.Server()
    @Entry(name = "Automatically register players (disable this if you choose to register players differently)")
    public static boolean autoRegister;

    @MidnightConfig.Server()
    @Entry(name = "Should the player be able to change the password?")
    public static boolean enableChangePassword;

    @MidnightConfig.Server()
    @Entry(name = "Which storage provider to use\nNedologin provides to available providers by default:\nnedologin:file -> file based storage\nnedologin:sqlite -> sqlite based storage\nnedologin:mariadb -> mariadb based storage (requires additional configuration)\nNote that you need to add JDBC sqlite & mariadb yourself if you want to use database based storage")
    public static String storageProvider;

    @MidnightConfig.Server()
    @Entry(name = "Default game type switched after player login\n0,1,2,3 represents survival,creative,adventure,spectator")
    public static int defaultGameType;

    @MidnightConfig.Server()
    @Entry(name = "Player login handler plugins to load\nnedologin:protect_coord is disabled by default, add to here to enable coord protect feature")
    public static List<? extends String> plugins;
}
