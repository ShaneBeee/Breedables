package tk.shanebee.breedables.listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;

class EntityListener implements Listener {

    private EntityManager entityManager;
    private Config config;

    EntityListener(Breedables plugin) {
        this.entityManager = plugin.getEntityManager();
        this.config = plugin.getBreedablesConfig();
    }

    @EventHandler
    private void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (config.enabledWorlds.contains(entity.getWorld())) {
            if (!entityManager.isBreedable(entity)) return;
            entityManager.createEntityData(entity);
        }
    }

    @EventHandler
    private void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entityManager.hasEntityData(entity)) {
            entityManager.removeEntityData(entity);
        }
    }

}
