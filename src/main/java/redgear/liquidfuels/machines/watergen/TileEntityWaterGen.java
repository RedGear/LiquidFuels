package redgear.liquidfuels.machines.watergen;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;

public class TileEntityWaterGen extends TileEntityTank {

	final AdvFluidTank tank;

	final int waterInput;
	final int waterOutput;
	static final FluidStack water = new FluidStack(FluidRegistry.WATER, 4000);

	public TileEntityWaterGen() {
		super(20);

		waterInput = addSlot(new TankSlot(this, 92, 21, false, -1)); //water empty
		waterOutput = addSlot(new TankSlot(this, 92, 49, true, 1)); //water full

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);
		addTank(tank);//, 69, 13, 16, 60
	}

	@Override
	protected boolean doPreWork() {
		boolean check = false;
		check |= emptyTank(waterInput, waterOutput, tank);
		check |= ejectAllFluids();
		check |= tank.fill(water, true) > 0;
		return check;
	}

	@Override
	protected int checkWork() {
		return 0;
	}

	@Override
	protected boolean doPostWork() {
		return false;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		return true;
	}

}
