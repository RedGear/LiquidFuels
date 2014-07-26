package redgear.liquidfuels.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import redgear.liquidfuels.core.LiquidFuels;

public class DryerRecipe {

	private static Map<Integer, Pair<FluidStack, ItemStack>> recipes = new HashMap<Integer, Pair<FluidStack, ItemStack>>();
	
	static {
		addRecipe(new FluidStack(LiquidFuels.petroleumCokeFluid, 1000), LiquidFuels.ptCoke.getStack());
		addRecipe(new FluidStack(LiquidFuels.latexFluid, 1000), LiquidFuels.rawNaturalRubber.getStack());
		
	}
	
	
	public static void addRecipe(FluidStack fluid, ItemStack item){
		recipes.put(fluid.fluidID, new ImmutablePair<FluidStack, ItemStack>(fluid, item));
	}
	
	public static Set<Integer> getFluidIds(){
		return recipes.keySet();
	}
	
	public static boolean isValid(FluidStack fluid){
		FluidStack key = recipes.get(fluid.fluidID).getLeft();
		
		return fluid.containsFluid(key);
	}
	
	public static ItemStack getOutput(IFluidTank tank, boolean doDrain){
		if(tank == null || tank.getFluid() == null)
			return null;
		
		Pair<FluidStack, ItemStack> pair = recipes.get(tank.getFluid().fluidID);
		FluidStack key = pair.getLeft();
		
		if(tank.getFluid().containsFluid(key)){
			tank.drain(key.amount, doDrain);
			return pair.getRight();
		}
		else
			return null;
	}
}
