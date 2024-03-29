package tk.shanebee.breedables.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.breedables.data.EntityData;

/**
 * Called when a female entity successfully gets pregnant
 */
@SuppressWarnings("unused")
public class EntityGetsPregnantEvent extends EntityEvent implements Cancellable {

    private static HandlerList handlerList = new HandlerList();
    private EntityData entityData;
    private int pregnancyTicks;
    private boolean cancel;

    public EntityGetsPregnantEvent(@NotNull EntityData entityData, @NotNull int pregnancyTicks) {
        super(entityData.getEntity());
        this.entityData = entityData;
        this.pregnancyTicks = pregnancyTicks;
        this.cancel = false;
    }

    /** Get the entity data from this event
     * @return EntityData from this event
     */
    public EntityData getEntityData() {
        return entityData;
    }

    /** Get the pregnancy ticks from this even
     * @return Pregnancy ticks
     */
    public int getPregnancyTicks() {
        return pregnancyTicks;
    }

    /** Set the pregnancy ticks for this event
     * @param pregnancyTicks Ticks the mother will be pregnant for
     */
    public void setPregnancyTicks(int pregnancyTicks) {
        this.pregnancyTicks = pregnancyTicks;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
