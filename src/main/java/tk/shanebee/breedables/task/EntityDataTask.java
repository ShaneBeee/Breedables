package tk.shanebee.breedables.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;

class EntityDataTask extends BukkitRunnable {

    private EntityManager entityManager;
    private Config config;
    private int taskID;

    EntityDataTask(Breedables plugin, int seconds) {
        this.entityManager = plugin.getEntityManager();
        this.config = plugin.getBreedablesConfig();
        this.taskID = runTaskTimer(plugin, 20 * seconds, 20 * seconds).getTaskId();
    }

    @Override
    public void run() {
        for (World world : config.enabledWorlds) {
            for (Entity entity : world.getEntities()) {
                if (!entityManager.isBreedable(entity)) continue;
                if (entityManager.hasEntityData(entity)) continue;

                if (entityManager.hasContainerData(entity)) {
                    entityManager.createDataFromContainer(entity);
                } else {
                    entityManager.createEntityData(entity);
                }
            }
        }
    }

    void cancelTask() {
        Bukkit.getScheduler().cancelTask(this.taskID);
    }

}
