package jogLib.commandAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class CommandParent
{
	public abstract Validator getValidator();
	public abstract String getFullName();
	
	List<Command> commands = new ArrayList<Command>();
	
	public Command getCommand(String name)
	{
		return getCommand(name, true);
	}
	
	public Command getCommand(String name, boolean includeAlias)
	{
		name = name.toLowerCase();
		
		for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();)
		{
			Command command = iterator.next();
			if (command.checkName(name, false))
			{
				return command;
			}
		}
		
		if (includeAlias)
		{
			for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();)
			{
				Command command = iterator.next();
				if (command.checkName(name, true))
				{
					return command;
				}
			}
		}
		return null;
	}
	
	public String getCommandList()
	{
		String list = "§3None§r";
		if (commands.size() > 0)
		{
			list = "";
			for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();)
			{
				Command command = iterator.next();
				list = list + "§3" + command.name + "§r";
				if (iterator.hasNext())
				{
					list = list + ", ";
				}
			}
		}
		return list;
	}
}