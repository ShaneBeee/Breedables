package tk.shanebee.breedables.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.breedables.data.EntityData;

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

    public EntityData getEntityData() {
        return entityData;
    }

    public int getPregnancyTicks() {
        return pregnancyTicks;
    }

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
