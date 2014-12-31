package redgear.liquidfuels.world;

import java.util.Random;

import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.core.world.gen.VeinHelper;
import redgear.liquidfuels.core.LiquidFuels;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class OilShaleGenerator implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (rand.nextInt(8) == 0)
			VeinHelper.generateSphere(new WorldLocation(chunkX * 16 + rand.nextInt(16), rand.nextInt(64) + 8, chunkZ * 16 + rand.nextInt(16),
					world), new SimpleItem(LiquidFuels.oilShale), new SimpleItem(Blocks.stone), rand, 16);
	}

}
