package jogLib.commandAPI.validators;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import jogLib.commandAPI.Command;
import jogLib.commandAPI.Validator;

public class PlayerValidator extends Validator
{
	@Override
	public boolean validate(Command command, CommandSender sender, String[] args)
	{
		return (sender instanceof Player);
	}
	
	@Override
	public String getInvalidReason()
	{
		return "You must be a player to run this command.";
	}
}