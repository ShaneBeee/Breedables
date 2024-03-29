package tk.shanebee.breedables;

import org.bukkit.plugin.java.JavaPlugin;
import tk.shanebee.breedables.listener.ListenerManager;
import tk.shanebee.breedables.manager.EffectManager;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.task.TaskManager;
import tk.shanebee.breedables.util.Config;
import tk.shanebee.breedables.util.Lang;
import tk.shanebee.breedables.util.Utils;

@SuppressWarnings({"WeakerAccess", "unused", "FieldCanBeLocal"})
public class Breedables extends JavaPlugin {

    public static Breedables instance;

    private Lang lang;
    private Config config;
    private EntityManager entityManager;
    private ListenerManager listenerManager;
    private TaskManager taskManager;
    private EffectManager effectManager;

    @Override
    public void onEnable() {
        instance = this;

        // Load configs
        loadConfigs();

        // Load managers
        this.effectManager = new EffectManager();
        this.entityManager = new EntityManager(this);
        this.listenerManager = new ListenerManager(this);
        this.taskManager = new TaskManager(this);

        // Start tasks
        this.taskManager.startTasks();

        Utils.log("&7Successfully &aenabled &7" + getDescription().getFullName());
    }

    @Override
    public void onDisable() {
        Utils.log("&7Shutting down");

        this.taskManager.cancelTasks();
        Utils.log("&7All tasks cancelled");

        Utils.log("&6Bye Bye Breedables! Sleep tight server!");
    }

    /**
     * Load/Reload configs
     */
    public void loadConfigs() {
        this.lang = new Lang(this);
        this.config = new Config(this);
    }

    /** Get an instance of this plugin
     * @return Instance of this plugin
     */
    public static Breedables getInstance() {
        return instance;
    }

    /** Get an instance of the EntityManager
     * @return Instance of EntityManager
     */
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /** Get an instance of the Lang config
     * @return Instance of Lang
     */
    public Lang getLang() {
        return this.lang;
    }

    /** Get an instance of the main config
     * @return Instance of Config
     */
    public Config getBreedablesConfig() {
        return this.config;
    }

    /** Get an instance of the TaskManager
     * @return Instance of TaskManager
     */
    public TaskManager getTaskManager() {
        return taskManager;
    }

    /** Get an instance of the EffectManager
     * @return Instance of EffectManager
     */
    public EffectManager getEffectManager() {
        return effectManager;
    }

}
