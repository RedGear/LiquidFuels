package redgear.liquidfuels.machines.fermenter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.liquidfuels.api.recipes.FermenterRecipe;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;

public class TileEntityFermenter extends TileEntityElectricFluidMachine {

	final AdvFluidTank inputTank;
	final AdvFluidTank outputTank;
	final int recipeInputSlot;
	final int recipeOutputSlot;
	final int stillageInputSlot;
	final int stillageOutputSlot;

	FluidStack output;

	public TileEntityFermenter() {
		super(10);

		recipeInputSlot = addSlot(new TankSlot(this, 50, 21, true, -1)); //recipe input
		recipeOutputSlot = addSlot(new TankSlot(this, 50, 49, false, 1)); //recipe output
		stillageInputSlot = addSlot(new TankSlot(this, 104, 21, false, -1)); //stillage input
		stillageOutputSlot = addSlot(new TankSlot(this, 104, 49, true, 1)); //stillage output

		inputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMapIds(
				FermenterRecipe.getFluidIds(), TransferRule.INPUT);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);

		addTank(inputTank);//, 27, 13, 16, 60
		addTank(outputTank);//, 132, 13, 16, 60

		//mainProgressBar = addProgressBar(20, 13, 3, 60);
	}

	@Override
	protected boolean doPreWork() {
		boolean check = false;
		check |= fillTank(recipeInputSlot, recipeOutputSlot, 0);
		check |= emptyTank(stillageInputSlot, stillageOutputSlot, 1);
		check |= ejectAllFluids();

		return check;
	}

	@Override
	protected int checkWork() {
		if (!inputTank.isEmpty()) {
			FermenterRecipe currRecipe = FermenterRecipe.getFermenterRecipe(inputTank.getFluid());

			if (currRecipe == null) //somehow the tank has something it shouldn't; get rid of it.
				inputTank.drain(inputTank.getCapacity(), true);
			else if (inputTank.canDrain(currRecipe.input, true) && outputTank.canFill(currRecipe.output, true)) {
				inputTank.drain(currRecipe.input.amount, true);
				output = currRecipe.output;
				setEnergyRate(currRecipe.power / currRecipe.work);
				return currRecipe.work;
			}
		}

		return 0;
	}

	@Override
	protected boolean doPostWork() {
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
	protected boolean doWork() {
		return false;
	}

}
