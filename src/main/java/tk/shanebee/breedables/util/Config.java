package tk.shanebee.breedables.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tk.shanebee.breedables.Breedables;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Config {

    private FileConfiguration config = null;
    private File customConfigFile = null;
    private Breedables plugin;

    public List<String> ENABLED_WORLDS;

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

    public int BABY_SEC_TIL_ADULT_COW;
    public int BABY_SEC_TIL_ADULT_CHICKEN;
    public int BABY_SEC_TIL_ADULT_SHEEP;
    public int BABY_SEC_TIL_ADULT_PIG;
    public int BABY_SEC_TIL_ADULT_RABBIT;
    public int BABY_SEC_TIL_ADULT_CAT;
    public int BABY_SEC_TIL_ADULT_WOLF;

    public String OFFSPRING_COW;
    public String OFFSPRING_CHICKEN;
    public String OFFSPRING_SHEEP;
    public String OFFSPRING_PIG;
    public String OFFSPRING_RABBIT;
    public String OFFSPRING_CAT;
    public String OFFSPRING_WOLF;

    public Config(Breedables plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    private void loadConfigFile() {
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!customConfigFile.exists()) {
            plugin.saveResource("config.yml", false);
            config = YamlConfiguration.loadConfiguration(customConfigFile);
            Utils.log("&7New config.yml &acreated");
        } else {
            config = YamlConfiguration.loadConfiguration(customConfigFile);
        }
        matchConfig(config, customConfigFile);
        loadConfig();
        Utils.log("&7config.yml &aloaded");
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
    
    private void loadConfig() {
        this.ENABLED_WORLDS = config.getStringList("settings.enabled-worlds");

        this.PREGNANCY_SEC_TIL_BIRTH_COW = config.getInt("settings.pregnancy.seconds-til-birth.cow");
        this.PREGNANCY_SEC_TIL_BIRTH_CHICKEN = config.getInt("settings.pregnancy.seconds-til-birth.chicken");
        this.PREGNANCY_SEC_TIL_BIRTH_SHEEP = config.getInt("settings.pregnancy.seconds-til-birth.sheep");
        this.PREGNANCY_SEC_TIL_BIRTH_CAT = config.getInt("settings.pregnancy.seconds-til-birth.cat");
        this.PREGNANCY_SEC_TIL_BIRTH_PIG = config.getInt("settings.pregnancy.seconds-til-birth.pig");
        this.PREGNANCY_SEC_TIL_BIRTH_WOLF = config.getInt("settings.pregnancy.seconds-til-birth.wolf");
        this.PREGNANCY_SEC_TIL_BIRTH_RABBIT = config.getInt("settings.pregnancy.seconds-til-birth.rabbit");

        this.PREGNANCY_SEC_TIL_BREED_COW = config.getInt("settings.pregnancy.seconds-til-breedable.cow");
        this.PREGNANCY_SEC_TIL_BREED_CHICKEN = config.getInt("settings.pregnancy.seconds-til-breedable.chicken");
        this.PREGNANCY_SEC_TIL_BREED_SHEEP = config.getInt("settings.pregnancy.seconds-til-breedable.sheep");
        this.PREGNANCY_SEC_TIL_BREED_CAT = config.getInt("settings.pregnancy.seconds-til-breedable.cat");
        this.PREGNANCY_SEC_TIL_BREED_PIG = config.getInt("settings.pregnancy.seconds-til-breedable.pig");
        this.PREGNANCY_SEC_TIL_BREED_WOLF = config.getInt("settings.pregnancy.seconds-til-breedable.wolf");
        this.PREGNANCY_SEC_TIL_BREED_RABBIT = config.getInt("settings.pregnancy.seconds-til-breedable.rabbit");

        this.BABY_SEC_TIL_ADULT_COW = config.getInt("settings.baby.seconds-til-adult.cow");
        this.BABY_SEC_TIL_ADULT_CHICKEN = config.getInt("settings.baby.seconds-til-adult.chicken");
        this.BABY_SEC_TIL_ADULT_SHEEP = config.getInt("settings.baby.seconds-til-adult.sheep");
        this.BABY_SEC_TIL_ADULT_PIG = config.getInt("settings.baby.seconds-til-adult.pig");
        this.BABY_SEC_TIL_ADULT_RABBIT = config.getInt("settings.baby.seconds-til-adult.rabbit");
        this.BABY_SEC_TIL_ADULT_CAT = config.getInt("settings.baby.seconds-til-adult.cat");
        this.BABY_SEC_TIL_ADULT_WOLF = config.getInt("settings.baby.seconds-til-adult.wolf");

        this.OFFSPRING_COW = config.getString("settings.pregnancy.offspring.cow");
        this.OFFSPRING_CHICKEN = config.getString("settings.pregnancy.offspring.chicken");
        this.OFFSPRING_SHEEP = config.getString("settings.pregnancy.offspring.sheep");
        this.OFFSPRING_PIG = config.getString("settings.pregnancy.offspring.pig");
        this.OFFSPRING_RABBIT = config.getString("settings.pregnancy.offspring.rabbit");
        this.OFFSPRING_CAT = config.getString("settings.pregnancy.offspring.cat");
        this.OFFSPRING_WOLF = config.getString("settings.pregnancy.offspring.wolf");
    }

}
