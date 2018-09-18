package jogLib.commandAPI;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandMaster extends CategoryParent implements CommandExecutor
{
	String name;
	String description;
	
	public CommandMaster(String name, String description)
	{
		this.name = name;
		this.description = description;
		CommandManager.registeredCommands.add(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args)
	{
		String name = "list";
		String[] newArgs = {};
		if (args.length == 1)
		{
			name = args[0];
		}
		else if (args.length > 1)
		{
			name = args[0];
			newArgs = new String[args.length - 1];
			for (int i = 1; i < args.length; i++)
			{
				newArgs[i - 1] = args[i];
			}
		}
		run(sender, name, newArgs);
		return true;
	}
	
	public boolean checkName(String name)
	{
		if (name.toLowerCase().compareTo(this.name.toLowerCase()) == 0)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public String getFullName()
	{
		return name;
	}
	
	@Override
	public String getDescription()
	{
		return description;
	}
}