package tk.shanebee.breedables.manager;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.type.Gender;
import tk.shanebee.breedables.util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings({"unused", "WeakerAccess"})
public class EntityManager {

    private Map<UUID, EntityData> entityDataMap;

    public EntityManager(Breedables plugin) {
        this.entityDataMap = new HashMap<>();
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

    /** Create a new EntityData for an entity
     * <p>This will create a new data, store it, as well as set the data in the entity's persistent container</p>
     * @param entity Entity to create data for
     */
    public void createEntityData(@NotNull Entity entity) {
        if (!isBreedable(entity)) return;

        EntityData data = new EntityData(entity, Utils.getRandomGender());
        this.entityDataMap.put(entity.getUniqueId(), data);
    }

    /** Update an entity's EntityData stored in their persistent container
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
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isBreedable(Entity entity) {
        switch (entity.getType()) {
            case CHICKEN:
            case COW:
            case PIG:
            case SHEEP:
            case RABBIT:
            case WOLF:
            case CAT:
                return true;
            default:
                return false;
        }
    }

    /** Check if 2 entities are opposing genders
     * @param entity1 First entity to check
     * @param entity2 Second entity to check
     * @return True if entities are opposing genders
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean opposingGenders(@NotNull Entity entity1, @NotNull Entity entity2) {
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
        if (!opposingGenders(entity1, entity2)) return null;
        EntityData entityData1 = getEntityData(entity1);
        EntityData entityData2 = getEntityData(entity2);
        return entityData1.getGender() == Gender.FEMALE ? entityData1 : entityData2;
    }

}