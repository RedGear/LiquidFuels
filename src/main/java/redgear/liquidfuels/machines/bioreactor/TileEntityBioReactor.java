package redgear.liquidfuels.machines.bioreactor;

import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityElectricFluidMachine;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.core.world.MultiBlockMap;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityBioReactor extends TileEntityElectricFluidMachine {
	final AdvFluidTank tank;
	final int workCycle = 7200;//length of work always the same for this machine (unlike some others)

	private static final MultiBlockMap multi;

	public TileEntityBioReactor() {
		super(60);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 24);

		tank.addFluidMap(LiquidFuels.biomassFluid, TransferRule.INPUT);
		tank.addFluidMap(LiquidFuels.mashFluid, TransferRule.OUTPUT);
		addTank(tank);//, 69, 13, 16, 60

		//mainProgressBar = addProgressBar(60, 13, 3, 60);
		
		this.energyRate_$eq(60);
	}

	static {
		multi = new MultiBlockMap(36);

		SimpleItem block = new SimpleItem(LiquidFuels.bioReactorMulit);
		SimpleItem air = new SimpleItem(Blocks.air, 0);

		for (int x = -1; x <= 1; x++)
			for (int z = -1; z <= 1; z++)
				for (int y = -4; y <= -1; y++)
					multi.addLocation(x, y, z, (y == -3 || y == -2) && x == 0 && z == 0 ? air : block);
	}

	@Override
	public boolean doPreWork() {
		boolean check = false;
		
		if (!checkMulitBlock()) {
			tank.drain(tank.getCapacity(), true); //if the tank isn't formed, destroy all contents.
			work_$eq(0);
			check = true;
		}

		check |= ejectAllFluids();
		return check;
	}
	
	@Override
	public int checkWork() {
		if (tank.canDrain(LiquidFuels.biomassFluid) && tank.isFull())
			return workCycle;
		else
			return 0;
	}
	
	@Override
	public boolean doWork() {
		return false;
	}

	@Override
	public boolean doPostWork() {
		tank.fill(new FluidStack(LiquidFuels.mashFluid, tank.drain(tank.getCapacity(), true).amount), true);
		return true;
	}

	private boolean checkMulitBlock() {
		return multi.check(worldObj, new Location(this));
	}
}
