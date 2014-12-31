package redgear.liquidfuels.machines.tap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.util.SimpleItem;
import redgear.core.world.WorldLocation;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityTap extends TileEntityTank {

	final AdvFluidTank tank;
	static final FluidStack latex = new FluidStack(LiquidFuels.latexFluid, 10);

	public TileEntityTap() {
		super(20);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME);
		tank.addFluidMap(LiquidFuels.latexFluid, TransferRule.OUTPUT);
		addTank(tank);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		direction_$eq(ForgeDirection.WEST);

		if (world.getBlock(x, y, z - 1) == LiquidFuels.rubberWood)
			direction_$eq(ForgeDirection.NORTH);

		if (world.getBlock(x + 1, y, z) == LiquidFuels.rubberWood)
			direction_$eq(ForgeDirection.EAST);

		if (world.getBlock(x, y, z + 1) == LiquidFuels.rubberWood)
			direction_$eq(ForgeDirection.SOUTH);

		if (world.getBlock(x - 1, y, z) == LiquidFuels.rubberWood)
			direction_$eq(ForgeDirection.WEST);

	}

	@Override
	public boolean doPreWork() {
		return false;
	}

	@Override
	public int checkWork() {
		return getLocation().translate(direction(), 1).getBlock() == LiquidFuels.rubberWood ? 80 : 0;
	}

	@Override
	public boolean doWork() {
		return false;
	}

	@Override
	public boolean tryUseEnergy(int energy) {
		return true;
	}

	@Override
	public boolean doPostWork() {
		if (tank.fill(latex, true) > 0 && worldObj.rand.nextInt(100) == 0)
			drainWood(getLocation().translate(direction(), 1));

		return true;
	}

	private boolean drainWood(WorldLocation next) {
		if (next.getBlock() == LiquidFuels.rubberWood) {
			if (drainWood(next.translate(ForgeDirection.UP, 1)))
				next.placeBlock(new SimpleItem(LiquidFuels.rubberWoodDrained));
			return false;
		} else
			return true;
	}
}
