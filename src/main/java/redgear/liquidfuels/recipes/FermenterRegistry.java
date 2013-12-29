package redgear.liquidfuels.recipes;

import java.util.ArrayList;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.liquidfuels.api.recipes.FermenterRecipe;
import redgear.liquidfuels.api.recipes.IFermenterManager;

public class FermenterRegistry implements IFermenterManager{
	
	private static ArrayList<FermenterRecipe> fermenterRecipes = new ArrayList();

	@Override
	public boolean addFermenterRecipe(FermenterRecipe recipe) {
		boolean output = false;
		if(fermenterRecipes.contains(recipe))
			output = true;
		
		fermenterRecipes.add(recipe);
		return output;
	}

	@Override
	public boolean addFermenterRecipe(FluidStack input, int work, int power, FluidStack output) {
		if(input == null || input.amount <= 0 || output == null || output.amount <= 0)
			return false;
		
		return addFermenterRecipe(new FermenterRecipe(input, work, power, output));
	}

	@Override
	public FermenterRecipe getFermenterRecipe(Fluid fluid) {
		return fluid == null ? null : getFermenterRecipe(fluid.getID());
	}
	
	@Override
	public FermenterRecipe getFermenterRecipe(FluidStack fluid) {
		return fluid == null ? null : getFermenterRecipe(fluid.fluidID);
	}

	@Override
	public FermenterRecipe getFermenterRecipe(int fluidId) {
		for(FermenterRecipe recipe : fermenterRecipes){
			if(recipe.input.fluidID == fluidId)
				return recipe;
		}
		return null;
	}

	@Override
	public int[] getFluids() {
		int size = fermenterRecipes.size();
		int[] fluids = new int[size];
		
		for(int i = 0; i < size; i++)
			fluids[i] = fermenterRecipes.get(i).input.fluidID;
		
		return fluids;
	}

}
