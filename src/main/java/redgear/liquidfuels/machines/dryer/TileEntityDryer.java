package redgear.liquidfuels.machines.dryer;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.util.ItemStackUtil;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityDryer extends TileEntityTank {

	final AdvFluidTank tank;

	static final int fluidRate = 1000;
	static final int workCycle = 10;
	final int slotInput;
	final int slotOutput;

	public TileEntityDryer() {
		super(10);

		slotInput = addSlot(new TankSlot(this, 56, 21, true, -1)); //fill
		slotOutput = addSlot(new TankSlot(this, 56, 49, false, 1)); //empty
		addSlot(97, 31); //dryer top left
		addSlot(115, 31); //dryer top middle
		addSlot(133, 31); //dryer top right
		addSlot(97, 49); //dryer bottom left
		addSlot(115, 49); //dryer bottom middle
		addSlot(133, 49); //dryer bottom right

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMap(LiquidFuels.petroleumCokeFluid, TransferRule.INPUT);
		addTank(tank);//, 33, 13, 16, 60

		//mainProgressBar = addProgressBar(24, 13, 3, 60);
	}

	@Override
	protected boolean doPreWork() {
		return fillTank(slotInput, slotOutput, 0);
	}

	@Override
	protected int checkWork() {
		if (tank.getAmount() > fluidRate && canAddStack(LiquidFuels.ptCoke.getStack(1))) {
			tank.drain(fluidRate, true);
			return workCycle;
		}
		return 0;
	}

	@Override
	protected boolean doPostWork() {
		ItemStackUtil.dropItemStack(worldObj, xCoord, yCoord, zCoord, addStack(LiquidFuels.ptCoke.getStack(1))); //Throw it on the ground if it doesn't fit
		return true;
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
