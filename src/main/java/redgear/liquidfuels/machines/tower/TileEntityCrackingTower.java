package redgear.liquidfuels.machines.tower;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;

public class TileEntityCrackingTower extends TileEntityTank {

	final AdvFluidTank tank;
	final int slotInput;
	final int slotOutput;

	public TileEntityCrackingTower() {
		super(40);

		slotInput = addSlot(new TankSlot(this, 92, 21, false, -1)); //input
		slotOutput = addSlot(new TankSlot(this, 92, 49, true, 1)); //output

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMap(-1, TransferRule.OUTPUT);
		addTank(tank);//, 69, 13, 16, 60
	}

	@Override
	protected boolean doPreWork() {
		return emptyTank(slotInput, slotOutput, tank) ||
		ejectAllFluids();
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
