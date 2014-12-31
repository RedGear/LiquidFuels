package redgear.liquidfuels.machines.watergen;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;

public class TileEntityWaterGen extends TileEntityTank {

	final AdvFluidTank tank;

	static final FluidStack water = new FluidStack(FluidRegistry.WATER, 4000);

	public TileEntityWaterGen() {
		super(20);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);
		addTank(tank);//, 69, 13, 16, 60
	}

	@Override
	public boolean doPreWork() {
		boolean check = false;
		check |= ejectAllFluids();
		check |= tank.fill(water, true) > 0;
		return check;
	}

	@Override
	public int checkWork() {
		return 0;
	}

	@Override
	public boolean doPostWork() {
		return false;
	}

	@Override
	public boolean doWork() {
		return false;
	}

	@Override
	public boolean tryUseEnergy(int energy) {
		return true;
	}
}
