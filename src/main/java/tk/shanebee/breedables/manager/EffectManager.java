package tk.shanebee.breedables.manager;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import tk.shanebee.breedables.data.EntityData;

@SuppressWarnings("WeakerAccess")
public class EffectManager {

    public EffectManager() {
    }

    public void playLoveHearts(EntityData entityData) {
        Location location = entityData.getEntity().getLocation().clone();
        location.add(0, entityData.getEntity().getHeight() + 0.5, 0);
        World w = location.getWorld();
        assert w != null;
        w.spawnParticle(Particle.HEART, location, 4, 0.5, 1, 0.5);
    }

    public void playBirthEffect(EntityData entityData, int count) {
        Location location = entityData.getEntity().getLocation().clone();
        location.add(0, 1.5, 0);
        World w = location.getWorld();
        assert w != null;
        w.spawnParticle(Particle.COMPOSTER, location, count * 5, 0, 0.25, 0);
    }

}
