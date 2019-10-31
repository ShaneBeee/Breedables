package tk.shanebee.breedables.task;

import tk.shanebee.breedables.Breedables;

/**
 * Manager for tasks
 * <p>Mainly used <b>internally</b></p>
 */
public class TaskManager {

    private Breedables plugin;
    private EntityDataTask pregnancyTask;

    public TaskManager(Breedables plugin) {
        this.plugin = plugin;
    }

    /**
     * Start plugin tasks
     * <p>This should not be used by external plugins</p>
     */
    public void startTasks() {
        this.pregnancyTask = new EntityDataTask(plugin, 1);
    }

    /**
     * Cancel all plugin tasks
     * <p>This should not be used by external plugins</p>
     */
    public void cancelTasks() {
        this.pregnancyTask.cancelTask();
    }

}
