package redgear.liquidfuels.recipes;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.api.item.ISimpleItem;
import redgear.liquidfuels.core.LiquidFuels;

public class MolderRecipe {

	private static final List<MolderRecipe> recipes = new LinkedList<MolderRecipe>();

	public final ISimpleItem mold;
	public final FluidStack fluid;
	public final ItemStack output;

	private MolderRecipe(ISimpleItem mold, FluidStack fluid, ItemStack output) {
		this.mold = mold;
		this.fluid = fluid;
		this.output = output;
		recipes.add(this);
	}

	public static void addRecipe(ISimpleItem mold, FluidStack fluid, ItemStack output) {
		if (!isValidRecipe(mold, fluid)) //If this recipe does't already exist.
			new MolderRecipe(mold, fluid, output);
	}

	public static boolean isValidRecipe(ISimpleItem mold, FluidStack fluid) {
		return getRecipe(mold, fluid) != null;
	}

	public static MolderRecipe getRecipe(ISimpleItem mold, FluidStack fluid) {

		if (fluid == null || fluid.amount <= 0 || mold == null){
			LiquidFuels.inst.logDebug("Something was null");
			return null;
		}

		for (MolderRecipe r : recipes){
			LiquidFuels.inst.logDebug("Tag is null: ", r.fluid.tag == null);
			
			if(r.fluid.tag == null)
				LiquidFuels.inst.logDebug("Fluid Ids match: ", r.fluid.fluidID == fluid.fluidID, " Fluid Amounts work: ", fluid.amount >= r.fluid.amount);
			else
				LiquidFuels.inst.logDebug("Contains Fluid: ", fluid.containsFluid(r.fluid));
			
			
			LiquidFuels.inst.logDebug("Items equal: ", r.mold.isItemEqual(mold, false));
			
			
			if ((r.fluid.tag == null ? r.fluid.fluidID == fluid.fluidID && fluid.amount >= r.fluid.amount : fluid
					.containsFluid(r.fluid)) && r.mold.isItemEqual(mold, false))
				return r;
		}

		return null;
	}

	public static boolean isValidFluid(FluidStack fluid) {
		if (fluid == null || fluid.amount <= 0)
			return false;

		for (MolderRecipe r : recipes)
			if (fluid.tag == null ? r.fluid.fluidID == fluid.fluidID : r.fluid.isFluidEqual(fluid))
				return true;

		return false;
	}

	public static boolean isValidMold(ISimpleItem item) {
		if (item == null)
			return false;

		for (MolderRecipe r : recipes)
			if (r.mold.isItemEqual(item, false))
				return true;

		return false;
	}
}
