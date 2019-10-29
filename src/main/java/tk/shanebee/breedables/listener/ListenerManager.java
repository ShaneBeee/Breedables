package tk.shanebee.breedables.listener;

import org.bukkit.plugin.PluginManager;
import tk.shanebee.breedables.Breedables;

public class ListenerManager {

    public ListenerManager(Breedables plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(plugin), plugin);
        pm.registerEvents(new EntityListener(plugin), plugin);
        //pm.registerEvents(new ChunkListener(plugin), plugin); //todo this may not be needed if we are using a timer
    }

}
