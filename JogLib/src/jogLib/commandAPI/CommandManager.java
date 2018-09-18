package jogLib.commandAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;

import jogLib.Main;

public class CommandManager
{
	static List<CommandMaster> registeredCommands;
	
	public static void init()
	{
		registeredCommands = new ArrayList<CommandMaster>();
		
		Bukkit.getPluginManager().registerEvents(new TabCompletor(), Main.getPlugin());
	}
	
	public static CommandMaster getCommand(String name)
	{
		for (Iterator<CommandMaster> iterator = registeredCommands.iterator(); iterator.hasNext();)
		{
			CommandMaster command = iterator.next();
			if (command.checkName(name))
			{
				return command;
			}
		}
		return null;
	}
}