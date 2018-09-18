package jogLib.commandAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

import jogLib.ListUtils;

public class TabCompletor implements Listener
{
	@EventHandler
	public void onTabComplete(TabCompleteEvent event)
	{
		String commandName = event.getBuffer().substring(1, event.getBuffer().indexOf(' '));
		List<String> args = new ArrayList<String>();
		if (event.getBuffer().length() > commandName.length() + 2)
		{
			String currentArg = "";
			for (int index = event.getBuffer().indexOf(' ') + 1; index < event.getBuffer().length(); index++)
			{
				char ch = event.getBuffer().charAt(index);
				if (ch == ' ')
				{
					args.add(currentArg);
					currentArg = "";
				}
				else
				{
					currentArg += ch;
				}
			}
			if (currentArg.length() > 0)
			{
				args.add(currentArg);
			}
		}
		CommandMaster command = CommandManager.getCommand(commandName);
		if (command != null)
		{
			List<String> completions = event.getCompletions();
			completions.clear();
			command.tabComplete(ListUtils.getStringArray(args), completions, event.getBuffer().charAt(event.getBuffer().length() - 1) == ' ');
		}
	}
}