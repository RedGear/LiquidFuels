package redgear.liquidfuels.machines;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.render.ProgressBar;
import redgear.core.tile.TileEntityElectricMachine;
import redgear.liquidfuels.api.recipes.FermenterRecipe;

public class TileEntityFermenter extends TileEntityElectricMachine {

	private final AdvFluidTank inputTank;
	private final AdvFluidTank outputTank;
	private final int recipeInputSlot;
	private final int recipeOutputSlot;
	private final int stillageInputSlot;
	private final int stillageOutputSlot;
	private final int mainProgressBar;

	private FluidStack output;

	public TileEntityFermenter() {
		super(10, 562800L);

		recipeInputSlot = addSlot(new TankSlot(this, 50, 21, true, -1)); //recipe input
		recipeOutputSlot = addSlot(new TankSlot(this, 50, 49, false, 1)); //recipe output
		stillageInputSlot = addSlot(new TankSlot(this, 104, 21, false, -1)); //stillage input
		stillageOutputSlot = addSlot(new TankSlot(this, 104, 49, true, 1)); //stillage output

		inputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMapIds(FermenterRecipe.getFluidIds(), TransferRule.INPUT);
		outputTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);

		addTank(inputTank, 27, 13, 16, 60);
		addTank(outputTank, 132, 13, 16, 60);

		mainProgressBar = addProgressBar(20, 13, 3, 60);
	}

	@Override
	protected void doPreWork() {
		fillTank(recipeInputSlot, recipeOutputSlot, 0);
		emptyTank(stillageInputSlot, stillageOutputSlot, 1);
		ejectAllFluids();
	}

	@Override
	protected void checkWork() {
		if (!inputTank.isEmpty()) {
			FermenterRecipe currRecipe = FermenterRecipe.getFermenterRecipe(inputTank.getFluid());

			if (currRecipe == null) //somehow the tank has something it shouldn't; get rid of it. 
				inputTank.drain(inputTank.getCapacity(), true);
			else if (inputTank.canDrain(currRecipe.input)
					&& outputTank.canFill(currRecipe.output)) {
				inputTank.drain(currRecipe.input.amount, true);
				output = currRecipe.output;
				addWork(currRecipe.work, currRecipe.power);
			}
		}
	}

	@Override
	protected void doPostWork() {
		outputTank.fill(output, true);
	}

	@Override
	public ProgressBar updateProgressBars(ProgressBar prog) {
		if (prog.id == mainProgressBar) {
			prog.total = workTotal;
			prog.value = work;
		}

		return prog;
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

}
