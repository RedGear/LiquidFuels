package redgear.liquidfuels.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import redgear.core.mod.Mods;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.core.world.WorldLocation;
import redgear.geocraft.api.IMine;
import redgear.geocraft.api.MineManager;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;

@InterfaceList(value = {@Interface(iface = "redgear.geocraft.api.IMine", modid = "redgear_geocraft") })
public class MineOilShale implements IMine {
	
	@Method(modid = "redgear_geocraft")
	public static void register(){
		MineManager.oreRegistry.registerMine(new MineOilShale());
	}

	private static final boolean isGeo() {
		return Mods.Geocraft.isIn();
	}

	@Override
	public String getName() {
		return "OilSands";
	}

	@Override
	public float getMineRarity() {
		return 1 * (isGeo() ? rarityModifier() : 1);
	}
	
	@Method(modid = "redgear_geocraft")
	private float rarityModifier(){
		return MineManager.oreRegistry.rarityModifier();
	}

	@Override
	public float getMineSize() {
		return 6 * (isGeo() ? volumeModifier() : 1);
	}
	
	@Method(modid = "redgear_geocraft")
	private float volumeModifier(){
		return MineManager.oreRegistry.volumeModifier();
	}

	@Override
	public void generate(World world, Random rand, int chunkX, int chunkZ) {
		generateSphere(
				new WorldLocation(chunkX * 16 + rand.nextInt(16), rand.nextInt(64) + 8, chunkZ * 16 + rand.nextInt(16),
						world), new SimpleItem(LiquidFuels.oilShale), new SimpleItem(Blocks.stone), rand,
				(int) getMineSize());
	}

	public static boolean isInAir(World world, Location test) {
		return test.getY() >= world.getHeightValue(test.getX(), test.getZ());
	}

	public static boolean isInAir(WorldLocation test) {
		return isInAir(test.world, test);
	}

	public static void generateSphere(WorldLocation start, SimpleItem block, SimpleItem target, Random rand, int size) {
		if (isInAir(start))
			return;

		float f = rand.nextFloat() * (float) Math.PI;
		double d0 = start.getX() + 8 + MathHelper.sin(f) * size / 8.0F;
		double d1 = start.getX() + 8 - MathHelper.sin(f) * size / 8.0F;
		double d2 = start.getZ() + 8 + MathHelper.cos(f) * size / 8.0F;
		double d3 = start.getZ() + 8 - MathHelper.cos(f) * size / 8.0F;
		double d4 = start.getY() + rand.nextInt(3) - 2;
		double d5 = start.getY() + rand.nextInt(3) - 2;

		for (int l = 0; l <= size; ++l) {
			double d6 = d0 + (d1 - d0) * l / size;
			double d7 = d4 + (d5 - d4) * l / size;
			double d8 = d2 + (d3 - d2) * l / size;
			double d9 = rand.nextDouble() * size / 16.0D;
			double d10 = (MathHelper.sin(l * (float) Math.PI / size) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * (float) Math.PI / size) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

			for (int k2 = i1; k2 <= l1; ++k2) {
				double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D)
					for (int l2 = j1; l2 <= i2; ++l2) {
						double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D)
							for (int i3 = k1; i3 <= j2; ++i3) {
								double d14 = (i3 + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
									new Location(k2, l2, i3).placeBlock(start.world, block, target);
							}
					}
			}
		}
	}

}