package tk.shanebee.breedables.data;

import org.bukkit.entity.EntityType;

public class BreedData {

    private EntityType entityType;
    private int pregnancyTicks;
    private int ticksTilBreed;
    private int ticksTilAdult;
    private int offspringMin, offspringMax;

    public BreedData(EntityType entityType, int pregnancyTicks, int ticksTilBreed, int ticksTilAdult, String offspring) {
        this.entityType = entityType;
        this.pregnancyTicks = pregnancyTicks;
        this.ticksTilBreed = ticksTilBreed;
        this.ticksTilAdult = ticksTilAdult;
        this.offspringMin = getOffspring(offspring)[0];
        this.offspringMax = getOffspring(offspring)[1];
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getPregnancyTicks() {
        return pregnancyTicks;
    }

    public int getTicksTilBreed() {
        return ticksTilBreed;
    }

    public int getTicksTilAdult() {
        return ticksTilAdult;
    }

    public int getOffspringMin() {
        return offspringMin;
    }

    public int getOffspringMax() {
        return offspringMax;
    }

    private int[] getOffspring(String offspring) {
        String[] split = offspring.split(";");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

}
