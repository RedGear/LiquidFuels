package redgear.liquidfuels.plugins;

import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.liquidfuels.api.recipes.FermenterRecipe;
import redgear.liquidfuels.core.LiquidFuels;

public class FermenterRecipes implements IPlugin{

	@Override
	public String getName() {
		return "FermenterRecipes";
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
		FermenterRecipe.addFermenterRecipe(new FluidStack(LiquidFuels.mashFluid, 10), 5, 45, new FluidStack( LiquidFuels.stillageFluid, 10));
	}

	@Override
	public void postInit(ModUtils mod) {
		
	}

}
