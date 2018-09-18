package r3dRolePlay;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import r3dRolePlay.rPlayer.RPlayer;

public class CoreListener implements Listener
{
	//process this event handler as soon as possible once a new player joins the server
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Main.players.add(new RPlayer(event.getPlayer()));
	}
	
	//process this event handler after all others when a player leaves the server
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		RPlayer player = Main.getPlayer(event.getPlayer());
		if (player != null)
		{
			player.disconnect();
			Main.players.remove(player);
		}
	}
}