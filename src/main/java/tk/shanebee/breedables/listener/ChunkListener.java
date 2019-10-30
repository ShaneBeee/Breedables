package tk.shanebee.breedables.listener;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;

import java.util.ArrayList;
import java.util.List;

class ChunkListener implements Listener {

    private EntityManager entityManager;
    private Config config;

    ChunkListener(Breedables plugin) {
        this.entityManager = plugin.getEntityManager();
        this.config = plugin.getBreedablesConfig();
    }

    @EventHandler
    private void onChunkLoad(ChunkLoadEvent event) {
        if (config.enabledWorlds.contains(event.getWorld())) {
            List<Entity> breedables = getBreedableEntities(event);
            createData(breedables);
            checkBirth(breedables);
        }
    }

    private List<Entity> getBreedableEntities(ChunkLoadEvent event) {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : event.getChunk().getEntities()) {
            if (entityManager.isBreedable(entity))
                entities.add(entity);
        }
        return entities;
    }

    private void createData(List<Entity> entities) {
        for (Entity entity : entities) {
            if (!entityManager.hasEntityData(entity)) {
                entityManager.createEntityData(entity);
            }
        }
    }

    private void checkBirth(List<Entity> entities) {
        for (Entity entity : entities) {
            EntityData data = entityManager.getEntityData(entity);
            if (data.isPregnant() && data.getPregnantTicks() <= 0) {
                entityManager.giveBirth(data);
            }
        }
    }

}
