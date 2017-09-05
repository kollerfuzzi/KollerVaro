package at.kollerfuzzi.varo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
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
			}
			
			if(damager != null && varo.areInSameTeam(damager.getName(), damagedPlayer.getName())) {
				evt.setCancelled(true);
				damager.sendMessage("You can't damage your teammate!");
			}
		}
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent evt) {
		if (evt.getEntity() instanceof Player && evt.getEntity().getKiller() != null && 
				evt.getEntity().getKiller() instanceof Player) {
			Player killed = (Player)evt.getEntity();
			Player killer = (Player)evt.getEntity().getKiller();
			if(!varo.areInSameTeam(killed.getName(), killer.getName())) {
				killer.giveExpLevels(2);
				killer.getInventory().addItem(new ItemStack(Material.DIAMOND, 4));
				killer.sendMessage("You killed an enemy player!");
			}
		}
	}
}
