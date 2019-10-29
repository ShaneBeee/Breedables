package tk.shanebee.breedables.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.breedables.Breedables;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lang {

    private FileConfiguration lang = null;
    private File customLangFile = null;
    private Breedables plugin;

    public String CANT_BREED_SAME_GENDER;
    public String CANT_BREED_ENTITY_PREGNANT;

    public Lang(Breedables plugin) {
        this.plugin = plugin;
        loadLangFile();
    }

    private void loadLangFile() {
        if (customLangFile == null) {
            customLangFile = new File(plugin.getDataFolder(), "language.yml");
        }
        if (!customLangFile.exists()) {
            plugin.saveResource("language.yml", false);
            lang = YamlConfiguration.loadConfiguration(customLangFile);
            Utils.log("&7New language.yml &acreated");
        } else {
            lang = YamlConfiguration.loadConfiguration(customLangFile);
        }
        matchConfig(lang, customLangFile);
        loadLang();
        Utils.log("&7language.yml &aloaded");
    }

    // Used to update config
    private void matchConfig(FileConfiguration config, File file) {
        try {
            boolean hasUpdated = false;
            InputStream test = plugin.getResource(file.getName());
            assert test != null;
            InputStreamReader is = new InputStreamReader(test);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(is);
            for (String key : defConfig.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(true)) {
                if (!defConfig.contains(key)) {
                    config.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLang() {
        this.CANT_BREED_SAME_GENDER = lang.getString("cant-breed-same-gender");
        this.CANT_BREED_ENTITY_PREGNANT = lang.getString("cant-breed-entity-pregnant");
    }
}
