package jogLib.commandAPI.validators;

import org.bukkit.command.CommandSender;

import jogLib.commandAPI.Command;
import jogLib.commandAPI.Validator;

public class OPValidator extends Validator
{
	@Override
	public boolean validate(Command command, CommandSender sender, String[] args)
	{
		return sender.isOp();
	}
	
	@Override
	public String getInvalidReason()
	{
		return "You must be OP to run this command.";
	}
}
