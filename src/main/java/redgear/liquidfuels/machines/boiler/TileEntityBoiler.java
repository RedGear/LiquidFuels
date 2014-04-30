package redgear.liquidfuels.machines.boiler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;

public class TileEntityBoiler extends TileEntityElectricFluidMachine {

	public final AdvFluidTank waterTank;
	public final AdvFluidTank steamTank;
	final int slotWaterFull;
	final int slotWaterEmpty;
	public final int workRate = 10;
	public final int waterRate = 4; // amount of water needed for each cycle
	public final int steamRate = 160; // amount of steam made each cycle
	public final int powerRatio = 160; // energy per mb of steam

	public TileEntityBoiler() {
		super(8);

		slotWaterFull = addSlot(new TankSlot(this, 63, 20, true, -1)); //water full
		slotWaterEmpty = addSlot(new TankSlot(this, 63, 48, false, 1)); //water empty

		waterTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		waterTank.addFluidMap(FluidRegistry.WATER, TransferRule.INPUT);
		addTank(waterTank);//, 40, 12, 16, 60

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.OUTPUT);
		addTank(steamTank);//, 120, 12, 16, 60
		
		this.setEnergyRate(powerRatio);
	}

	@Override
	protected boolean doPreWork() {
		return fillTank(slotWaterFull, slotWaterEmpty, waterTank) || ejectFluidAllSides(steamTank);
	}

	@Override
	protected int checkWork() {
		if(steamTank.getCapacity() >= steamRate * workRate && waterTank.getAmount() >= waterRate * workRate)
			return workRate;
			
		return 0;
	}
	
	@Override
	protected boolean doWork() {
		waterTank.drain(waterRate, true);
		steamTank.fill(new FluidStack(LiquidFuels.steamFluid, steamRate), true);
		return true;
	}

	@Override
	protected boolean doPostWork() {
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
