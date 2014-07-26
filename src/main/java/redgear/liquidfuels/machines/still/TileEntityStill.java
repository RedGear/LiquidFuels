package redgear.liquidfuels.machines.still;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;

public class TileEntityStill extends TileEntityElectricFluidMachine {

	final AdvFluidTank steamTank;
	final AdvFluidTank stillageTank;
	final AdvFluidTank ethanolTank;
	final int steamRatio = 520; //How much steam is needed for each operation
	final int stillageRatio = 1; //How much stillage is needed for one mB of ethanol
	final int ethanolRatio = 100; //Amount of ethanol made each doWork.

	public TileEntityStill() {
		super(8);

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.INPUT);
		addTank(steamTank);//, 18, 13, 16, 60

		stillageTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		stillageTank.addFluidMap(LiquidFuels.stillageFluid, TransferRule.INPUT);
		addTank(stillageTank);//, 53, 13, 16, 60

		ethanolTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		ethanolTank.addFluidMap(LiquidFuels.ethanolFluid, TransferRule.OUTPUT);
		addTank(ethanolTank);//, 132, 13, 16, 60

		setEnergyRate(26);
	}

	@Override
	protected boolean doPreWork() {
		return ejectFluidAllSides(ethanolTank);
	}

	@Override
	protected int checkWork() {
		if (ethanolTank.canFill(ethanolRatio) && steamTank.canDrain(steamRatio)
				&& stillageTank.canDrain(stillageRatio * ethanolRatio))
			return 20;

		return 0;
	}

	@Override
	protected boolean doPostWork() {
		ethanolTank.fill(new FluidStack(LiquidFuels.ethanolFluid, ethanolRatio), true);
		steamTank.drain(steamRatio, true);
		stillageTank.drain(stillageRatio * ethanolRatio, true);
		return true;
	}

	@Override
	protected boolean doWork() {
		return false;
	}
}
