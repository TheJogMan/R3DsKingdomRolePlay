package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

import jogLib.ByteConverter;

public class CCreatureSpawner extends CBlockState
{
	int delay;
	int maxNearbyEntities;
	int maxSpawnDelay;
	int minSpawnDelay;
	int playerRange;
	int spawnCount;
	int spawnType;
	int spawnRange;
	
	@Override
	CBlockState getNew()
	{
		return new CCreatureSpawner();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof CreatureSpawner);
	}
	
	@Override
	byte[] export()
	{
		int[] values = new int[] {delay, maxNearbyEntities, maxSpawnDelay, minSpawnDelay, playerRange, spawnCount, spawnType, spawnRange};
		byte[] data = new byte[values.length * 4];
		int dataIndex = 0;
		for (int index = 0; index < values.length; index++)
		{
			byte[] valueData = ByteConverter.fromInteger(values[index]);
			data[dataIndex] = valueData[0];
			data[dataIndex + 1] = valueData[1];
			data[dataIndex + 2] = valueData[2];
			data[dataIndex + 3] = valueData[3];
			dataIndex += 4;
		}
		return data;
	}
	
	@Override
	void load(byte[] data)
	{
		int[] values = new int[8];
		int dataIndex = 0;
		for (int index = 0; index < values.length; index++)
		{
			values[index] = ByteConverter.toInteger(new byte[] {data[dataIndex], data[dataIndex + 1], data[dataIndex + 2], data[dataIndex + 3]});
			dataIndex += 4;
		}
		delay = values[0];
		maxNearbyEntities = values[1];
		maxSpawnDelay = values[2];
		minSpawnDelay = values[3];
		playerRange = values[4];
		spawnCount = values[5];
		spawnType = values[6];
		spawnRange = values[7];
	}
	
	@Override
	void create(BlockState blockState)
	{
		CreatureSpawner creatureSpawner = (CreatureSpawner)blockState;
		delay = creatureSpawner.getDelay();
		maxNearbyEntities = creatureSpawner.getMaxNearbyEntities();
		maxSpawnDelay = creatureSpawner.getMaxSpawnDelay();
		minSpawnDelay = creatureSpawner.getMinSpawnDelay();
		playerRange = creatureSpawner.getRequiredPlayerRange();
		spawnCount = creatureSpawner.getSpawnCount();
		spawnRange = creatureSpawner.getSpawnRange();
		EntityType[] types = EntityType.values();
		for (int index = 0; index < types.length; index++)
		{
			if (types[index].equals(creatureSpawner.getSpawnedType()))
			{
				spawnType = index;
				break;
			}
		}
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		CreatureSpawner creatureSpawner = (CreatureSpawner)blockState;
		creatureSpawner.setDelay(delay);
		creatureSpawner.setMaxNearbyEntities(maxNearbyEntities);
		creatureSpawner.setMaxSpawnDelay(maxSpawnDelay);
		creatureSpawner.setMinSpawnDelay(minSpawnDelay);
		creatureSpawner.setRequiredPlayerRange(playerRange);
		creatureSpawner.setSpawnCount(spawnCount);
		creatureSpawner.setSpawnRange(spawnRange);
		creatureSpawner.setSpawnedType(EntityType.values()[spawnType]);
		return creatureSpawner;
	}
}