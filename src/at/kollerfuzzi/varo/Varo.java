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
		for (Team tm: teams) {
			for(int i = 0;i < tm.getPlayers().size();++i) {
				for(int j = 0;j<toAdd.getPlayers().size();++j) {
					
					if(tm.getPlayers().get(i).equals(toAdd.getPlayers().get(j))){
						sender.sendMessage("Player " + Bukkit.getPlayer(tm.getPlayers().get(i)) + "is already in a team");
						return;
					} 
				}
			}
		}
		teams.add(toAdd);
		serialize();
	}
	
	public void removeTeam(String toRemoveName, CommandSender sender) {
		Optional<Team> foundTeam =  teams.stream().filter(t -> t.getTeamName().equals(toRemoveName)).findAny();
		if(foundTeam.isPresent()) {
			teams.remove(foundTeam.get());
			serialize();
		} else {
			sender.sendMessage("Team \"" + toRemoveName + "\" not found!");
		}
	}
	
	public void sendTeamList(CommandSender sender) {
		String str = "===TEAMS===";
		for (Team tm: teams) {
			str += "\n" + tm.getTeamName().toUpperCase() + ": ";
			for (UUID id: tm.getPlayers()) {
				str += Bukkit.getPlayer(id).getName() + ", ";
			}
			str = str.substring(0, str.length() - 2);
		}
		sender.sendMessage(str);
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public void serialize() {
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dir+File.separator+"teams.kv"));
			oos.writeObject(this);
			oos.close();
		} catch (IOException e) {
			
		}
	}

	public static Varo deserialize() {
		ObjectInputStream ois = null;
		Varo v = null;
		
		 try {
			ois = new ObjectInputStream(new FileInputStream(dir+File.separator+"teams.kv"));
			v =  (Varo)ois.readObject();
		} catch (IOException e) {
			Bukkit.broadcastMessage("no file found");
		} catch (ClassNotFoundException e) {
		}
		if(ois != null) {
			try {
				ois.close();
			} catch (IOException e) {
			}
		}
		return v;
	}
	
	public static void setDir(String dir) {
		Varo.dir = dir;
	}
}
