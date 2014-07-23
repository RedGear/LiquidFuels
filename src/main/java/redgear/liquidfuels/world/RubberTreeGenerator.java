package redgear.liquidfuels.world;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.IWorldGenerator;

public class RubberTreeGenerator implements IWorldGenerator {

	private final boolean doNotify;

	public RubberTreeGenerator() {
		this(false);
	}

	public RubberTreeGenerator(boolean doNotify) {
		this.doNotify = doNotify;
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		LiquidFuels.inst.logDebug("Checking for tree gen.");
		int minX = chunkX * 16;
		int minZ = chunkZ * 16;

		BiomeGenBase biome = world.getBiomeGenForCoords(minX + 8, minZ + 8);
		Type[] typeArray = BiomeDictionary.getTypesForBiome(biome);
		List<?> typeList = Arrays.asList(typeArray);

		int x;
		int y;
		int z;

		if (/*typeList.contains(Type.JUNGLE)*/ true){
			LiquidFuels.inst.logDebug("It's a Jungle, planting rubber saplings ....");
			
			for (int tries = rand.nextInt(3) + 4; tries > 0; tries--) {
				x = minX + rand.nextInt(16);
				z = minZ + rand.nextInt(16);
				y = world.getActualHeight();

				Block block;
				while (y > 0) {
					block = world.getBlock(x, y--, z);

					if (!(block.isAir(world, x, y, z) || block.isLeaves(world, x, y, z)
							|| block.isReplaceable(world, x, y, z) || block.canBeReplacedByLeaves(world, x, y, z))) {
						growTree(world, x, y, z, rand);
						y = -1;
					}
				}
			}
		}
	}

	public boolean growTree(World world, int x, int y, int z, Random rand) {
		LiquidFuels.inst.logDebug("Growing a tree ...");
		int treeHeight = rand.nextInt(3) + 5;
		int worldHeight = world.getActualHeight();

		if (y < 1 || y + treeHeight + 1 >= worldHeight){
			LiquidFuels.inst.logDebug("Tree failed to grow due to: Hitting height limit");
			return false;
		}

		Block block = world.getBlock(x, y - 1, z);

		if (block.canSustainPlant(world, x, y, z, ForgeDirection.UP, LiquidFuels.rubberSapling)) {
			block.onPlantGrow(world, x, y - 1, z, x, y, z);

			int xOffset;
			int yOffset;
			int zOffset;

			for (yOffset = y; yOffset <= y + 1 + treeHeight; ++yOffset) {
				byte radius = 1;

				if (yOffset <= y + 1)
					radius = 0;

				if (yOffset >= y + 1 + treeHeight - 2)
					radius = 2;

				if (yOffset >= 0 & yOffset < worldHeight) {
					if (radius == 0) {
						block = world.getBlock(x, yOffset, z);
						if (!(block.isLeaves(world, x, yOffset, z) || block.isAir(world, x, yOffset, z)
								|| block.isReplaceable(world, x, yOffset, z) || block.canBeReplacedByLeaves(world, x,
								yOffset, z))){
							LiquidFuels.inst.logDebug("Tree failed to grow due to: Not enough vertical space");
							return false;
						}
					} else
						for (xOffset = x - radius; xOffset <= x + radius; ++xOffset)
							for (zOffset = z - radius; zOffset <= z + radius; ++zOffset) {
								block = world.getBlock(xOffset, yOffset, zOffset);

								if (!(block.isLeaves(world, xOffset, yOffset, zOffset)
										|| block.isAir(world, xOffset, yOffset, zOffset) || block
											.canBeReplacedByLeaves(world, xOffset, yOffset, zOffset))){
									LiquidFuels.inst.logDebug("Tree failed to grow due to: Not enough lateral space");
									return false;
								}
							}
				} else{
					LiquidFuels.inst.logDebug("Tree failed to grow due to: World Height again");
					return false;
				}
			}

			block = world.getBlock(x, y - 1, z);
			if (!block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, LiquidFuels.rubberSapling)){
				LiquidFuels.inst.logDebug("Tree failed to grow due to: Something went weird ....");
				return false; // abort, something went weird
			}
			block.onPlantGrow(world, x, y - 1, z, x, y, z);

			for (yOffset = y - 3 + treeHeight; yOffset <= y + treeHeight; ++yOffset) {
				int var12 = yOffset - (y + treeHeight), center = 1 - var12 / 2;

				for (xOffset = x - center; xOffset <= x + center; ++xOffset) {
					int xPos = xOffset - x, t = xPos >> 31;
				xPos = xPos + t ^ t;

				for (zOffset = z - center; zOffset <= z + center; ++zOffset) {
					int zPos = zOffset - z;
					zPos = zPos + (t = zPos >> 31) ^ t;

					block = world.getBlock(xOffset, yOffset, zOffset);

					if ((xPos != center | zPos != center || rand.nextInt(2) != 0 && var12 != 0)
								&& (block == null || block.isLeaves(world, xOffset, yOffset, zOffset)
										|| block.isAir(world, xOffset, yOffset, zOffset) || block
											.canBeReplacedByLeaves(world, xOffset, yOffset, zOffset)))
						setBlock(world, xOffset, yOffset, zOffset, LiquidFuels.rubberLeaves, 0);
				}
				}
			}

			for (yOffset = 0; yOffset < treeHeight; ++yOffset) {
				block = world.getBlock(x, y + yOffset, z);

				if (block == null || block.isAir(world, x, y + yOffset, z) || block.isLeaves(world, x, y + yOffset, z)
						|| block.isReplaceable(world, x, y + yOffset, z))
					setBlock(world, x, y + yOffset, z, LiquidFuels.rubberWood, 1);
			}

			return true;

		}
		
		LiquidFuels.inst.logDebug("Tree failed to grow due to: Base can't sustain");

		return false;
	}

	private void setBlock(World world, int x, int y, int z, Block block, int meta) {
		world.setBlock(x, y, z, block, meta, doNotify ? 3 : 2);
	}

}
