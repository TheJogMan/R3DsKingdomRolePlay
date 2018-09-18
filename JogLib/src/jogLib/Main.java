package jogLib;

import java.io.File;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.itemnbtapi.ItemNBTAPI;
import jogLib.commandAPI.CommandManager;
import jogLib.commands.GetHead;
import jogLib.inventoryHandler.customItemMeta.CItemMeta;

public class Main extends JavaPlugin
{
	static Plugin plugin;
	static File file = new File("plugins/JogLib");
	static ItemNBTAPI itemNBTAPI;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		
		if (!(file.exists() && file.isDirectory()))
		{
			file.mkdir();
		}
		
		itemNBTAPI = new ItemNBTAPI();
		itemNBTAPI.init(this);
		
		OfflineNameTracker.init();
		SkinHandler.init();
		CItemMeta.init();
		CommandManager.init();
		
		this.getCommand("GetHead").setExecutor(new GetHead());
	}
	
	public static Plugin getPlugin()
	{
		return plugin;
	}
	
	public ItemNBTAPI getItemNBTAPI()
	{
		return itemNBTAPI;
	}
}

/*
TODO
Update SelectionInterpreter to 1.13 format

ItemConverter
TropicalFishBucket - Broken
StructureBlock - Broken
CommandBlock - Untested
more testing needed in general
*/