package r3dRolePlay.rPlayer;

import org.bukkit.entity.Player;

public class RPlayer extends ROfflinePlayer
{
	Player player;
	boolean disconnected;
	
	public RPlayer(Player player)
	{
		super(player, true);
		this.player = player;
		disconnected = false;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void disconnect()
	{
		if (!disconnected)
		{
			
			
			player.kickPlayer("disconnected.");
			disconnected = true;
		}
	}
}