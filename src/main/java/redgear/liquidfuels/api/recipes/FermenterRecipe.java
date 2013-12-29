package redgear.liquidfuels.api.recipes;

import net.minecraftforge.fluids.FluidStack;

public class FermenterRecipe {

	public final FluidStack input;
	public final FluidStack output;
	public final int work;
	public final int power;
	
	public FermenterRecipe(FluidStack input, int work, int power, FluidStack output){
		this.input = input;
		this.work = work;
		this.power = power;
		this.output = output;
	}
	
	@Override
	public int hashCode(){
		return input.fluidID;
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof FermenterRecipe)
			return input.fluidID == ((FermenterRecipe) other).input.fluidID;
		else
			return false;
	}
}
