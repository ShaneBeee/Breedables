package tk.shanebee.breedables.listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.manager.EntityManager;

class EntityListener implements Listener {

    private EntityManager entityManager;

    EntityListener(Breedables plugin) {
        this.entityManager = plugin.getEntityManager();
    }

    @EventHandler
    private void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (!entityManager.isBreedable(entity)) return;
        entityManager.createEntityData(entity);
    }

}
