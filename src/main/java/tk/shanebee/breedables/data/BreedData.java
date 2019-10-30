package tk.shanebee.breedables.data;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import tk.shanebee.breedables.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Stored config data for each breedable {@link EntityType}
 */
@SuppressWarnings("unused")
public class BreedData {

    private EntityType entityType;
    private int pregnancyTicks;
    private int ticksTilBreed;
    private int ticksTilAdult;
    private int offspringMin, offspringMax;
    private List<Material> breedingTools;

    public BreedData(EntityType entityType, int pregnancyTicks, int ticksTilBreed, int ticksTilAdult, String offspring, String tools) {
        this.entityType = entityType;
        this.pregnancyTicks = pregnancyTicks;
        this.ticksTilBreed = ticksTilBreed;
        this.ticksTilAdult = ticksTilAdult;
        this.offspringMin = getOffspring(offspring)[0];
        this.offspringMax = getOffspring(offspring)[1];
        this.breedingTools = getTools(tools);
    }

    /** Get the entity type
     * @return Entity type
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /** Get the pregnancy ticks
     * @return Pregnancy ticks
     */
    public int getPregnancyTicks() {
        return pregnancyTicks;
    }

    /** Get the ticks til breed again
     * @return Ticks til breed again
     */
    public int getTicksTilBreed() {
        return ticksTilBreed;
    }

    /** Get the ticks til adulthood
     * @return Ticks til adulthood
     */
    public int getTicksTilAdult() {
        return ticksTilAdult;
    }

    /** Get minimum amount of offspring
     * @return Min amount of offspring
     */
    public int getOffspringMin() {
        return offspringMin;
    }

    /** Get maximum amount of offspring
     * @return Max amount of offspring
     */
    public int getOffspringMax() {
        return offspringMax;
    }

    /** Get the breeding tools
     * @return List of breeding tool materials
     */
    public List<Material> getBreedingTools() {
        return breedingTools;
    }

    private int[] getOffspring(String offspring) {
        String[] split = offspring.split(";");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

    private List<Material> getTools(String string) {
        List<Material> list = new ArrayList<>();
        for (String tool : string.split(";")) {
            String debug = "&6Invalid breeding tool: &c" + tool + " &6in breed data for: &b" + entityType;
            try {
                Material material = Material.valueOf(tool.toUpperCase());
                if (!material.isItem()) {
                    Utils.log(debug);
                    continue;
                }
                list.add(material);
            } catch (IllegalArgumentException ex) {
                Utils.log(debug);
            }

        }
        return list;
    }

}
