package redgear.liquidfuels.machines.molder;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;
import redgear.liquidfuels.recipes.MolderRecipe;

public class TileEntityMolder extends TileEntityElectricFluidMachine {

	final AdvFluidTank tank;
	final int moldSlot;
	final int outputSlot;
	ItemStack output;

	TileEntityMolder() {
		super(20);
		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMap(LiquidFuels.plasticFluid, TransferRule.INPUT);
		addTank(tank);

		moldSlot = this.addSlot(new MoldSlot(this, 92, 13).setMachineRule(TransferRule.NEITHER).setStackLimit(1));
		outputSlot = this.addSlot(92, 57);
		getSlot(outputSlot).setMachineRule(TransferRule.OUTPUT).setPlayerRule(TransferRule.OUTPUT);
	}

	@Override
	protected boolean doPreWork() {
		return false;
	}

	@Override
	protected int checkWork() {
		MolderRecipe recipe = MolderRecipe.getRecipe(new SimpleItem(getStackInSlot(moldSlot)), tank.getFluid());

		LiquidFuels.inst.logDebug("Recipe is null? = ", recipe == null);
		
		if (recipe != null)
			if (this.canAddStack(outputSlot, recipe.output)) {
				tank.drain(recipe.fluid.amount, true);
				output = recipe.output.copy();
				return 80;
			}

		return 0;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean doPostWork() {
		this.addStack(outputSlot, output);
		output = null;

		return false;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		if (output != null)
			output.writeToNBT(tag);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		output = ItemStack.loadItemStackFromNBT(tag);
	}

}
