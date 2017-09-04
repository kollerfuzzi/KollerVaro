package at.kollerfuzzi.varo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;

public class Varo implements Serializable {
	
	private List<Team> teams;
	private static String dir;
	
	public Varo(String dir) {
		teams = new ArrayList<>();
		Varo.dir = dir;
	}
	
	public void addTeam(Team toAdd) {
		for (Team tm: teams) {
			for(int i = 0;i < tm.getPlayers().size();++i) {
				for(int j = 0;j<toAdd.getPlayers().size();++i) {
					
					if(tm.getPlayers().get(i).equals(toAdd.getPlayers().get(j))){
						Bukkit.broadcastMessage("Player " + Bukkit.getPlayer(tm.getPlayers().get(i)) + "is already in a team");
						return;
					} 
				}
			}
		}
		teams.add(toAdd);
	}
	
	public void removeTeam(Team toRemove) {
		teams.remove(toRemove);
	}
	
	public List<Team> getTeams() {
		return teams;
	}
	
	public void serialize() {
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(dir+File.separator+"teams.kv"));
			oos.writeObject(this);
		} catch (IOException e) {
			
		}
		try {
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
		 
		try {
			ois.close();
		} catch (IOException e) {
		}
		return v;
	}
}
