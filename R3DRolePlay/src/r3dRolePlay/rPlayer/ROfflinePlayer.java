package r3dRolePlay.rPlayer;

import java.io.File;

import org.bukkit.OfflinePlayer;

public class ROfflinePlayer
{
	//persistent values (saved to file
	
	
	//temporary values (not saved to file)
	
	
	//immutable values (set when the player is created, and can never change)
	OfflinePlayer player;
	File file;
	
	public ROfflinePlayer(OfflinePlayer player)
	{
		load(player, false);
	}
	
	public ROfflinePlayer(OfflinePlayer player, boolean totalLoad)
	{
		load(player, totalLoad);
	}
	
	private void load(OfflinePlayer player, boolean totalLoad)
	{
		this.player = player;
		file = new File("plugins/R3DRolePlay/players/" + player.getUniqueId().toString());
		
		
	}
	
	public OfflinePlayer getOfflinePlayer()
	{
		return player;
	}
}