package jogLib.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTItem;
import de.tr7zw.itemnbtapi.NBTListCompound;
import de.tr7zw.itemnbtapi.NBTType;
import jogLib.SelectorInterpreter;
import jogLib.SkinHandler;
import jogLib.SkinHandler.SkinEntry;

public class GetHead implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		int amount = 1;
		Entity[] targets = new Entity[] {(Entity)sender};
		String name = "null";
		UUID id = UUID.randomUUID();
		String value = "null";
		String signature = "null";
		
		if (args.length == 0 || args.length == 4 || args.length > 5)
		{
			return false;
		}
		
		if (args.length > 0)
		{
			if (sender instanceof Entity)
			{
				name = args[0];
			}
			else
			{
				sender.sendMessage("You must specify the recipient of the item!");
				return false;
			}
			
			boolean dataGiven = false;
			if (args.length > 1)
			{
				targets = SelectorInterpreter.getTargets(sender, args[1]);
				
				if (args.length > 2)
				{
					amount = Integer.parseInt(args[2]);
					
					if (args.length == 5)
					{
						dataGiven = true;
						signature = args[3];
						value = args[4];
					}
				}
			}
			
			if (!dataGiven)
			{
				SkinEntry entry = SkinHandler.getFromName(name);
				value = entry.getValue();
				signature = entry.getSignature();
				name = entry.getName();
				id = entry.getID();
			}
		}
		
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta)item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(id));
		meta.setDisplayName("§e" + name + "'s Head§r");
		NBTItem nbti = new NBTItem(item);
		NBTCompound skull = nbti.addCompound("SkullOwner");
		skull.setString("Name", name);
		skull.setString("Id", id.toString());
		NBTListCompound texture = skull.addCompound("Properties").getList("textures", NBTType.NBTTagCompound).addCompound();
		texture.setString("Signature", signature);
		texture.setString("Value", value);
		item = nbti.getItem();
		
		for (int targetIndex = 0; targetIndex < targets.length; targetIndex++)
		{
			if (targets[targetIndex] instanceof InventoryHolder)
			{
				for (int amountIterator = 0; amountIterator < amount; amountIterator++)
				{
					((InventoryHolder)targets[targetIndex]).getInventory().addItem(item);
				}
				sender.sendMessage("Gave " + amount + " of §e[" + name + "'s Head]§r to " + targets[targetIndex].getCustomName());
			}
		}
		
		return true;
	}
}