package ru.marduk.nedologin.server.storage;

import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.NedologinServer;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class StorageProviderSQLite extends StorageProviderSQL {
    public StorageProviderSQLite() throws SQLException {
        // Default path at $WORLD_DIR/sl_entries.dat
        super(DriverManager.getConnection("jdbc:sqlite:" +
                NedologinServer.SERVER.getWorldPath(NLConstants.NL_ENTRY)));
    }
}