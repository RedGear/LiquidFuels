package redgear.liquidfuels.plugins;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.event.FMLInterModComms;

public class ThermalExpansionPlugin implements IPlugin {
	
	@Override
	public String getName() {
		return "Thermal Expansion Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state){
		return Mods.ThermalExpansion.isIn();
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
		compressionFuel(LiquidFuels.keroseneFluid, 4800000);
		compressionFuel(LiquidFuels.gasolineFluid, 5200000);
		compressionFuel(LiquidFuels.dieselFluid, 30000000);
	}

	@Override
	public void postInit(ModUtils inst) {

	}

	private void compressionFuel(Fluid fluid, int value) {
		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setString("fluidName", fluid.getName());
		toSend.setInteger("energy", value);
		FMLInterModComms.sendMessage("ThermalExpansion", "CompressionFuel", toSend);
	}
}
