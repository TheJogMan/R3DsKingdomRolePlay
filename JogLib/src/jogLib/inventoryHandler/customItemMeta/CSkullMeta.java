package jogLib.inventoryHandler.customItemMeta;

import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTItem;
import de.tr7zw.itemnbtapi.NBTListCompound;
import de.tr7zw.itemnbtapi.NBTType;
import jogLib.ByteConverter;
import jogLib.SkinHandler;
import jogLib.SkinHandler.SkinEntry;

public class CSkullMeta extends CItemMeta
{
	String name;
	UUID id;
	String stringID;
	String value;
	String signature;
	boolean hasOwner;
	
	@Override
	CItemMeta getNew()
	{
		return new CSkullMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof SkullMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		if (hasOwner)
		{
			NBTItem nbti = new NBTItem(item);
			NBTCompound skull = nbti.addCompound("SkullOwner");
			skull.setString("Name", name);
			skull.setString("Id", stringID);
			NBTListCompound texture = skull.addCompound("Properties").getList("textures", NBTType.NBTTagCompound).addCompound();
			if (value.length() > 3)
			{
				texture.setString("Signature", signature);
				texture.setString("Value", value);
			}
			else
			{
				SkinEntry skinEntry = SkinHandler.getFromUUID(id);
				texture.setString("Signature", skinEntry.getSignature());
				texture.setString("Value", skinEntry.getValue());
			}
			item = nbti.getItem();
		}
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		SkullMeta skullMeta = (SkullMeta)meta;
		if (skullMeta.hasOwner())
		{
			NBTItem nbti = new NBTItem(item);
			NBTCompound skull = nbti.getCompound("SkullOwner");
			name = skull.getString("Name");
			id = skullMeta.getOwningPlayer().getUniqueId();
			stringID = skull.getString("Id");
			hasOwner = true;
			NBTListCompound texture = skull.addCompound("Properties").getList("textures", NBTType.NBTTagCompound).addCompound();
			signature = texture.getString("Signature");
			value = texture.getString("Value");
		}
		else
		{
			id = UUID.randomUUID();
			name = "null";
			signature = "null";
			value = "null";
			stringID = "null";
			hasOwner = false;
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		return meta;
	}
	
	@Override
	void load(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		String[] values = ByteConverter.toStringArray(array[0]);
		name = values[0];
		signature = values[1];
		value = values[2];
		hasOwner = ByteConverter.toBoolean(array[1][0]);
		id = ByteConverter.toUUID(array[2]);
		stringID = values[3];
	}
	
	@Override
	byte[] export()
	{
		byte[][] data = new byte[3][];
		data[0] = ByteConverter.fromStringArray(new String[] {name, signature, value, stringID});
		data[1] = new byte[] {ByteConverter.fromBoolean(hasOwner)};
		data[2] = ByteConverter.fromUUID(id);
		return ByteConverter.from2DByteArray(data);
	}
}