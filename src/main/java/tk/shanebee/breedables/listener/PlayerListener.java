package tk.shanebee.breedables.listener;

import org.bukkit.Material;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.inventory.ItemStack;
import tk.shanebee.breedables.Breedables;
import tk.shanebee.breedables.data.EntityData;
import tk.shanebee.breedables.manager.EntityManager;
import tk.shanebee.breedables.util.Config;
import tk.shanebee.breedables.util.Lang;
import tk.shanebee.breedables.util.Utils;

class PlayerListener implements Listener {

    private EntityManager entityManager;
    private Lang lang;
    private Config config;

    PlayerListener(Breedables plugin) {
        this.entityManager = plugin.getEntityManager();
        this.lang = plugin.getLang();
        this.config = plugin.getBreedablesConfig();
    }

    @EventHandler
    private void onBreed(EntityBreedEvent event) {
        if (event.getBreeder() instanceof Player) {
            Player player = ((Player) event.getBreeder());
            Entity entity1 = event.getMother();
            Entity entity2 = event.getFather();

            if (!entityManager.isBreedable(entity1) || !entityManager.isBreedable(entity2)) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            if (!entityManager.opposingGenders(entity1, entity2)) {
                String gender = entityManager.getEntityData(entity1).getGender().getName().toLowerCase();
                Utils.sendColMsg(player, lang.CANT_BREED_SAME_GENDER.replace("<gender>", gender));
                ((Ageable) entity1).setBreed(false);
                ((Ageable) entity2).setBreed(false);
            } else {
                EntityData entityData = entityManager.getFemaleData(entity1, entity2);
                if (entityData.isPregnant()) {
                    Utils.sendColMsg(player, lang.CANT_BREED_ENTITY_PREGNANT);
                } else {

                    entityData.setPregnant(true);
                    entityData.setPregnantTicks(20 * getPregnancySeconds(entityData));
                    assert event.getBredWith() != null;
                    removeOne(event.getBredWith());
                    player.giveExp(event.getExperience());
                    Utils.sendColMsg(player, "&aSuccessfully bred 2 entities");
                    ((Ageable) entity1).setBreed(false);
                    ((Ageable) entity2).setBreed(false);
                }
            }
        }
    }

    private void removeOne(ItemStack itemStack) {
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else {
            itemStack.setType(Material.AIR);
        }
    }

    private int getPregnancySeconds(EntityData data) {
        switch (data.getEntityType()) {
            case CHICKEN:
                return config.PREGNANCY_SEC_TIL_BIRTH_CHICKEN;
            case COW:
                return config.PREGNANCY_SEC_TIL_BIRTH_COW;
            case PIG:
                return config.PREGNANCY_SEC_TIL_BIRTH_PIG;
            case SHEEP:
                return config.PREGNANCY_SEC_TIL_BIRTH_SHEEP;
            case RABBIT:
                return config.PREGNANCY_SEC_TIL_BIRTH_RABBIT;
            case CAT:
                return config.PREGNANCY_SEC_TIL_BIRTH_CAT;
            case WOLF:
                return config.PREGNANCY_SEC_TIL_BIRTH_WOLF;
            default:
                return 0;
        }
    }

}
