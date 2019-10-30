package tk.shanebee.breedables.util;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.BreedData;
import tk.shanebee.breedables.data.EntityData;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Config {

    private Breedables plugin;
    private FileConfiguration config = null;
    private File customConfigFile = null;
    private Map<EntityType, BreedData> breedMap = new HashMap<>();

    //public List<String> ENABLED_WORLDS;
    public Set<World> enabledWorlds;


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
            if (hasUpdated)
                config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadConfig() {
        //this.ENABLED_WORLDS = config.getStringList("enabled-worlds");
        setEnabledWorlds(config.getStringList("enabled-worlds"));

        ConfigurationSection sections = config.getConfigurationSection("entity-data");
        assert sections != null;
        for (String key : sections.getKeys(false)) {
            if (isValidType(key)) {
                EntityType type = EntityType.valueOf(key.toUpperCase());
                int preg = config.getInt("entity-data." + key + ".seconds-til-birth") * 20;
                int breed = config.getInt("entity-data." + key + ".seconds-til-breed") * 20;
                int adult = config.getInt("entity-data." + key + ".seconds-til-adult") * 20;
                String off = config.getString("entity-data." + key + ".offspring");
                String tool = config.getString("entity-data." + key + ".breeding-tool");
                BreedData data = new BreedData(type, preg, breed, adult, off, tool);
                breedMap.put(type, data);
            } else {
                Utils.log("&6Invalid entity type: &c" + key);
            }
        }
    }

    public BreedData getBreedData(EntityType type) {
        if (breedMap.containsKey(type)) {
            return breedMap.get(type);
        }
        return null;
    }

    private void setEnabledWorlds(List<String> enabledWorlds) {
        for (String world : enabledWorlds) {
            World w = Bukkit.getWorld(world);
            if (w != null) {
                this.enabledWorlds.add(w);
            }
        }
    }

    public BreedData getBreedData(Entity entity) {
        return getBreedData(entity.getType());
    }

    public BreedData getBreedData(EntityData data) {
        return getBreedData(data.getEntityType());
    }

    private boolean isValidType(String type) {
        try {
            EntityType.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

}
