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
	private final int steamRatio = 36; //1 water = 36 steam
	private final long powerRatio = 13507200;
	private int rate = 5; //use 5 water at a time

	public TileEntityBoiler() {
		super(4, 1407000000L);

		slotWaterFull = addSlot(new TankSlot(this, 63, 20, true, -1)); //water full
		slotWaterEmpty = addSlot(new TankSlot(this, 63, 48, false, 1)); //water empty

		waterTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		waterTank.addFluidMap(FluidRegistry.WATER, TransferRule.INPUT);
		addTank(waterTank, 40, 12, 16, 60);

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 16);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.OUTPUT);
		addTank(steamTank, 120, 12, 16, 60);
	}

	@Override
	protected void doPreWork() {
		fillTank(slotWaterFull, slotWaterEmpty, waterTank);
		ejectFluidAllSides(steamTank);
	}

	@Override
	protected void checkWork() {
		rate = (int) Math.min(getEnergyAmount() / powerRatio,
				Math.min(steamTank.getSpace() / steamRatio, waterTank.getAmount()));

		if (rate >= 1)
			addWork(1, powerRatio * rate);
	}

	@Override
	protected void doPostWork() {
		steamTank.fill(new FluidStack(LiquidFuels.steamFluid, steamRatio * rate), true);
		waterTank.drain(1, true);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("rate", rate);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		rate = tag.getInteger("rate");
	}

}
