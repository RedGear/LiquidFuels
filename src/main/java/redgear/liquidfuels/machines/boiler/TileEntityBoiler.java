package redgear.liquidfuels.machines.boiler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityElectricFluidMachine;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityBoiler extends TileEntityElectricFluidMachine {

	public final AdvFluidTank waterTank;
	public final AdvFluidTank steamTank;
	public final int workRate = 10;
	public final int waterRate = 4; // amount of water needed for each cycle
	public final int steamRate = 160; // amount of steam made each cycle
	public final int powerRatio = 160; // energy per mb of steam

	public TileEntityBoiler() {
		super(8);

		waterTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		waterTank.addFluidMap(FluidRegistry.WATER, TransferRule.INPUT);
		addTank(waterTank);//, 40, 12, 16, 60

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.OUTPUT);
		addTank(steamTank);//, 120, 12, 16, 60
		
		this.energyRate_$eq(powerRatio);
	}

	@Override
	public boolean doPreWork() {
		return ejectFluidAllSides(steamTank);
	}

	@Override
	public int checkWork() {
		if(steamTank.getCapacity() >= steamRate * workRate && waterTank.getAmount() >= waterRate * workRate)
			return workRate;
			
		return 0;
	}
	
	@Override
	public boolean doWork() {
		waterTank.drain(waterRate, true);
		steamTank.fill(new FluidStack(LiquidFuels.steamFluid, steamRate), true);
		return true;
	}

	@Override
	public boolean doPostWork() {
		return false;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
	}
}
