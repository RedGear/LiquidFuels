package redgear.liquidfuels.plugins;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.recipes.FluidBoilerRecipe;
import cpw.mods.fml.common.LoaderState.ModState;

public class FluidBoilerPlugin implements IPlugin{

	@Override
	public String getName() {
		return "Fluid Boiler Recipes";
	}

	@Override
	public boolean shouldRun(ModUtils mod, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils mod) {
		
	}

	@Override
	public void Init(ModUtils mod) {
		FluidBoilerRecipe.addBoilerRecipe(new FluidStack(LiquidFuels.ethanolFluid, 	10), 30, 150);
		FluidBoilerRecipe.addBoilerRecipe(new FluidStack(LiquidFuels.oilFluid, 		10), 15, 100);
		FluidBoilerRecipe.addBoilerRecipe(new FluidStack(LiquidFuels.keroseneFluid, 10), 50, 500);
		FluidBoilerRecipe.addBoilerRecipe(new FluidStack(LiquidFuels.dieselFluid, 	10), 20, 400);
		FluidBoilerRecipe.addBoilerRecipe(new FluidStack(LiquidFuels.gasolineFluid, 20), 40, 500);
		
		FluidStack fuel = FluidRegistry.getFluidStack("fuel", 10);
		if(fuel != null)
			FluidBoilerRecipe.addBoilerRecipe(fuel, 20, 400);
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
