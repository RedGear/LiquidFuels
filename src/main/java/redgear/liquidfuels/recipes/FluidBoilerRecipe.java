package redgear.liquidfuels.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraftforge.fluids.FluidStack;

public class FluidBoilerRecipe {

	private static Map<Integer, FluidBoilerRecipe> recipes = new HashMap<Integer, FluidBoilerRecipe>();
	
	public final FluidStack fuel;
	public final int work;
	public final int power;

	private FluidBoilerRecipe(FluidStack fuel, int work, int power) {
		this.fuel = fuel;
		this.work = work;
		this.power = power;
	}
	
	public static void addBoilerRecipe(FluidStack fuel, int work, int power){
		if(fuel != null && work > 0 && power > 0)
			recipes.put(fuel.fluidID, new FluidBoilerRecipe(fuel, work, power));
	}
	
	public static FluidBoilerRecipe getBoilerRecipe(FluidStack fuel){
		if(fuel == null)
			return null;
		else
			return  recipes.get(fuel.fluidID);
	}
	
	public static Set<Integer> getFluidIds(){
		return recipes.keySet();
	}

}
