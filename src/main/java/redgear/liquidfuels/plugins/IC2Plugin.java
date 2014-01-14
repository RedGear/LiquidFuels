package redgear.liquidfuels.plugins;

import ic2.api.recipe.Recipes;
import net.minecraftforge.fluids.Fluid;
import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.liquidfuels.core.LiquidFuels;

public class IC2Plugin implements IPlugin {

	@Override
	public void preInit(ModUtils inst) {

	}

	@Override
	public void Init(ModUtils inst) {
		if (Mods.IC2.isIn()) {
			fluidGen(LiquidFuels.keroseneFluid, 2, 16);
			fluidGen(LiquidFuels.gasolineFluid, 1, 13);
			fluidGen(LiquidFuels.dieselFluid, 2, 20);
		}

	}

	@Override
	public void postInit(ModUtils inst) {

	}

	private void fluidGen(Fluid fluid, int amount, double power) {
		Recipes.semiFluidGenerator.addFluid(fluid.getName(), amount, power);
	}
}
