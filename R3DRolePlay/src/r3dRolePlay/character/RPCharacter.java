package r3dRolePlay.character;

import java.io.File;

import org.bukkit.Location;

import r3dRolePlay.Faction;
import r3dRolePlay.rPlayer.RPlayer;

public class RPCharacter
{
	//persistent values (saved to file
	String name;
	Location location;
	int unionOpinion;
	int phoenixOpinion;
	int civilianOpinion;
	Faction faction;
	int lootingSkill;
	int charismaSkill;
	int thiefSkill;
	int lockSmithSkill;
	int lockPickSkill;
	
	//temporary values (not saved to file)
	RPlayer player;
	
	//immutable values (set when the character is loaded, and can never change)
	File file;
	
	public RPCharacter(File file)
	{
		load(file, false);
	}
	
	public RPCharacter(File file, boolean totalLoad)
	{
		load(file, totalLoad);
	}
	
	void load(File file, boolean totalLoad)
	{
		
	}
	
	public void save()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
		if (player != null)
		{
			if (!player.getPlayer().getLocation().equals(location))
			{
				player.getPlayer().teleport(location);
			}
		}
		save();
	}
	
	public void setPlayer(RPlayer player)
	{
		this.player = player;
	}
	
	public RPlayer getPlayer()
	{
		return player;
	}
}