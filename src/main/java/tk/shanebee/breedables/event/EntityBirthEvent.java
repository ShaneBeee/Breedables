package tk.shanebee.breedables.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.breedables.data.EntityData;

/**
 * Called when an entity gives birth to her babies
 */
@SuppressWarnings("unused")
public class EntityBirthEvent extends EntityEvent implements Cancellable {

    private static HandlerList handlerList = new HandlerList();
    private EntityData entityData;
    private boolean cancel;

    public EntityBirthEvent(@NotNull EntityData entityData) {
        super(entityData.getEntity());
        this.entityData = entityData;
        this.cancel = false;
    }

    /** Get the entity data from this event
     * @return Entity data from this event
     */
    public EntityData getEntityData() {
        return entityData;
    }

    /** Set whether or not the event is cancelled
     * <p><b>NOTE:</b> When cancelling this event, babies wont be born,
     * <br>but the mother will still lose her pregnancy state.
     * <br>If need be, you can set her pregnancy state again.</p>
     * @param cancel Cancel the event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
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
