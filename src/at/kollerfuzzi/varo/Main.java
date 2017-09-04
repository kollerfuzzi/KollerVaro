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

/*
 * Bugs:
 * 	If player is not on server while adding -> server crashes
 *  uuid -> name?
 *  list teams one missing
 *  rmteams not case sensitive
 */
public class Main extends JavaPlugin {
	
	Varo varo;

	@Override
	public void onEnable() {
		Varo.setDir(getDataFolder().toString());
		varo = Varo.deserialize();
		if(varo == null) {
			varo = new Varo();
		}
	}
	
	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean commandOk = true;
		switch(label.toLowerCase()) {
			case "mkteam":
				if(args.length >= 3) {
					Team toAdd = new Team(args[0], Arrays.copyOfRange(args, 1, args.length - 1));
					varo.addTeam(toAdd, sender);
				} else {
					commandOk = false;
				}
				break;
			case "rmteam":
				if(args.length == 1) {
					varo.removeTeam(args[0], sender);
				} else {
					commandOk = false;
				}
				break;
			case "teams":
				varo.sendTeamList(sender);
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
				break;
			default: // EIN GUTER PROGRAMMIERER MACHT STETS EIN DEFAULT
				throw new InvalidParameterException("Command \"" + label + "\" not supported by KollerVaro!");
		}
		return commandOk;
	}
	
	
	

	
}