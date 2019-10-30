package tk.shanebee.breedables.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.event.EntityBirthEvent;
import tk.shanebee.breedables.type.Gender;
import tk.shanebee.breedables.util.Config;
import tk.shanebee.breedables.util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Data manager for an <b>{@link Entity}</b> and their <b>{@link EntityData}</b>
 */
@SuppressWarnings({"unused"})
public class EntityManager {

    private Map<UUID, EntityData> entityDataMap;
    private Config config;

    public EntityManager(Breedables plugin) {
        this.entityDataMap = new HashMap<>();
        this.config = plugin.getBreedablesConfig();
    }

    /** Add EntityData to the entity manager
     * @param entityData EntityData to add
     */
    public void addEntityData(@NotNull EntityData entityData) {
        this.entityDataMap.put(entityData.getUuid(), entityData);
    }

    /** Check if an entity currently has EntityData stored
     * @param entity Entity to check
     * @return True if this entity has stored EntityData
     */
    public boolean hasEntityData(Entity entity) {
        return entityDataMap.containsKey(entity.getUniqueId());
    }

    public boolean hasContainerData(Entity entity) {
        NamespacedKey key = new NamespacedKey(Breedables.getInstance(), "entity-data");
        return entity.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    /** Get the EntityData from an entity
     * @param entity Entity to grab data from
     * @return EntityData from entity
     */
    public EntityData getEntityData(@NotNull Entity entity) {
        if (this.entityDataMap.containsKey(entity.getUniqueId())) {
            return this.entityDataMap.get(entity.getUniqueId());
        } else {
            if (!isBreedable(entity)) return null;
            EntityData data = new EntityData(entity, Utils.getRandomGender());
            this.entityDataMap.put(entity.getUniqueId(), data);
            return data;
        }
    }

    /** Get the EntityData from an entity's UUID
     * @param uuid UUID of entity to grab data from
     * @return EntityData of entity
     */
    public EntityData getEntityData(@NotNull UUID uuid) {
        if (this.entityDataMap.containsKey(uuid)) {
            return this.entityDataMap.get(uuid);
        }
        return null;
    }

    /** Create and store a new EntityData for an entity
     * <p>This will create a new data, store it, as well as set the data in the entity's persistent container</p>
     * @param entity Entity to create data for
     */
    public void createEntityData(@NotNull Entity entity) {
        if (!isBreedable(entity)) return;

        EntityData data = new EntityData(entity, Utils.getRandomGender());
        this.entityDataMap.put(entity.getUniqueId(), data);
    }

    /** Remove an entity data from this entity manager
     * @param entity Entity to remove data for
     */
    public void removeEntityData(@NotNull Entity entity) {
        this.entityDataMap.remove(entity.getUniqueId());
    }

    /** Create and store a new <b>{@link EntityData}</b> from an entity's <b>{@link org.bukkit.persistence.PersistentDataContainer}</b>
     * @param entity Entity to grab data from
     */
    public void createDataFromContainer(@NotNull Entity entity) {
        if (!isBreedable(entity)) return;
        NamespacedKey key = new NamespacedKey(Breedables.getInstance(), "entity-data");
        if (entity.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
            EntityData data = EntityData.dataFromEntityContainer(entity);
            this.entityDataMap.put(entity.getUniqueId(), data);
        }
    }

    /** Update an entity's EntityData stored in their <b>{@link org.bukkit.persistence.PersistentDataContainer}</b>
     * <p>This is to ensure all entity data is persistent after server restarts</p>
     * @param entity Entity to update
     */
    public void updateEntityData(@NotNull Entity entity) {
        EntityData data = getEntityData(entity);
        data.setDataContainer();
    }

    /** Check if the entity can be bred
     * @param entity Entity to check
     * @return True if this entity is able to breed
     */
    public boolean isBreedable(Entity entity) {
        return config.getBreedData(entity) != null;
    }

    /** Check if 2 entities are opposing genders
     * @param entity1 First entity to check
     * @param entity2 Second entity to check
     * @return True if entities are opposing genders
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean areOpposingGenders(@NotNull Entity entity1, @NotNull Entity entity2) {
        if (!isBreedable(entity1) || !isBreedable(entity2)) return false;

        EntityData data1 = getEntityData(entity1);
        EntityData data2 = getEntityData(entity2);
        return (data1.getGender() == Gender.MALE && data2.getGender() == Gender.FEMALE)
                || (data1.getGender() == Gender.FEMALE && data2.getGender() == Gender.MALE);
    }

    /** Get the EntityData for the female between 2 entities
     * @param entity1 First entity
     * @param entity2 Second entity
     * @return EntityData for female of the two entities
     */
    public EntityData getFemaleData(Entity entity1, Entity entity2) {
        if (!areOpposingGenders(entity1, entity2)) return null;
        EntityData entityData1 = getEntityData(entity1);
        EntityData entityData2 = getEntityData(entity2);
        return entityData1.getGender() == Gender.FEMALE ? entityData1 : entityData2;
    }

    /** Get the EntityData for the male between 2 entities
     * @param entity1 First entity
     * @param entity2 Second entity
     * @return EntityData for male of the two entities
     */
    public EntityData getMaleData(Entity entity1, Entity entity2) {
        if (!areOpposingGenders(entity1, entity2)) return null;
        EntityData entityData1 = getEntityData(entity1);
        EntityData entityData2 = getEntityData(entity2);
        return entityData1.getGender() == Gender.MALE ? entityData1 : entityData2;
    }

    /** Make this entity give birth
     * <p>This method will also call the <b>{@link EntityBirthEvent}</b>
     * <br>If the event is cancelled, the offspring will not be born but the mother will still lose her pregnancy state</p>
     * @param entityData EntityData of entity to give birth
     */
    public void giveBirth(EntityData entityData) {
        entityData.setPregnant(false);
        entityData.setPregnantTicks(0);

        // Call entity birth event
        EntityBirthEvent event = new EntityBirthEvent(entityData);
        Bukkit.getPluginManager().callEvent(event);

        // If the event isn't cancelled, proceed to make mother give birth
        if (!event.isCancelled()) {
            int b = getBabyAmount(entityData.getEntityType());
            Location loc = entityData.getEntity().getLocation();
            World w = loc.getWorld();
            assert w != null;
            EntityType type = entityData.getEntityType();
            for (int i = 0; i < b; i++) {
                Ageable baby = ((Ageable) w.spawnEntity(loc, type));
                baby.setAge(-(config.getBreedData(type).getTicksTilAdult()));
            }
        }
    }

    private int getBabyAmount(EntityType type) {
        int min = config.getBreedData(type).getOffspringMin();
        int max = config.getBreedData(type).getOffspringMax();
        Random random = new Random();
        return (random.nextInt(max - min + 1) + min);
    }

}
