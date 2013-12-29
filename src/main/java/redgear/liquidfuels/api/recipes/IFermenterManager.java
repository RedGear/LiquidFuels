package redgear.liquidfuels.api.recipes;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public interface IFermenterManager {
	
	public boolean addFermenterRecipe(FermenterRecipe recipe);
	
	public boolean addFermenterRecipe(FluidStack input, int work, int power, FluidStack output);
	
	public FermenterRecipe getFermenterRecipe(Fluid fluid);
	
	public FermenterRecipe getFermenterRecipe(FluidStack fluid);
	
	public FermenterRecipe getFermenterRecipe(int fluidId);
	
	public int[] getFluids();
}
