package at.kollerfuzzi.varo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Varo implements Serializable {
	
	private List<Team> teams;
	private static String dir;
	
	public Varo() {
		teams = new ArrayList<>();
	}
	
	public void addTeam(Team toAdd, CommandSender sender) {
		boolean doAdd = true;
		for (Team tm: teams) {
			if(tm.getTeamName().equalsIgnoreCase(toAdd.getTeamName())) {
				sender.sendMessage("Teamname \"" + tm.getTeamName() + "\" is already used!");
				doAdd = false;
			}
			for(int i = 0; i < tm.getPlayers().size(); ++i) {
				for(int j = 0; j < toAdd.getPlayers().size(); ++j) {
					if(tm.getPlayers().get(i).equals(toAdd.getPlayers().get(j))){
						sender.sendMessage("Player \"" + tm.getPlayers().get(i) + "\" is already in a team");
						doAdd = false;
					} 
				}
			}
		}
		if(doAdd) {
			teams.add(toAdd);
			serialize();
			sender.sendMessage("Added team \"" + toAdd.getTeamName() + "\" with members: " + 
				toAdd.getPlayers().stream().collect(Collectors.joining(", ")));
		}
	}
	
	public void removeTeam(String toRemoveName, CommandSender sender) {
		Optional<Team> foundTeam =  teams.stream().filter(t -> 
			t.getTeamName().equalsIgnoreCase(toRemoveName)).findAny();
		if(foundTeam.isPresent()) {
			teams.remove(foundTeam.get());
			serialize();
			sender.sendMessage("Team \"" + toRemoveName + "\" deleted");
		} else {
			sender.sendMessage("Team \"" + toRemoveName + "\" not found!");
		}
	}
	
	public void sendTeamList(CommandSender sender) {
		if(teams.size() != 0) {
			StringBuilder str = new StringBuilder("===TEAMS===");
			teams.stream().sorted((a, b) -> a.getTeamName().compareTo(b.getTeamName())).forEach(tm -> {
				str.append("\n" + tm.getTeamName().toUpperCase() + ": ");
				str.append(tm.getPlayers().stream().sorted().collect(Collectors.joining(", ")));
			});
			sender.sendMessage(str.toString());
		} else {
			sender.sendMessage("No teams created.");
		}
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public Team getTeam(String name) {
		Optional<Team> tm = teams.stream().filter(t -> 
			t.getTeamName().equalsIgnoreCase(name)).findAny();
		if(tm.isPresent()) {
			return tm.get();
		} else {
			return null;
		}
	}
	
	public Team getPlayerTeam(String playerName) {
		Optional<Team> tm = teams.stream().filter(t -> t.getPlayers().contains(playerName)).findAny();
		if(tm.isPresent()) {
			return tm.get();
		} else {
			return null;
		}
	}
	
	public boolean areInSameTeam(String p1, String p2) {
		Team p1team = getPlayerTeam(p1);
		Team p2team = getPlayerTeam(p2);
		if(p1team != null && p2team != null) {
			return p1team == p2team;
		} else {
			return false;
		}
	}
	
	public void serialize() {
		try (ObjectOutputStream oos = 
				new ObjectOutputStream(new FileOutputStream(dir+File.separator+"teams.kv"))){
			oos.writeObject(this);
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.WARNING, "There was an error while saving!", e);
		}
	}

	public static Varo deserialize() {
		Varo v = null;
		try (ObjectInputStream ois = 
				new ObjectInputStream(new FileInputStream(dir + File.separator + "teams.kv"))){
			v =  (Varo)ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			Bukkit.getLogger().log(Level.WARNING, "There was an error while loading!");
		}
		return v;
	}
	
	public static void setDir(String dir) {
		Varo.dir = dir;
	}
}
