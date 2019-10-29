package tk.shanebee.breedables.task;

import tk.shanebee.breedables.Breedables;

public class TaskManager {

    private Breedables plugin;
    private PregnancyTask pregnancyTask;
    private EntityDataTask entityDataTask;

    public TaskManager(Breedables plugin) {
        this.plugin = plugin;
    }

    public void startTasks() {
        this.pregnancyTask = new PregnancyTask(plugin, 1);
        this.entityDataTask = new EntityDataTask(plugin, 5);
    }

    public void cancelTasks() {
        this.pregnancyTask.cancelTask();
        this.entityDataTask.cancelTask();
    }
}
