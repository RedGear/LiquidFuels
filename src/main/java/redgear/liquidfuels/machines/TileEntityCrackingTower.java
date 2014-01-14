package redgear.liquidfuels.machines;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityFreeMachine;

public class TileEntityCrackingTower extends TileEntityFreeMachine {

	private final AdvFluidTank tank;
	private final int slotInput;
	private final int slotOutput;

	public TileEntityCrackingTower() {
		super(4);

		slotInput = addSlot(new TankSlot(this, 92, 21, false, -1)); //input
		slotOutput = addSlot(new TankSlot(this, 92, 49, true, 1)); //output

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMap(-1, TransferRule.OUTPUT);
		addTank(tank, 69, 13, 16, 60);
	}

	@Override
	protected void doPreWork() {
		emptyTank(slotInput, slotOutput, tank);
		ejectAllFluids();
	}

	@Override
	protected void checkWork() {
	}

	@Override
	protected void doPostWork() {
	}

}
