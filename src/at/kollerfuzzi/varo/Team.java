package at.kollerfuzzi.varo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Team implements Serializable {
	
	private String teamName;
	private List<String> players;
	private int teleportsLeft;
	private static int maxTeleports = 3;
	
	public Team(String teamName, String... playerNames) {
		players = new ArrayList<>();
		this.teamName = teamName;
		teleportsLeft = maxTeleports;
		players.addAll(Arrays.asList(playerNames));
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public int getTeleportsLeft() {
		return teleportsLeft;
	}

	public void setTeleportsLeft(int teleportsLeft) {
		this.teleportsLeft = teleportsLeft;
	}
	
}
