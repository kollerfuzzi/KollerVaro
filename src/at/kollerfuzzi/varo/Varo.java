package at.kollerfuzzi.varo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Varo implements Serializable {
	
	private List<Team> teams;
	
	public Varo() {
		teams = new ArrayList<>();
	}
	
	public void addTeam(Team toAdd) {
		for (Team tm: teams) {
			if(tm.getPlayers().stream().anyMatch(
					uid -> toAdd.getPlayers().stream().anyMatch(taUid -> uid.equals(taUid)))) {
				
			}
		}
	}
	
	public List<Team> getTeams() {
		return teams;
	}

}
