package jogLib.commandAPI;

import org.bukkit.command.CommandSender;

public abstract class Validator
{
	public abstract boolean validate(Command command, CommandSender sender, String[] args);
	public abstract String getInvalidReason();
}