package redgear.liquidfuels.machines.dryer;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.InvSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.liquidfuels.recipes.DryerRecipe;

public class TileEntityDryer extends TileEntityTank {

	final AdvFluidTank tank;
	static final int workCycle = 100;

	public TileEntityDryer() {
		super(10);
		addSlot(97, 31); //dryer top left
		addSlot(115, 31); //dryer top middle
		addSlot(133, 31); //dryer top right
		addSlot(97, 49); //dryer bottom left
		addSlot(115, 49); //dryer bottom middle
		addSlot(133, 49); //dryer bottom right
		
		for(InvSlot slot : this.getSlots())
			slot.setRules(TransferRule.OUTPUT);

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMapIds(DryerRecipe.getFluidIds(), TransferRule.INPUT);
		addTank(tank);
	}

	@Override
	public boolean doPreWork() {
		return false;
	}

	@Override
	public int checkWork() {
		ItemStack out = DryerRecipe.getOutput(tank, false);
		
		if (out != null && out.stackSize > 0 && this.canAddStack(out)) 
			return workCycle;
		else
			return 0;
	}

	@Override
	public boolean doPostWork() {
		addStack(DryerRecipe.getOutput(tank, true));
		return true;
	}

	@Override
	public boolean doWork() {
		return false;
	}
}
