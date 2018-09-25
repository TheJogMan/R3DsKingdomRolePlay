package jogLib.inventoryHandler.customItemMeta.customBlockState;

import org.bukkit.block.BlockState;
import org.bukkit.block.Structure;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.block.structure.UsageMode;

import jogLib.ByteConverter;
import jogLib.inventoryHandler.CVector;

public class CStructure extends CBlockState
{
	String author;
	String name;
	String metaData;
	float integrity;
	long seed;
	boolean boundingBoxVisible;
	boolean ignoreEntities;
	boolean showAir;
	byte mirror;
	byte rotation;
	byte usage;
	CVector relativePosition;
	CVector size;
	
	@Override
	CBlockState getNew()
	{
		return new CStructure();
	}
	
	@Override
	boolean check(BlockState blockState)
	{
		return (blockState instanceof Structure);
	}
	
	@Override
	byte[] export()
	{
		byte[][] data = new byte[8][];
		data[0] = ByteConverter.fromString(author);
		data[1] = ByteConverter.fromString(name);
		data[2] = ByteConverter.fromString(metaData);
		data[3] = ByteConverter.fromFloat(integrity);
		data[4] = ByteConverter.fromLong(seed);
		data[5] = new byte[] {ByteConverter.fromBoolean(boundingBoxVisible), ByteConverter.fromBoolean(ignoreEntities), ByteConverter.fromBoolean(showAir), mirror, rotation, usage};
		data[6] = relativePosition.export();
		data[7] = size.export();
		return ByteConverter.from2DByteArray(data);
	}
	
	@Override
	void load(byte[] data)
	{
		byte[][] array = ByteConverter.to2DByteArray(data);
		author = ByteConverter.toString(array[0]);
		name = ByteConverter.toString(array[1]);
		metaData = ByteConverter.toString(array[2]);
		integrity = ByteConverter.toFloat(array[3]);
		seed = ByteConverter.toLong(array[4]);
		boundingBoxVisible = ByteConverter.toBoolean(array[5][0]);
		ignoreEntities = ByteConverter.toBoolean(array[5][1]);
		showAir = ByteConverter.toBoolean(array[5][2]);
		mirror = array[5][3];
		rotation = array[5][4];
		usage = array[5][5];
		relativePosition = new CVector(array[6]);
		size = new CVector(array[7]);
	}
	
	@Override
	void create(BlockState blockState)
	{
		Structure structure = (Structure)blockState;
		author = structure.getAuthor();
		name = structure.getStructureName();
		metaData = structure.getMetadata();
		integrity = structure.getIntegrity();
		seed = structure.getSeed();
		Mirror[] mirrors = Mirror.values();
		for (byte index = 0; index < mirrors.length; index++)
		{
			if (mirrors[index].equals(structure.getMirror()))
			{
				mirror = index;
				break;
			}
		}
		StructureRotation[] rotations = StructureRotation.values();
		for (byte index = 0; index < rotations.length; index++)
		{
			if (rotations[index].equals(structure.getRotation()))
			{
				rotation = index;
				break;
			}
		}
		UsageMode[] modes = UsageMode.values();
		for (byte index = 0; index < modes.length; index++)
		{
			if (modes[index].equals(structure.getUsageMode()))
			{
				usage = index;
				break;
			}
		}
		relativePosition = new CVector(structure.getRelativePosition());
		size = new CVector(structure.getStructureSize());
	}
	
	@Override
	BlockState getBlockState(BlockState blockState)
	{
		Structure structure = (Structure)blockState;
		structure.setAuthor(author);
		structure.setStructureName(name);
		structure.setMetadata(metaData);
		structure.setIntegrity(integrity);
		structure.setSeed(seed);
		structure.setMirror(Mirror.values()[mirror]);
		structure.setRotation(StructureRotation.values()[rotation]);
		System.out.println(UsageMode.values()[0]);
		structure.setUsageMode(UsageMode.values()[usage]);
		structure.setRelativePosition(relativePosition.getVector().toBlockVector());
		structure.setStructureSize(size.getVector().toBlockVector());
		return structure;
	}
}