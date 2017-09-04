package at.kollerfuzzi.varo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.base.Ticker;
import com.mysql.fabric.xmlrpc.base.Array;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {

	}
	
	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		switch(label.toLowerCase()) {
			case "mkteam":
				if(sender instanceof Player) {
					Player p = (Player)sender;
					// HUNDERT MAL ZWANZIG WEIL EINE SEKUNDE HAT ZWANZIG TICKS
					p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100*20, 255, true, true, Color.FUCHSIA));
				}
				if(args.length >= 3) {
					
				} else {
					
				}
				break;
			case "rmteam":
				break;
			case "tptm":
				break;
			case "tptmlimit":
				break;
			case "killmyself":
				if(sender instanceof Player) {
					Player p = (Player) sender;
					p.sendMessage("Bye " + p.getName() + "!");
					p.setHealth(0);
				}
			default: // EIN GUTER PROGRAMMIERER MACHT STETS EIN DEFAULT
				throw new InvalidParameterException("Command \"" + label + "\" not supported by KollerVaro!");
		}
		return true;
	}
	
	public void onMkTeam(String teamname, String... players) {
		
	}
	
	

	
}