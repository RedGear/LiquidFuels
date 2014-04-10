package redgear.liquidfuels.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityElectricMachine;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityBoiler extends TileEntityElectricMachine {

	private final AdvFluidTank waterTank;
	private final AdvFluidTank steamTank;
	private final int slotWaterFull;
	private final int slotWaterEmpty;
	private final int waterRate = 4; // amount of water needed for each cycle
	private final int steamRate = 160; // amount of steam made each cycle
	private final int powerRatio = 80; // energy per mb of steam

	public TileEntityBoiler() {
		super(8);

		slotWaterFull = addSlot(new TankSlot(this, 63, 20, true, -1)); //water full
		slotWaterEmpty = addSlot(new TankSlot(this, 63, 48, false, 1)); //water empty

		waterTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		waterTank.addFluidMap(FluidRegistry.WATER, TransferRule.INPUT);
		addTank(waterTank, 40, 12, 16, 60);

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.OUTPUT);
		addTank(steamTank, 120, 12, 16, 60);
		
		this.setEnergyRate(powerRatio);
	}

	@Override
	protected void doPreWork() {
		fillTank(slotWaterFull, slotWaterEmpty, waterTank);
		ejectFluidAllSides(steamTank);
	}

	@Override
	protected void checkWork() {
		if(steamTank.getCapacity() >= steamRate){
			if(waterTank.getAmount() >= waterRate){
				addWork(1);
				waterTank.drain(waterRate, true);
			}
		}
	}

	@Override
	protected void doPostWork() {
		steamTank.fill(new FluidStack(LiquidFuels.steamFluid, steamRate), true);
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
