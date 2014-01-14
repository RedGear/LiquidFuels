package redgear.liquidfuels.plugins;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.event.FMLInterModComms;

public class ThermalExpansionPlugin implements IPlugin {

	@Override
	public void preInit(ModUtils inst) {

	}

	@Override
	public void Init(ModUtils inst) {
		if (Mods.ThermalExpansion.isIn()) {
			compressionFuel(LiquidFuels.keroseneFluid, 4800000);
			compressionFuel(LiquidFuels.gasolineFluid, 5200000);
			compressionFuel(LiquidFuels.dieselFluid, 30000000);
		}
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
