package redgear.liquidfuels.machines.fermenter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityElectricFluidMachine;
import redgear.liquidfuels.recipes.FermenterRecipe;

public class TileEntityFermenter extends TileEntityElectricFluidMachine {

	final AdvFluidTank inputTank;
	final AdvFluidTank outputTank;

	FluidStack output;

	public TileEntityFermenter() {
		super(10);

		inputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMapIds(
				FermenterRecipe.getFluidIds(), TransferRule.INPUT);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);

		addTank(inputTank);//, 27, 13, 16, 60
		addTank(outputTank);//, 132, 13, 16, 60

		//mainProgressBar = addProgressBar(20, 13, 3, 60);
	}

	@Override
	public boolean doPreWork() {
		return ejectAllFluids();
	}

	@Override
	public int checkWork() {
		if (!inputTank.isEmpty()) {
			FermenterRecipe currRecipe = FermenterRecipe.getFermenterRecipe(inputTank.getFluid());

			if (currRecipe == null) //somehow the tank has something it shouldn't; get rid of it.
				inputTank.drain(inputTank.getCapacity(), true);
			else if (inputTank.canDrain(currRecipe.input, true) && outputTank.canFill(currRecipe.output, true)) {
				inputTank.drain(currRecipe.input.amount, true);
				output = currRecipe.output;
				energyRate_$eq(currRecipe.power / currRecipe.work);
				return currRecipe.work;
			}
		}

		return 0;
	}

	@Override
	public boolean doPostWork() {
		outputTank.fill(output, true);
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
