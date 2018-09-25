package r3dRolePlay;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import r3dRolePlay.rPlayer.RPlayer;

public class CoreListener implements Listener
{
	//process this event handler as soon as possible once a new player joins the server
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		RPlayer player = new RPlayer(event.getPlayer());
		Main.players.add(player);
		player.applyCharacter();
	}
	
	//process this event handler after all others when a player leaves the server
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		RPlayer player = Main.getPlayer(event.getPlayer());
		if (player != null)
		{
			player.disconnect();
			Main.players.remove(player);
		}
	}
	
	public static String processCommand(CommandSender sender, String command, Cancellable event)
	{
		if (sender.isOp() && command.toLowerCase().compareTo("/reload") == 0)
		{
			for (Iterator<? extends Player> iterator = Bukkit.getServer().getOnlinePlayers().iterator(); iterator.hasNext();)
			{
				Player player = iterator.next();
				player.sendMessage("Server is reloading, and will be frozen for the next few seconds, anything that happens will not be saved.");
				RPlayer rPlayer = Main.getPlayer(player);
				if (rPlayer != null)
				{
					rPlayer.save();
				}
			}
		}
		return command;
	}
	
	//process this event handler after all others when a player runs a command
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		if (!event.isCancelled())
		{
			event.setMessage(processCommand((CommandSender)event.getPlayer(), event.getMessage(), event));
		}
	}
	
	//process this event handler after all others when the server runs a command
	@EventHandler(priority = EventPriority.MONITOR)
	public void onServerCommand(ServerCommandEvent event)
	{
		if (!event.isCancelled())
		{
			event.setCommand(processCommand(event.getSender(), event.getCommand(), event));
		}
	}
}