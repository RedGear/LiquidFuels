package redgear.liquidfuels.machines;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.tile.TileEntityMachine;

public class TileEntityWaterGenerator extends TileEntityMachine {
	
	private final AdvFluidTank tank;
	
	private final int waterInput;
	private final int waterOutput;

	public TileEntityWaterGenerator() {
		super(20);
		
		waterInput = addSlot(new TankSlot(this, 92, 21, false, -1)); //water empty
		waterOutput =  addSlot(new TankSlot(this, 92, 49, true, 1)); //water full
		
		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.setPressure(1);
        addTank(tank, 69, 13, 16, 60);
	}

	@Override
	protected void doPreWork() {
		emptyTank(waterInput, waterOutput, tank);
		ejectAllFluids();
		tank.fillOverride(new FluidStack(FluidRegistry.WATER, 4000), true);
	}

	@Override
	protected void checkWork() {}
	
	@Override
	protected boolean tryUseEnergy(int energy) {
		return true;
	}

	@Override
	protected void doPostWork() {
	}

	

}
