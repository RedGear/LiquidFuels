package redgear.liquidfuels.api.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FermenterRecipe {

	private static Map<Integer, FermenterRecipe> recipes = new HashMap<Integer, FermenterRecipe>();

	public final FluidStack input;
	public final FluidStack output;
	public final int work;
	public final long power;

	public FermenterRecipe(FluidStack input, int work, long power, FluidStack output) {
		this.input = input;
		this.work = work;
		this.power = power;
		this.output = output;
	}

	@Override
	public int hashCode() {
		return input.fluidID;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof FermenterRecipe)
			return input.fluidID == ((FermenterRecipe) other).input.fluidID;
		else
			return false;
	}

	public static FermenterRecipe addFermenterRecipe(FermenterRecipe recipe) {
		return recipes.put(recipe.input.fluidID, recipe);
	}

	public static FermenterRecipe addFermenterRecipe(FluidStack input, int work, long power, FluidStack output) {
		if (input == null || input.amount <= 0 || output == null || output.amount <= 0)
			return null;
		else
			return addFermenterRecipe(new FermenterRecipe(input, work, power, output));
	}

	public static FermenterRecipe getFermenterRecipe(Fluid fluid) {
		return fluid == null ? null : getFermenterRecipe(fluid.getID());
	}

	public static FermenterRecipe getFermenterRecipe(FluidStack fluid) {
		return fluid == null ? null : getFermenterRecipe(fluid.fluidID);
	}

	public static FermenterRecipe getFermenterRecipe(int fluidId) {
		return recipes.get(fluidId);
	}

	public static Set<Integer> getFluidIds() {
		return recipes.keySet();
	}
}
