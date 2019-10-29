package tk.shanebee.breedables.task;

import tk.shanebee.breedables.Breedables;

public class TaskManager {

    private Breedables plugin;
    private PregnancyTask pregnancyTask;

    public TaskManager(Breedables plugin) {
        this.plugin = plugin;
    }

    public void startTasks() {
        this.pregnancyTask = new PregnancyTask(plugin);
    }

    public void cancelTasks() {
        this.pregnancyTask.cancelTask();
    }
}
