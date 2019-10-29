package tk.shanebee.breedables.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;
import tk.shanebee.breedables.data.EntityData;

@SuppressWarnings("unused")
public class EntityBreedEvent extends EntityEvent implements Cancellable {

    private static HandlerList handlerList = new HandlerList();
    private EntityData mom, dad;
    private boolean cancel;

    public EntityBreedEvent(@NotNull EntityData mom, @NotNull EntityData dad) {
        super(mom.getEntity());
        this.mom = mom;
        this.dad = dad;
        this.cancel = false;
    }

    public EntityData getDad() {
        return dad;
    }

    public EntityData getMom() {
        return mom;
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
