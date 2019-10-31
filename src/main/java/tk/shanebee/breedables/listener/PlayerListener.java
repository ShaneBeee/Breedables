package tk.shanebee.breedables.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.event.EntityGetsPregnantEvent;
import tk.shanebee.breedables.manager.EffectManager;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;
import tk.shanebee.breedables.util.Lang;
import tk.shanebee.breedables.util.Utils;

import java.util.HashMap;
import java.util.Map;

class PlayerListener implements Listener {

    private EntityManager entityManager;
    private EffectManager effectManager;
    private Lang lang;
    private Config config;
    private Map<Player, Entity> playerEntityMap = new HashMap<>();

    PlayerListener(Breedables plugin) {
        this.entityManager = plugin.getEntityManager();
        this.effectManager = plugin.getEffectManager();
        this.lang = plugin.getLang();
        this.config = plugin.getBreedablesConfig();
    }

    @EventHandler
    private void onBreedAttempt(PlayerInteractEntityEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);
            return;
        }

        Player player = event.getPlayer();

        // Return if this entity can not breed
        if (!entityManager.hasEntityData(event.getRightClicked())) return;

        Animals clicked = ((Animals) event.getRightClicked());

        // Return if entity can not breed (ie: baby or in love mode already)
        if (!clicked.canBreed()) return;
        if (clicked.isLoveMode()) return;

        // If this is the first clicked entity, add the player and entity to a map
        if (!playerEntityMap.containsKey(player)) {
            playerEntityMap.put(player, clicked);
        } else {
            Entity firstMate = playerEntityMap.get(player);

            // If same gender, prevent love mode
            if (!entityManager.areOpposingGenders(clicked, firstMate)) {
                event.setCancelled(true);

                String gender = entityManager.getEntityData(clicked).getGender().getName().toLowerCase();
                Utils.sendColMsg(player, lang.CANT_BREED_SAME_GENDER.replace("<gender>", gender));

                // Else let the animals continue to mate
            } else {
                //EntityData mom = entityManager.getFemaleData(clicked, firstMate);
                //EntityData dad = entityManager.getMaleData(clicked, firstMate);
                playerEntityMap.remove(player);
                //TODO: do we need to do something here?
            }
        }


    }

    @EventHandler
    private void onBreed(EntityBreedEvent event) {
        // Return if breeder is not a player, but who the heck is breeding these entities?
        if (!(event.getBreeder() instanceof Player)) return;

        Animals entity1 = ((Animals) event.getMother());
        Animals entity2 = ((Animals) event.getFather());

        // If entities are not breedable, or do not have entity data, lets remove their love mode and get out of here
        if (!entityManager.isBreedable(entity1) || !entityManager.isBreedable(entity1) ||
                !entityManager.hasEntityData(entity1) || !entityManager.hasEntityData(entity2)) {
            event.setCancelled(true);
            entity1.setLoveModeTicks(0);
            entity2.setLoveModeTicks(0);
            return;
        }
        Player breeder = ((Player) event.getBreeder());
        EntityData momData = entityManager.getFemaleData(entity1, entity2);
        EntityData dadData = entityManager.getMaleData(entity1, entity2);

        // Prevent an actual baby being born
        event.setCancelled(true);

        int ticks = config.getBreedData(momData).getPregnancyTicks();

        // Call pregnancy event
        EntityGetsPregnantEvent pregEvent = new EntityGetsPregnantEvent(momData, ticks);
        Bukkit.getPluginManager().callEvent(pregEvent);

        if (!pregEvent.isCancelled()) {
            int pregTick = pregEvent.getPregnancyTicks();
            int tilBreedTick = config.getBreedData(momData).getTicksTilBreed();

            // Prevent further breeding for a while
            ((Animals) momData.getEntity()).setLoveModeTicks(0);
            ((Animals) momData.getEntity()).setAge(pregTick + tilBreedTick);
            ((Animals) dadData.getEntity()).setLoveModeTicks(0);
            ((Animals) dadData.getEntity()).setAbsorptionAmount(tilBreedTick);

            // Make mom pregnant
            momData.setPregnant(true);
            momData.setPregnantTicks(pregTick);

            // Play effects
            effectManager.playLoveHearts(momData);
            effectManager.playLoveHearts(dadData);

            Utils.sendColMsg(breeder, "&aSuccessfully bred 2 entities");
        }
    }

}
