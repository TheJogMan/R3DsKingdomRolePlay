package jogLib.commandAPI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CategoryParent extends CommandParent
{
	public abstract String getFullName();
	public abstract String getDescription();
	
	List<Category> categories = new ArrayList<Category>();
	
	public void tabComplete(String[] args, List<String> completions, boolean lastArgumentComplete)
	{
		if (args.length == 0)
		{
			completions.add("Help");
			completions.add("List");
			for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();)
			{
				completions.add(iterator.next().getName());
			}
			for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();)
			{
				completions.add(iterator.next().getName());
			}
		}
		else if (args.length == 1 && !lastArgumentComplete)
		{
			List<String> potentialCompletions = new ArrayList<String>();
			potentialCompletions.add("Help");
			potentialCompletions.add("List");
			for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();)
			{
				potentialCompletions.add(iterator.next().getName());
			}
			for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();)
			{
				potentialCompletions.add(iterator.next().getName());
			}
			List<String> viableCompletions = new ArrayList<String>();
			for (Iterator<String> iterator = potentialCompletions.iterator(); iterator.hasNext();)
			{
				String candidate = iterator.next();
				if (candidate.toLowerCase().startsWith(args[0].toLowerCase()))
				{
					viableCompletions.add(candidate);
				}
			}
			completions.addAll(viableCompletions);
		}
		else
		{
			String argument = args[0];
			String[] newArgs = new String[args.length - 1];
			for (int index = 0; index < newArgs.length; index++) newArgs[index] = args[index + 1];
			Category category = getCategory(argument);
			if (category != null)
			{
				category.tabComplete(newArgs, completions, lastArgumentComplete);
			}
			else
			{
				if (argument.toLowerCase().compareTo("help") == 0)
				{
					List<String> potentialCompletions = new ArrayList<String>();
					potentialCompletions.add("Help");
					potentialCompletions.add("List");
					for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();)
					{
						potentialCompletions.add(iterator.next().getName());
					}
					for (Iterator<Command> iterator = commands.iterator(); iterator.hasNext();)
					{
						potentialCompletions.add(iterator.next().getName());
					}
					List<String> viableCompletions = new ArrayList<String>();
					
					if (newArgs.length == 0)
					{
						viableCompletions = potentialCompletions;
					}
					else
					{
						for (Iterator<String> iterator = potentialCompletions.iterator(); iterator.hasNext();)
						{
							String candidate = iterator.next();
							if (candidate.toLowerCase().startsWith(newArgs[0].toLowerCase()))
							{
								viableCompletions.add(candidate);
							}
						}
					}
					
					completions.addAll(viableCompletions);
				}
				else
				{
					Command command = getCommand(argument);
					if (command != null)
					{
						command.tabComplete(newArgs, completions, lastArgumentComplete);
					}
				}
			}
		}
	}
	
	public Category getCategory(String name)
	{
		return getCategory(name, true);
	}
	
	public Category getCategory(String name, boolean includeAlias)
	{
		name = name.toLowerCase();
		
		for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();)
		{
			Category category = iterator.next();
			if (category.checkName(name, false))
			{
				return category;
			}
		}
		
		if (includeAlias)
		{
			for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();)
			{
				Category category = iterator.next();
				if (category.checkName(name, true))
				{
					return category;
				}
			}
		}
		return null;
	}
	
	void run(CommandSender sender, String name, String[] args)
	{
		if (name.compareTo("list") == 0)
		{
			list(sender);
		}
		else if (name.compareTo("help") == 0)
		{
			if (args.length == 1)
			{
				help(sender, args[0]);
			}
			else
			{
				sender.sendMessage("The Help command only accepts 1 argument!");
			}
		}
		else
		{
			Category category = getCategory(name);
			if (category != null)
			{
				String newName = "list";
				String[] newArgs = {};
				if (args.length == 1)
				{
					newName = args[0];
				}
				else if (args.length > 1)
				{
					newName = args[0];
					newArgs = new String[args.length - 1];
					for (int i = 1; i < args.length; i++)
					{
						newArgs[i - 1] = args[i];
					}
				}
				category.run(sender, newName, newArgs);
			}
			else
			{
				Command command = getCommand(name);
				if (command != null)
				{
					Validator validator = command.getValidator();
					boolean valid = true;
					if (validator != null)
					{
						valid = validator.validate(command, sender, args);
					}
					if (valid)
					{
						boolean success = command.execute(sender, args);
						if (success)
						{
							if (command.reportSuccess)
							{
								sender.sendMessage("Command executed succesfully!");
							}
						}
						else
						{
							sender.sendMessage("Command failed!");
							help(sender, name);
						}
					}
					else
					{
						sender.sendMessage(validator.getInvalidReason());
					}
				}
				else
				{
					sender.sendMessage("§4" + name + "§r is not a category or command, try running §3/" + getFullName() + " List§r");
				}
			}
		}
	}
	
	public String getCategoryList()
	{
		String list = "§3None§r";
		if (categories.size() > 0)
		{
			list = "";
			for (Iterator<Category> iterator = categories.iterator(); iterator.hasNext();)
			{
				Category category = iterator.next();
				list = list + "§3" + category.name + "§r";
				if (iterator.hasNext())
				{
					list = list + ", ";
				}
			}
		}
		return list;
	}
	
	void list(CommandSender sender)
	{
		String bar = "§4-----------------------------------------------------§r";
		String commandList = getCommandList();
		if (commandList.compareTo("§3None§r") == 0)
		{
			commandList = "";
		}
		else
		{
			commandList = ", " + commandList;
		}
		String prefix = "";
		if (!(sender instanceof Player))
		{
			prefix = "\n";
		}
		sender.sendMessage(prefix + bar + "\n§6Category:§r\n /" + getFullName() + "\n§6Description:§r\n " + getDescription() +
						"\n§6Sub-Categories:§r\n  " + getCategoryList() + "\n§6Commands:§r\n  §3List§r, §3Help§r" + commandList +
						"\n§6Run §a/" + getFullName() + " Help <command>§6 to learn more about a command.§r\n" + bar);
	}
	
	String concatHelp(String command, String args, String description, List<String> aliases)
	{
		String bar = "§4-----------------------------------------------------§r";
		String mes = bar + "\n§6Command:§r\n /" + command + "\n§6Arguments:§r\n " + args + "\n§6Description:§r\n " + description + "\n§6Aliases:§r\n ";
		if (aliases.size() == 0)
		{
			mes += "None";
		}
		else
		{
			for (Iterator<String> iterator = aliases.iterator(); iterator.hasNext();)
			{
				mes += iterator.next();
				if (iterator.hasNext())
				{
					mes += ", ";
				}
			}
		}
		return mes + "\n" + bar;
	}
	
	void help(CommandSender sender, String name)
	{
		String prefix = "";
		if (!(sender instanceof Player))
		{
			prefix = "\n";
		}
		Command command = getCommand(name);
		if (command != null)
		{
			sender.sendMessage(prefix + concatHelp(command.getFullName(), command.getArgumentUsage(), command.getDescription(), command.getAliases()));
		}
		else
		{
			if (name.toLowerCase().compareTo("list") == 0)
			{
				sender.sendMessage(prefix + concatHelp(getFullName() + " List","None","Lists the categories and commands in this category.", new ArrayList<String>()));
			}
			else if (name.toLowerCase().compareTo("help") == 0)
			{
				sender.sendMessage(prefix + concatHelp(getFullName() + " Help","<commandName>","Shows more information on the given command.", new ArrayList<String>()));
			}
			else
			{
				sender.sendMessage("Command not found!");
			}
		}
	}
}