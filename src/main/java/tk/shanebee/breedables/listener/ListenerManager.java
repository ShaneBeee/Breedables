package tk.shanebee.breedables.listener;

import tk.shanebee.breedables.Breedables;

public class ListenerManager {

    public ListenerManager(Breedables plugin) {
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new EntityListener(plugin), plugin);
    }

}
