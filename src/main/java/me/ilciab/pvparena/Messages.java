package me.ilciab.pvparena;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;

public class Messages {

    private static final Plugin plugin = Main.getInstance();

    @Getter
    private static String tooManyArgumentsError;
    @Getter
    private static String notEnoughArgumentsError;
    @Getter
    private static String invalidArgumentsError;
    @Getter
    private static String shopExistsError;
    @Getter
    private static String shopNotFoundError;
    @Getter
    private static String consoleExecutionError;
    @Getter
    private static String unknownCommandError;
    @Getter
    private static String noProductWithIdError;
    @Getter
    private static String reloadPluginMessage;
    @Getter
    private static String wrongDatabaseTypeError;
    @Getter
    private static String databaseInitializeError;
    @Getter
    private static String databaseLoadError;
    @Getter
    private static String noPermissionError;
    @Getter
    private static String shopCreatedMessage;
    @Getter
    private static String productAddedMessage;
    @Getter
    private static String productDeletedMessage;
    @Getter
    private static String shopDeletedMessage;
    @Getter
    private static String thereAreNoShopsMessage;


    public static void loadMessages() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(messagesFile);

        shopCreatedMessage = colorize(config.getString("shopCreatedMessage"));
        productAddedMessage = colorize(config.getString("productAddedMessage"));
        productDeletedMessage = colorize(config.getString("productDeletedMessage"));
        shopDeletedMessage = colorize(config.getString("shopDeletedMessage"));
        thereAreNoShopsMessage = colorize(config.getString("thereAreNoShopsMessage"));
        tooManyArgumentsError = colorize(config.getString("tooManyArgumentsError"));
        notEnoughArgumentsError = colorize(config.getString("notEnoughArgumentsError"));
        invalidArgumentsError = colorize(config.getString("invalidArgumentsError"));
        shopExistsError = colorize(config.getString("shopExistsError"));
        shopNotFoundError = colorize(config.getString("shopNotFoundError"));
        consoleExecutionError = colorize(config.getString("consoleExecutionError"));
        unknownCommandError = colorize(config.getString("unknownCommandError"));
        noProductWithIdError = colorize(config.getString("noProductWithIdError"));
        wrongDatabaseTypeError = colorize(config.getString("wrongDatabaseTypeError"));
        databaseInitializeError = colorize(config.getString("databaseInitializeError"));
        databaseLoadError = colorize(config.getString("databaseLoadError"));
        noPermissionError = colorize(config.getString("noPermissionError"));
    }

    static String colorize(String message) {
        return Objects.requireNonNull(message).replace('&', '§');
    }
}
