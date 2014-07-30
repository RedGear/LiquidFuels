package redgear.liquidfuels.machines.molder;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;

public class TileEntityMolder extends TileEntityElectricFluidMachine{
	
	
	AdvFluidTank tank;
	
	TileEntityMolder(){
		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMap(LiquidFuels.plasticFluid, TransferRule.INPUT);
		addTank(tank);
	}

	@Override
	protected boolean doPreWork() {
		return false;
	}

	@Override
	protected int checkWork() {
		return 0;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean doPostWork() {
		return false;
	}

}
