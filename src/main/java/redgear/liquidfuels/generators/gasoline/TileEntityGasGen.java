package redgear.liquidfuels.generators.gasoline;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.world.WorldLocation;
import redgear.liquidfuels.core.LiquidFuels;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.tileentity.IEnergyInfo;

public class TileEntityGasGen extends TileEntityTank implements IEnergyHandler, IEnergyInfo {

	final AdvFluidTank tank;
	final EnergyStorage energy = new EnergyStorage(500000);
	final int ticksPerMiliBucket = 20;
	final int energyPerTick = 80;
	final int maxHeat = 40;
	int heatUp = 0;

	public TileEntityGasGen() {
		super(20);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);

		tank.addFluidMap(LiquidFuels.gasolineFluid, TransferRule.INPUT);
		addTank(tank);
	}

	@Override
	public boolean doPreWork() {
		boolean check = false;

		if (work() == 0) {
			heatUp--;
			check = true;
		}

		return check || exportEnergy();
	}

	@Override
	public int checkWork() {
		if (tank.getAmount() > 0 && energy.getEnergyStored() < energy.getMaxEnergyStored()) {
			tank.drain(1, true);
			return ticksPerMiliBucket;
		} else
			return 0;
	}

	@Override
	public boolean doWork() {
		if (heatUp++ >= maxHeat) {
			heatUp = maxHeat; // Don't let it get any higher.
			energy.receiveEnergy(energyPerTick, false);
		}

		exportEnergy();
		return true;
	}

	protected boolean exportEnergy() {
		boolean check = false;

		if (energy.getEnergyStored() > 0) {
			WorldLocation loc = new WorldLocation(this);

			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
				TileEntity tile = loc.translate(side, 1).getTile();

				if (tile != null && tile instanceof IEnergyHandler) {
					IEnergyHandler handler = (IEnergyHandler) tile;

					check |= energy.extractEnergy(
							handler.receiveEnergy(side.getOpposite(), energy.getEnergyStored(), false), false) > 0;
				}

			}

		}

		return check;
	}

	@Override
	public boolean doPostWork() {
		return false;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energy.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energy.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energy.getMaxEnergyStored();
	}

	@Override
	public int getInfoEnergyPerTick() {
		return work() > 0 ? energyPerTick : 0;
	}

	@Override
	public int getInfoMaxEnergyPerTick() {
		return energyPerTick;
	}

	@Override
	public int getInfoEnergyStored() {
		return energy.getEnergyStored();
	}

	@Override
	public int getInfoMaxEnergyStored() {
		return energy.getMaxEnergyStored();
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		energy.writeToNBT(tag);
		tag.setInteger("heatUp", heatUp);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energy.readFromNBT(tag);
		heatUp = tag.getInteger("heatUp");
	}

}
