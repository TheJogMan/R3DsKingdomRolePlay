package r3dRolePlay;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import r3dRolePlay.rPlayer.RPlayer;

public class Main extends JavaPlugin
{
	static List<RPlayer> players;
	private static Plugin plugin;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		
		File mainDir = new File("plugins/R3DRolePlay");
		if (!(mainDir.exists() && mainDir.isDirectory()))
		{
			mainDir.mkdir();
		}
		File playerDir = new File("plugins/R3DRolePlay/players");
		if (!(playerDir.exists() && playerDir.isDirectory()))
		{
			playerDir.mkdir();
		}
		
		Bukkit.getPluginManager().registerEvents(new CoreListener(), this);
		players = new ArrayList<RPlayer>();
		//load in any players that are already on the server, for if the server had been reloaded
		for (Iterator<? extends Player> iterator = getServer().getOnlinePlayers().iterator(); iterator.hasNext();)
		{
			players.add(new RPlayer(iterator.next()));
		}
	}
	
	public static RPlayer getPlayer(Player player)
	{
		for (Iterator<RPlayer> iterator = players.iterator(); iterator.hasNext();)
		{
			RPlayer rPlayer = iterator.next();
			if (rPlayer.getPlayer().getUniqueId().compareTo(player.getUniqueId()) == 0)
			{
				return rPlayer;
			}
		}
		return null;
	}
	
	public static Plugin getPlugin()
	{
		return plugin;
	}
}