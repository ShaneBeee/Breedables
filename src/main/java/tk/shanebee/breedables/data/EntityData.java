package tk.shanebee.breedables.data;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.type.Gender;
import tk.shanebee.breedables.util.Utils;

import java.util.UUID;

@SuppressWarnings("unused")
public class EntityData {

    private UUID uuid;
    private EntityType entityType;
    private Gender gender;
    private boolean pregnant;
    private int pregnantTicks;
    private int recoveryTicks;

    public EntityData(Entity entity, Gender gender) {
        this.uuid = entity.getUniqueId();
        this.entityType = entity.getType();
        this.gender = gender;
        this.pregnant = false;
        this.pregnantTicks = 0;
        this.recoveryTicks = 0;
        setDataContainer(entity);
        ((LivingEntity) entity).setCustomName(Utils.getColString(gender.getSymbol()));
    }

    private EntityData(UUID uuid, EntityType entityType, Gender gender, boolean pregnant, int pregnantTicks, int recoveryTicks) {
        this.uuid = uuid;
        this.entityType = entityType;
        this.gender = gender;
        this.pregnant = pregnant;
        this.pregnantTicks = pregnantTicks;
        this.recoveryTicks = recoveryTicks;
    }

    /** Get the entity from this data
     * @return Entity from this data
     */
    public Entity getEntity() {
        return Bukkit.getEntity(this.uuid);
    }

    /** Get the UUID of the entity from this data
     * @return UUID of entity from this data
     */
    public UUID getUuid() {
        return uuid;
    }

    /** Get the type of entity from this data
     * @return EntityType from this data
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /** Get the gender of the entity of this data
     * @return Gender of this data
     */
    public Gender getGender() {
        return gender;
    }

    /** Set the gender of the entity for this data
     * @param gender Gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
        setDataContainer();
    }

    /** Check if the entity of this data is pregnant
     * @return True if entity is pregnant
     */
    public boolean isPregnant() {
        return pregnant;
    }

    /** Set the pregnancy state of the entity from this data
     * @param pregnant Pregnancy state to set
     */
    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
        setDataContainer();
    }

    /** Get the amount of remaining ticks this entity will be pregnant for
     * @return Ticks this entity will be pregnant for
     */
    public int getPregnantTicks() {
        return pregnantTicks;
    }

    /** Set the amount of ticks this entity will be pregnant for
     * @param pregnantTicks Ticks this entity will be pregnant for
     */
    public void setPregnantTicks(int pregnantTicks) {
        this.pregnantTicks = pregnantTicks;
        setDataContainer();
    }

    /** Remove from the amount of ticks this entity will be pregnant for
     * @param remove Amount of ticks to remove
     */
    public void removePregnantTicks(int remove) {
        if (gender == Gender.MALE) return;
        this.pregnantTicks -= remove;
        setDataContainer();
    }

    /** Get the amount of ticks before the entity can breed again
     * @return Amount of ticks before entity can breed again
     */
    public int getRecoveryTicks() {
        return recoveryTicks;
    }

    /** Set the amount of ticks before the entity can breed again
     * @param recoveryTicks The amount of ticks before entity can breed again
     */
    public void setRecoveryTicks(int recoveryTicks) {
        this.recoveryTicks = recoveryTicks;
        setDataContainer();
    }

    /** Remove from the amount of ticks before the entity can breed again
     * @param remove Amount to remove
     */
    public void removeRecoveryTicks(int remove) {
        if (gender == Gender.MALE) return;
        this.recoveryTicks -= remove;
        setDataContainer();
    }

    @Override
    public String toString() {
        return "EntityData:{UUID:" + uuid.toString() + ",EntityType:" + entityType + ",Gender:" + gender +
                ",Pregnant:" + pregnant + ",PregnancyTicks:" + pregnantTicks + ",RecoveryTicks:" + recoveryTicks + "}";
    }

    private String toDataString() {
        return "EntityData:{Gender:" + gender + ",Pregnant:" + pregnant + ",PregnancyTicks:" + pregnantTicks + ",RecoveryTicks:" + recoveryTicks + "}";
    }

    /** Get the EntityData from the entity's persistent container
     * @param entity Entity to grab data from
     * @return EntityData from this entity
     */
    public static EntityData dataFromEntity(Entity entity) {
        NamespacedKey key = new NamespacedKey(Breedables.getInstance(), "entity-data");
        if (!entity.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return null;
        String dataString = entity.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (dataString == null) return null;
        String[] dataSplit = dataString.replace("EntityData:{", "").replace("}", "").split(",");
        Gender gender = Gender.valueOf(dataSplit[0].split(":")[1]);
        boolean pregnant = Boolean.parseBoolean(dataSplit[1].split(":")[1]);
        int pregnantTicks = Integer.parseInt(dataSplit[2].split(":")[1]);
        int recoveryTicks = Integer.parseInt(dataSplit[3].split(":")[1]);

        return new EntityData(entity.getUniqueId(), entity.getType(), gender, pregnant, pregnantTicks, recoveryTicks);
    }

    /**
     *  Set the data container for this EntityData
     *  @return True if the data was set
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean setDataContainer() {
        Entity entity = Bukkit.getEntity(this.uuid);
        if (entity == null) return false;
        NamespacedKey key = new NamespacedKey(Breedables.getInstance(), "entity-data");
        entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, this.toDataString());
        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean setDataContainer(Entity entity) {
        if (!entity.getUniqueId().equals(this.uuid)) return false;
        NamespacedKey key = new NamespacedKey(Breedables.getInstance(), "entity-data");
        entity.getPersistentDataContainer().set(key, PersistentDataType.STRING, this.toDataString());
        return true;
    }

}
