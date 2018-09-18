package jogLib.inventoryHandler.customItemMeta;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.KnowledgeBookMeta;
import org.bukkit.plugin.Plugin;

import jogLib.ByteConverter;

public class CKnowledgeBookMeta extends CItemMeta
{
	CNamespacedKey[] recipes;
	
	@Override
	CItemMeta getNew()
	{
		return new CKnowledgeBookMeta();
	}
	
	@Override
	boolean check(ItemMeta meta)
	{
		return (meta instanceof KnowledgeBookMeta);
	}
	
	@Override
	public ItemStack itemFinalization(ItemStack item)
	{
		return item;
	}
	
	@Override
	void create(ItemMeta meta, ItemStack item)
	{
		KnowledgeBookMeta knowledgeBookMeta = (KnowledgeBookMeta)meta;
		List<NamespacedKey> keys = knowledgeBookMeta.getRecipes();
		recipes = new CNamespacedKey[keys.size()];
		Iterator<NamespacedKey> iterator = keys.iterator();
		for (int index = 0; index < recipes.length; index++)
		{
			recipes[index] = new CNamespacedKey(iterator.next());
		}
	}
	
	@Override
	ItemMeta getItemMeta(ItemMeta meta)
	{
		KnowledgeBookMeta knowledgeBookMeta = (KnowledgeBookMeta)meta;
		for (int index = 0; index < recipes.length; index++)
		{
			NamespacedKey key = recipes[index].getKey();
			if (key != null)
			{
				knowledgeBookMeta.addRecipe(key);
			}
		}
		return knowledgeBookMeta;
	}
	
	@Override
	void load(byte[] data)
	{
		recipes = new CNamespacedKey[ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]})];
		int dataIndex = 4;
		for (int index = 0; index < recipes.length; index++)
		{
			byte[] recipeData = new byte[ByteConverter.toInteger(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3]})];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < recipeData.length; subIndex++)
			{
				recipeData[subIndex] = data[dataIndex + subIndex];
			}
			recipes[index] = new CNamespacedKey(recipeData);
			dataIndex += recipeData.length;
		}
	}
	
	@Override
	byte[] export()
	{
		byte[][] recipeData = new byte[recipes.length][];
		int dataLength = 0;
		for (int index = 0; index < recipes.length; index++)
		{
			recipeData[index] = recipes[index].export();
			dataLength += recipeData[index].length;
		}
		byte[] data = new byte[4 + dataLength + 4 * recipes.length];
		byte[] recipeCountData = ByteConverter.fromInteger(recipes.length);
		data[0] = recipeCountData[0];
		data[1] = recipeCountData[1];
		data[2] = recipeCountData[2];
		data[3] = recipeCountData[3];
		int dataIndex = 4;
		for (int index = 0; index < recipeData.length; index++)
		{
			byte[] recipeLengthData = ByteConverter.fromInteger(recipeData[index].length);
			data[dataIndex] = recipeLengthData[0];
			data[dataIndex + 1] = recipeLengthData[1];
			data[dataIndex + 2] = recipeLengthData[2];
			data[dataIndex + 3] = recipeLengthData[3];
			dataIndex += 4;
			for (int subIndex = 0; subIndex < recipeData[index].length; subIndex++)
			{
				data[dataIndex + subIndex] = recipeData[index][subIndex];
			}
			dataIndex += recipeData[index].length;
		}
		return data;
	}
	
	public class CNamespacedKey
	{
		String key;
		String namespace;
		
		public CNamespacedKey(byte[] data)
		{
			byte[] keyData = new byte[ByteConverter.toInteger(new byte[] {data[0], data[1], data[2], data[3]})];
			byte[] namespaceData = new byte[data.length - keyData.length];
			for (int index = 0; index < keyData.length; index++)
			{
				keyData[index] = data[4 + index];
			}
			for (int index = 0; index < namespaceData.length; index++)
			{
				namespaceData[index] = data[4 + keyData.length + index];
			}
			key = ByteConverter.toString(keyData);
			namespace = ByteConverter.toString(namespaceData);
		}
		
		public CNamespacedKey(NamespacedKey key)
		{
			this.key = key.getKey();
			namespace = key.getNamespace();
		}
		
		public NamespacedKey getKey()
		{
			if (namespace.compareTo("minecraft") == 0)
			{
				return NamespacedKey.minecraft(key);
			}
			else
			{
				Plugin plugin = Bukkit.getPluginManager().getPlugin(namespace);
				if (plugin != null)
				{
					return new NamespacedKey(plugin, key);
				}
				return null;
			}
		}
		
		public byte[] export()
		{
			byte[] data = new byte[4 + key.length() * 2 + namespace.length() * 2];
			byte[] keyLengthData = ByteConverter.fromInteger(key.length());
			data[0] = keyLengthData[0];
			data[1] = keyLengthData[1];
			data[2] = keyLengthData[2];
			data[3] = keyLengthData[3];
			byte[] keyData = ByteConverter.fromString(key);
			byte[] namespaceData = ByteConverter.fromString(namespace);
			for (int index = 0; index < keyData.length; index++)
			{
				data[4 + index] = keyData[index];
			}
			for (int index = 0; index < namespaceData.length; index++)
			{
				data[4 + index + keyData.length] = namespaceData[index];
			}
			return data;
		}
	}
}