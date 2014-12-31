package redgear.liquidfuels.machines.masher;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.InvSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityElectricFluidMachine;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.recipes.MasherRecipe;

public class TileEntityMasher extends TileEntityElectricFluidMachine {
	
	final AdvFluidTank waterTank;
	final AdvFluidTank biomassTank;

	FluidStack output = null;

	public TileEntityMasher() {
		super(8);

		addSlot(80, 39); //masher bottom
		addSlot(62, 21); //masher left
		addSlot(80, 21); //masher center
		addSlot(98, 21); //masher right

		waterTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(
				FluidRegistry.WATER, TransferRule.INPUT);
		biomassTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1,
				TransferRule.OUTPUT);
		
		for(InvSlot slot : this.getSlots())
			slot.setMachineRule(TransferRule.INPUT);

		addTank(waterTank);//, 11, 13, 16, 60
		addTank(biomassTank);//, 149, 13, 16, 60

		//mainProgressBar = addProgressBar(86, 59, 3, 20);
	}

	@Override
	public boolean doPreWork() {
		return ejectFluidAllSides(biomassTank);
	}

	@Override
	public int checkWork() {
		MasherRecipe currRecipe;
		ItemStack stack;
		for (int i = 0; i <= 3; i++) {
			stack = getStackInSlot(i);

			if (stack == null)
				continue;

			currRecipe = MasherRecipe.getMasherRecipe(new SimpleItem(stack));

			if (currRecipe != null && waterTank.canDrain(currRecipe.water)
					&& biomassTank.canFill(currRecipe.output, true)) {
				energyRate_$eq(currRecipe.power / currRecipe.work);
				decrStackSize(i, 1);
				waterTank.drain(currRecipe.water, true);// use water
				output = currRecipe.output;
				return currRecipe.work;
			}

		}

		return 0;
	}

	@Override
	public boolean doPostWork() {
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
	public boolean doWork() {
		return false;
	}
}
