package redgear.liquidfuels.plugins;

import net.minecraftforge.fluids.Fluid;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.event.FMLInterModComms;

public class RailcraftPlugin implements IPlugin {
	
	@Override
	public String getName() {
		return "Railcraft Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state) {
		return Mods.Railcraft.isIn();
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
		boilerFuel(LiquidFuels.keroseneFluid, 163800);
		boilerFuel(LiquidFuels.gasolineFluid, 6300);
		boilerFuel(LiquidFuels.dieselFluid, 31500);
	}

	private void boilerFuel(Fluid fluid, int value) {
		FMLInterModComms.sendMessage(Mods.Railcraft.getId(), "boiler-fuel-liquid", fluid.getName() + "@" + value);
	}

	@Override
	public void postInit(ModUtils inst) {

	}

}
