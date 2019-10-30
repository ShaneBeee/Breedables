package tk.shanebee.breedables.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;

class PregnancyTask extends BukkitRunnable {

    private EntityManager entityManager;
    private Config config;
    private int taskID;
    private int secondsDelay;

    PregnancyTask(Breedables plugin, int seconds) {
        this.entityManager = plugin.getEntityManager();
        this.config = plugin.getBreedablesConfig();
        this.secondsDelay = seconds;
        this.taskID = this.runTaskTimer(plugin, 20 * seconds, 20 * seconds).getTaskId();
    }

    @Override
    public void run() {
        for (World world : config.enabledWorlds) {
            for (Entity entity : world.getEntities()) {
                if (entityManager.isBreedable(entity)) {
                    EntityData data = entityManager.getEntityData(entity);
                    if (data == null) continue;
                    if (!data.isPregnant()) continue;

                    if (data.getPregnantTicks() > 0) {
                        data.removePregnantTicks(20 * secondsDelay);
                    } else {
                        entityManager.giveBirth(data);
                    }
                }
            }
        }
    }

    void cancelTask() {
        Bukkit.getScheduler().cancelTask(this.taskID);
    }

}
