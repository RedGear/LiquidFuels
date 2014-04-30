package redgear.liquidfuels.machines.fluidboiler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.liquidfuels.machines.boiler.TileEntityBoiler;
import redgear.liquidfuels.recipes.FluidBoilerRecipe;

public class TileEntityFluidBoiler extends TileEntityBoiler {

	final AdvFluidTank fuel;
	int energyGenRate;
	int energyTicks;

	public TileEntityFluidBoiler() {
		fuel = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
		
		for(Integer id : FluidBoilerRecipe.getFluidIds())
			fuel.addFluidMap(id, TransferRule.INPUT);
		
		addTank(fuel);
	}

	@Override
	protected boolean doPreWork() {
		boolean check = super.doPreWork();

		if (energyTicks > 0) {
			storage.receiveEnergy(energyGenRate, false);
			energyTicks--;
			check = true;
		} else if (storage.getEnergyStored() < powerRatio * workRate) {
				FluidBoilerRecipe recipe = FluidBoilerRecipe.getBoilerRecipe(fuel.getFluid());

			if (recipe != null)
					if (fuel.getFluid().containsFluid(recipe.fuel)) {
						fuel.drain(recipe.fuel.amount, true);
						energyGenRate = recipe.power;
						energyTicks = recipe.work;
					}
			}

		return check;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("energyGenRate", energyGenRate);
		tag.setInteger("energyTicks", energyTicks);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		energyGenRate = tag.getInteger("energyGenRate");
		energyTicks = tag.getInteger("energyTicks");
	}

	/**
	 * Add energy to an IEnergyHandler, internal distribution is left entirely
	 * to the IEnergyHandler.
	 *
	 * @param from
	 * Orientation the energy is received from.
	 * @param maxReceive
	 * Maximum amount of energy to receive.
	 * @param simulate
	 * If TRUE, the charge will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated)
	 * received.
	 */
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	/**
	 * Returns true if the Handler functions on a given side - if a Tile Entity
	 * can receive or send energy on a given side, this should return true.
	 */
	@Override
	public boolean canInterface(ForgeDirection from) {
		return false;
	}

}
