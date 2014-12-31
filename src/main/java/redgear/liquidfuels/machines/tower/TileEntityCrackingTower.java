package redgear.liquidfuels.machines.tower;

import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;

public class TileEntityCrackingTower extends TileEntityTank {

	final AdvFluidTank tank;

	public TileEntityCrackingTower() {
		super(40);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMap(-1, TransferRule.OUTPUT);
		addTank(tank);//, 69, 13, 16, 60
	}

	@Override
	public boolean doPreWork() {
		return false;
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

}
