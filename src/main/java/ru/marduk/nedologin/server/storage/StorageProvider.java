package ru.marduk.nedologin.server.storage;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.level.GameType;
import oshi.annotation.concurrent.ThreadSafe;

import java.io.IOException;
import java.util.Collection;

@ThreadSafe
@Environment(EnvType.SERVER)
public interface StorageProvider {
    boolean checkPassword(String username, String password);

    void unregister(String username);

    boolean registered(String username);

    void register(String username, String password);

    void save();

    GameType gameType(String username);

    void setGameType(String username, GameType gameType);

    void changePassword(String username, String newPassword);

    boolean dirty();

    /**
     * Should be immutable
     *
     * @return all registered username
     */
    Collection<String> getAllRegisteredUsername();
}
