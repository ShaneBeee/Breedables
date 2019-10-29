package tk.shanebee.breedables.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.breedables.Breedables;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

    private FileConfiguration config = null;
    private File customConfigFile = null;
    private Breedables plugin;

    public int PREGNANCY_SEC_TIL_BIRTH_COW;
    public int PREGNANCY_SEC_TIL_BIRTH_CHICKEN;
    public int PREGNANCY_SEC_TIL_BIRTH_SHEEP;
    public int PREGNANCY_SEC_TIL_BIRTH_PIG;
    public int PREGNANCY_SEC_TIL_BIRTH_RABBIT;
    public int PREGNANCY_SEC_TIL_BIRTH_CAT;
    public int PREGNANCY_SEC_TIL_BIRTH_WOLF;

    public int PREGNANCY_SEC_TIL_BREED_COW;
    public int PREGNANCY_SEC_TIL_BREED_CHICKEN;
    public int PREGNANCY_SEC_TIL_BREED_SHEEP;
    public int PREGNANCY_SEC_TIL_BREED_PIG;
    public int PREGNANCY_SEC_TIL_BREED_RABBIT;
    public int PREGNANCY_SEC_TIL_BREED_CAT;
    public int PREGNANCY_SEC_TIL_BREED_WOLF;
    public int BABY_SEC_TIL_ADULT;

    public Config(Breedables plugin) {
        this.plugin = plugin;
    }

    private void loadLangFile() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!customConfigFile.exists()) {
            plugin.saveResource("config.yml", false);
            config = YamlConfiguration.loadConfiguration(customConfigFile);
            Utils.log("&7New config.yml created");
        } else {
            config = YamlConfiguration.loadConfiguration(customConfigFile);
        }
        matchConfig(config, customConfigFile);
        loadConfig();
        Utils.log("&7config.yml loaded");
    }

    // Used to update config
    @SuppressWarnings("ConstantConditions")
    private void matchConfig(FileConfiguration config, File file) {
        try {
            boolean hasUpdated = false;
            InputStream test = plugin.getResource(file.getName());
            assert test != null;
            InputStreamReader is = new InputStreamReader(test);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(is);
            for (String key : defConfig.getConfigurationSection("").getKeys(false)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(false)) {
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
    
    private void loadConfig() {
        this.PREGNANCY_SEC_TIL_BIRTH_COW = config.getInt("pregnancy.seconds-til-birth.cow");
        this.PREGNANCY_SEC_TIL_BIRTH_CHICKEN = config.getInt("pregnancy.seconds-til-birth.chicken");
        this.PREGNANCY_SEC_TIL_BIRTH_SHEEP = config.getInt("pregnancy.seconds-til-birth.sheep");
        this.PREGNANCY_SEC_TIL_BIRTH_CAT = config.getInt("pregnancy.seconds-til-birth.cat");
        this.PREGNANCY_SEC_TIL_BIRTH_PIG = config.getInt("pregnancy.seconds-til-birth.pig");
        this.PREGNANCY_SEC_TIL_BIRTH_WOLF = config.getInt("pregnancy.seconds-til-birth.wolf");
        this.PREGNANCY_SEC_TIL_BIRTH_RABBIT = config.getInt("pregnancy.seconds-til-birth.rabbit");

        this.PREGNANCY_SEC_TIL_BREED_COW = config.getInt("pregnancy.seconds-til-breedable.cow");
        this.PREGNANCY_SEC_TIL_BREED_CHICKEN = config.getInt("pregnancy.seconds-til-breedable.chicken");
        this.PREGNANCY_SEC_TIL_BREED_SHEEP = config.getInt("pregnancy.seconds-til-breedable.sheep");
        this.PREGNANCY_SEC_TIL_BREED_CAT = config.getInt("pregnancy.seconds-til-breedable.cat");
        this.PREGNANCY_SEC_TIL_BREED_PIG = config.getInt("pregnancy.seconds-til-breedable.pig");
        this.PREGNANCY_SEC_TIL_BREED_WOLF = config.getInt("pregnancy.seconds-til-breedable.wolf");
        this.PREGNANCY_SEC_TIL_BREED_RABBIT = config.getInt("pregnancy.seconds-til-breedable.rabbit");

        this.BABY_SEC_TIL_ADULT = config.getInt("baby.seconds-til-adult");
    }

}
