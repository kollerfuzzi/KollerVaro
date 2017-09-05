package at.kollerfuzzi.varo;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener {
	
	Varo varo;
	JavaPlugin plugin;
	
	public Events(Varo varo, JavaPlugin plugin) {
		this.varo = varo;
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
}
