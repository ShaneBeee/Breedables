package tk.shanebee.breedables;

import org.bukkit.plugin.java.JavaPlugin;
import tk.shanebee.breedables.listener.ListenerManager;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;
import tk.shanebee.breedables.util.Lang;

@SuppressWarnings({"WeakerAccess", "unused", "FieldCanBeLocal"})
public class Breedables extends JavaPlugin {

    public static Breedables instance;

    private Lang lang;
    private Config config;
    private EntityManager entityManager;
    private ListenerManager listenerManager;

    @Override
    public void onEnable() {
        instance = this;

        this.lang = new Lang(this);
        this.config = new Config(this);
        this.entityManager = new EntityManager(this);
        this.listenerManager = new ListenerManager(this);
    }

    @Override
    public void onDisable() {
    }

    public static Breedables getInstance() {
        return instance;
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public Lang getLang() {
        return this.lang;
    }

    public Config getBreedablesConfig() {
        return this.config;
    }

}
