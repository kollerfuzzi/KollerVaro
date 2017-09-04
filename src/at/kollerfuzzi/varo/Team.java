package at.kollerfuzzi.varo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Team implements Serializable{
	
	private List<UUID> players;
	
	public Team(String... playerNames) {
		players = new ArrayList<>();
		for (String player: playerNames) {
			@SuppressWarnings("deprecation")
			UUID uid = Bukkit.getPlayer(player).getUniqueId();
			players.add(uid);
		}
	}
	
	public List<UUID> getPlayers() {
		return players;
	}
	
}