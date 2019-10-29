package tk.shanebee.breedables.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.manager.EntityManager;

class PregnancyTask extends BukkitRunnable {

    private EntityManager entityManager;
    private int taskID;

    PregnancyTask(Breedables plugin) {
        this.entityManager = plugin.getEntityManager();
        this.taskID = this.runTaskTimer(plugin, 20, 20).getTaskId();
    }

    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entityManager.isBreedable(entity)) {
                    EntityData data = entityManager.getEntityData(entity);
                    if (data == null) {
                        entityManager.createEntityData(entity);
                        continue;
                    }
                    if (!data.isPregnant()) continue;
                    if (data.getPregnantTicks() > 0) {
                        data.removePregnantTicks(20);
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
