package r3dRolePlay;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;

public class SandboxDirector
{
	
	public static void init()
	{
		Bukkit.getPluginManager().registerEvents(new SandboxListener(), Main.getPlugin());
	}
	
	public static boolean isFurnature(Material material)
	{
		if (isSeed(material)) return true;
		if (isLockable(material)) return true;
		
		for (int index = 0; index < furnatureMaterials.length; index++)
		{
			if (furnatureMaterials[index].compareTo(material) == 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isLockable(Material material)
	{
		for (int index = 0; index < lockableMaterials.length; index++)
		{
			if (lockableMaterials[index].compareTo(material) == 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isSeed(Material material)
	{
		for (int index = 0; index < seeds.length; index++)
		{
			if (seeds[index].compareTo(material) == 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static class SandboxListener implements Listener
	{
		@EventHandler
		public void onBlockCanBuild(BlockCanBuildEvent event)
		{
			event.setBuildable(false);
			if (isSeed(event.getMaterial()))
			{
				if (event.getBlock().getType().compareTo(Material.FARMLAND) == 0)
				{
					Farmland farmLand = (Farmland)event.getBlock();
					if (farmLand.getMoisture() != 7)
					{
						event.setBuildable(true);
					}
				}
			}
			else if (isFurnature(event.getMaterial()))
			{
				event.setBuildable(true);
			}
		}
		
		@EventHandler
		public void onBlockBreak(BlockBreakEvent event)
		{
			if (!isFurnature(event.getBlock().getType()))
			{
				event.setCancelled(true);
			}
		}
	}
	
	static Material[] lockableMaterials = {
			Material.CHEST,
			Material.FURNACE,
			Material.DISPENSER,
			Material.DROPPER,
			Material.HOPPER,
			Material.BREWING_STAND,
			Material.TRAPPED_CHEST,
			
			Material.ACACIA_DOOR,
			Material.ACACIA_FENCE_GATE,
			Material.ACACIA_TRAPDOOR,
			
			Material.BIRCH_DOOR,
			Material.BIRCH_FENCE_GATE,
			Material.BIRCH_TRAPDOOR,
			
			Material.DARK_OAK_DOOR,
			Material.DARK_OAK_FENCE_GATE,
			Material.DARK_OAK_TRAPDOOR,
			
			Material.IRON_DOOR,
			Material.IRON_TRAPDOOR,
			
			Material.JUNGLE_DOOR,
			Material.JUNGLE_FENCE_GATE,
			Material.JUNGLE_TRAPDOOR,
			
			Material.OAK_DOOR,
			Material.OAK_FENCE_GATE,
			Material.OAK_TRAPDOOR,
			
			Material.SPRUCE_DOOR,
			Material.SPRUCE_FENCE_GATE,
			Material.SPRUCE_TRAPDOOR
	};
	
	static Material[] seeds = {
			Material.BEETROOT_SEEDS,
			Material.WHEAT_SEEDS,
			Material.CARROT,
			Material.POTATO,
			Material.MELON_SEEDS,
			Material.PUMPKIN_SEEDS,
	};
	
	//lockable materials and seeds also count as furniture
	static Material[] furnatureMaterials = {
			Material.PAINTING,
			Material.ITEM_FRAME,
			Material.SIGN,
			Material.WALL_SIGN,
			Material.TORCH,
			Material.WALL_TORCH,
			Material.CRAFTING_TABLE,
			Material.FLOWER_POT,
			Material.LADDER,
			
			Material.BEETROOTS,
			Material.WHEAT,
			Material.CARROTS,
			Material.POTATOES,
			Material.SUGAR_CANE,
			Material.MELON,
			Material.MELON_STEM,
			Material.PUMPKIN,
			Material.PUMPKIN_STEM,
			Material.COCOA_BEANS,
			Material.COCOA,
			
			Material.SKELETON_SKULL,
			Material.SKELETON_WALL_SKULL,
			Material.WITHER_SKELETON_SKULL,
			Material.WITHER_SKELETON_WALL_SKULL,
			Material.PLAYER_HEAD,
			Material.PLAYER_WALL_HEAD,
			Material.ZOMBIE_HEAD,
			Material.ZOMBIE_WALL_HEAD,
			Material.CREEPER_HEAD,
			Material.CREEPER_WALL_HEAD,
			Material.DRAGON_HEAD,
			Material.DRAGON_WALL_HEAD,
			
			Material.ANVIL,
			Material.CHIPPED_ANVIL,
			Material.DAMAGED_ANVIL,
			
			Material.BLACK_BANNER,
			Material.BLUE_BANNER,
			Material.BROWN_BANNER,
			Material.CYAN_BANNER,
			Material.GRAY_BANNER,
			Material.GREEN_BANNER,
			Material.LIGHT_BLUE_BANNER,
			Material.LIGHT_GRAY_BANNER,
			Material.LIME_BANNER,
			Material.MAGENTA_BANNER,
			Material.ORANGE_BANNER,
			Material.PINK_BANNER,
			Material.PURPLE_BANNER,
			Material.RED_BANNER,
			Material.WHITE_BANNER,
			Material.YELLOW_BANNER,
			
			Material.BLACK_BED,
			Material.BLUE_BED,
			Material.BROWN_BED,
			Material.CYAN_BED,
			Material.GRAY_BED,
			Material.GREEN_BED,
			Material.LIGHT_BLUE_BED,
			Material.LIGHT_GRAY_BED,
			Material.LIME_BED,
			Material.MAGENTA_BED,
			Material.ORANGE_BED,
			Material.PINK_BED,
			Material.PURPLE_BED,
			Material.RED_BED,
			Material.WHITE_BED,
			Material.YELLOW_BED
	};
}