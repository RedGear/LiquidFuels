package redgear.liquidfuels.machines.masher;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;
import redgear.liquidfuels.recipes.MasherRecipe;

public class TileEntityMasher extends TileEntityElectricFluidMachine {
	final AdvFluidTank waterTank;
	final AdvFluidTank biomassTank;

	final int slotWaterFull;
	final int slotWaterEmpty;

	final int slotBiomassEmpty;
	final int slotBiomassFull;

	FluidStack output = null;

	public TileEntityMasher() {
		super(8);

		addSlot(80, 39); //masher bottom
		addSlot(62, 21); //masher left
		addSlot(80, 21); //masher center
		addSlot(98, 21); //masher right
		slotWaterFull = addSlot(new TankSlot(this, 34, 21, true, -1)); //water full
		slotWaterEmpty = addSlot(new TankSlot(this, 34, 49, false, 1)); //water empty
		slotBiomassEmpty = addSlot(new TankSlot(this, 126, 21, false, -1)); //biomass empty
		slotBiomassFull = addSlot(new TankSlot(this, 126, 49, true, 1)); //biomass full

		waterTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(
				FluidRegistry.WATER, TransferRule.INPUT);
		biomassTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1,
				TransferRule.OUTPUT);

		addTank(waterTank);//, 11, 13, 16, 60
		addTank(biomassTank);//, 149, 13, 16, 60

		//mainProgressBar = addProgressBar(86, 59, 3, 20);
	}

	@Override
	protected boolean doPreWork() {
		boolean check = false;
		check |= fillTank(slotWaterFull, slotWaterEmpty, waterTank);
		check |= emptyTank(slotBiomassEmpty, slotBiomassFull, biomassTank);
		check |= ejectFluidAllSides(biomassTank);
		return check;
	}

	@Override
	protected int checkWork() {
		MasherRecipe currRecipe;
		ItemStack stack;
		for (int i = 0; i <= 3; i++) {
			stack = getStackInSlot(i);

			if (stack == null)
				continue;

			currRecipe = MasherRecipe.getMasherRecipe(new SimpleItem(stack));

			if (currRecipe != null && waterTank.canDrain(currRecipe.water)
					&& biomassTank.canFill(currRecipe.output, true)) {
				setEnergyRate(currRecipe.power / currRecipe.work);
				decrStackSize(i, 1);
				waterTank.drain(currRecipe.water, true);// use water
				output = currRecipe.output;
				return currRecipe.work;
			}

		}

		return 0;
	}

	@Override
	protected boolean doPostWork() {
		biomassTank.fill(output, true);
		output = null;
		return true;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		writeFluidStack(tag, "output", output);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		output = readFluidStack(tag, "output");
	}

	@Override
	protected boolean doWork() {
		return false;
	}
}
