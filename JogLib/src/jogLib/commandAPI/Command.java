package jogLib.commandAPI;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

public abstract class Command
{
	String name;
	String arguments;
	String description;
	CommandParent parent;
	Validator validator;
	boolean reportSuccess = true;
	List<String> aliases;
	
	public abstract boolean execute(CommandSender sender, String[] args);
	
	void initialize(String name, String arguments, String description, CommandParent parent, Validator validator)
	{
		if (parent.getCommand(name) != null)
		{
			throw new IllegalStateException("Command name [" + name + "] already taken in provided CommandParent!");
		}
		
		this.name = name;
		this.arguments = arguments;
		this.description = description;
		this.parent = parent;
		this.validator = validator;
		aliases = new ArrayList<String>();
		
		parent.commands.add(this);
	}
	
	public Command(CommandParent parent, String name, String arguments, String description)
	{
		initialize(name, arguments, description, parent, parent.getValidator());
	}
	
	public Command(CommandParent parent, String name, String arguments, String description, Validator validator)
	{
		initialize(name, arguments, description, parent, validator);
	}
	
	public boolean reportSuccess()
	{
		return reportSuccess;
	}
	
	public void setReportSuccess(boolean reportSuccess)
	{
		this.reportSuccess = reportSuccess;
	}
	
	public boolean checkName(String name)
	{
		return checkName(name, true);
	}
	
	public boolean checkName(String name, boolean checkAliases)
	{
		name = name.toLowerCase();
		if (name.compareTo(this.name.toLowerCase()) == 0)
		{
			return true;
		}
		else
		{
			if (checkAliases)
			{
				return aliases.contains(name);
			}
			else
			{
				return false;
			}
		}
	}
	
	public void addAlias(String alias)
	{
		aliases.add(alias.toLowerCase());
	}
	
	public List<String> getAliases()
	{
		return aliases;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getArgumentUsage()
	{
		if (arguments.length() > 0)
		{
			return arguments;
		}
		else
		{
			return "None";
		}
	}
	
	public String getArguments()
	{
		return arguments;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public CommandParent getParent()
	{
		return parent;
	}
	
	public Validator getValidator()
	{
		return validator;
	}
	
	public String getFullName()
	{
		return parent.getFullName() + " " + name;
	}
	
	public void tabComplete(String[] args, List<String> completions, boolean lastArgumentComplete)
	{
		//place holder, to be overridden by commands that need it
	}
}