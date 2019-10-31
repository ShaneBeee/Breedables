package tk.shanebee.breedables.task;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.manager.EntityManager;

class EntityDataTask extends BukkitRunnable {

    private EntityManager entityManager;
    private int taskID;
    private int secondsDelay;

    EntityDataTask(Breedables plugin, int seconds) {
        this.entityManager = plugin.getEntityManager();
        this.secondsDelay = seconds;
        this.taskID = this.runTaskTimer(plugin, 20 * seconds, 20 * seconds).getTaskId();
    }

    @Override
    public void run() {
        for (EntityData data : entityManager.getEntityData().values()) {
            data.tick(secondsDelay);
        }
    }

    void cancelTask() {
        Bukkit.getScheduler().cancelTask(this.taskID);
    }

}
