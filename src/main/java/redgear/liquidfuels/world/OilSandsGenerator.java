package redgear.liquidfuels.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class OilSandsGenerator implements IWorldGenerator {

	private final MineOilSands mine = new MineOilSands();

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		if (rand.nextInt(8) == 0)
			mine.generate(world, rand, chunkX, chunkZ);
	}

}
