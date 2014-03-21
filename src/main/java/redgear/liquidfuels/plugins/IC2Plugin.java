package redgear.liquidfuels.plugins;

import cpw.mods.fml.common.LoaderState.ModState;
import ic2.api.recipe.Recipes;
import net.minecraftforge.fluids.Fluid;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.liquidfuels.core.LiquidFuels;

public class IC2Plugin implements IPlugin {
	
	@Override
	public String getName() {
		return "IC2 Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state) {
		return Mods.IC2.isIn();
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void preInit(ModUtils inst) {

	}

	@Override
	public void Init(ModUtils inst) {
		fluidGen(LiquidFuels.keroseneFluid, 2, 16);
		fluidGen(LiquidFuels.gasolineFluid, 1, 13);
		fluidGen(LiquidFuels.dieselFluid, 2, 20);
	}

	@Override
	public void postInit(ModUtils inst) {

	}

	private void fluidGen(Fluid fluid, int amount, double power) {
		Recipes.semiFluidGenerator.addFluid(fluid.getName(), amount, power);
	}
}
