package redgear.liquidfuels.machines.mixer;

import static redgear.liquidfuels.recipes.MixerRecipe.getRecipe;
import static redgear.liquidfuels.recipes.MixerRecipe.getRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;
import redgear.liquidfuels.recipes.MixerRecipe;

public class TileEntityMixer extends TileEntityElectricFluidMachine {

	final AdvFluidTank firstInput;
	final AdvFluidTank secondInput;
	final AdvFluidTank output;

	private FluidStack inProgress;

	public TileEntityMixer() {
		super(20);

		firstInput = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		addTank(firstInput);

		secondInput = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		addTank(secondInput);

		output = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		output.addFluidMap(-1, TransferRule.OUTPUT);
		addTank(output);

		for (MixerRecipe recipe : getRecipes()) {
			firstInput.addFluidMap(recipe.firstInput.fluidID, TransferRule.INPUT);
			secondInput.addFluidMap(recipe.secondInput.fluidID, TransferRule.INPUT);
		}
		
		this.setEnergyRate(10);
	}

	@Override
	protected boolean doPreWork() {
		return false;
	}

	@Override
	protected int checkWork() {
		if(firstInput.isEmpty() || secondInput.isEmpty())
			return 0;
		
		
		MixerRecipe recipe = getRecipe(firstInput.getFluid(), secondInput.getFluid());

		if (recipe != null)
			if (firstInput.getFluid().containsFluid(recipe.firstInput)
					&& secondInput.getFluid().containsFluid(recipe.secondInput) && output.canFill(recipe.output, true)) {
				firstInput.drain(recipe.firstInput.amount, true);
				secondInput.drain(recipe.secondInput.amount, true);
				inProgress = recipe.output;
				return 40;
			}

		return 0;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean doPostWork() {
		output.fill(inProgress, true);
		return true;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if(inProgress != null)
			inProgress.writeToNBT(tag);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		inProgress = FluidStack.loadFluidStackFromNBT(tag);
	}

}
