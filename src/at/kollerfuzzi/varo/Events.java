package at.kollerfuzzi.varo;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener {
	
	Varo varo;
	JavaPlugin plugin;
	
	public Events(Varo varo, JavaPlugin plugin) {
		this.varo = varo;
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent evt) {
		if(evt.getEntity() instanceof Player && evt instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent eeEvt = (EntityDamageByEntityEvent) evt;
			Player damagedPlayer = (Player)eeEvt.getEntity();
			Player damager = null;
			if(eeEvt.getDamager()instanceof Player) {
				damager = (Player)eeEvt.getDamager(); 
			} else if(eeEvt.getDamager() instanceof Arrow) {
				Arrow dmgArrow = (Arrow)eeEvt.getDamager();
				if (dmgArrow.getShooter() instanceof Player) {
					damager = (Player)dmgArrow.getShooter();
				}
			} else {
				Bukkit.broadcastMessage("Damage not from Player!");
			}
			
			
			Bukkit.broadcastMessage(damager.getName() + " hits " + damagedPlayer.getName());
			if(varo.areInSameTeam(damager.getName(), damagedPlayer.getName())) {
				evt.setCancelled(true);
				Bukkit.broadcastMessage("DAMAGE CANCELLED!");
			}
		}
	}
}
