package redgear.liquidfuels.plugins;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.liquidfuels.core.LiquidFuels;
import buildcraft.api.fuels.IronEngineFuel;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.event.FMLInterModComms;

public class BuildcraftPlugin implements IPlugin {

	@Override
	public String getName() {
		return "Buildcraft Compatibility";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state) {
		return Mods.BCEnergy.isIn();
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
		int ethanolToFuelRate = 5;
		int fuelFromEthanolRate = 2;

		int seedOilToFuelRate = 20;
		int fuelFromSeedOilRate = 1;

		int creosoteToFuelRate = 20;
		int fuelFromCreosotelRate = 1;

		String cat = "refineryRecipes";

		ethanolToFuelRate = inst.getInt(cat, "EthanolToFuelRate",
				"The amount of Ethanol, in millibuckets, needed to be refined: default is " + ethanolToFuelRate,
				ethanolToFuelRate);
		fuelFromEthanolRate = inst.getInt(cat, "fuelFromEthanolRate",
				"The amount of Fuel, in millibuckets, made from each amount of Ethanol: default is "
						+ fuelFromEthanolRate, fuelFromEthanolRate);

		seedOilToFuelRate = inst.getInt(cat, "SeedOilToFuelRate",
				"The amount of Seed Oil, in millibuckets, needed to be refined: default is " + seedOilToFuelRate,
				seedOilToFuelRate);
		fuelFromSeedOilRate = inst.getInt(cat, "FuelFromSeedOilRate",
				"The amount of Fuel, in millibuckets, made from each amount of Seed Oil: default is "
						+ fuelFromSeedOilRate, fuelFromSeedOilRate);

		creosoteToFuelRate = inst.getInt(cat, "CreosoteToFuelRate",
				"The amount of Creosote, in millibuckets, needed to be refined: default is " + creosoteToFuelRate,
				creosoteToFuelRate);
		fuelFromCreosotelRate = inst.getInt(cat, "FuelFromCreosotelRate",
				"The amount of Fuel, in millibuckets, made from each amount of Creosote: default is "
						+ fuelFromCreosotelRate, fuelFromCreosotelRate);

		if (ethanolToFuelRate > 1 && fuelFromEthanolRate > 1)
			addRefineryRecipe(FluidRegistry.getFluidStack("bioethanol", ethanolToFuelRate),
					FluidRegistry.getFluidStack("fuel", fuelFromEthanolRate), 12, 1);

		if (fuelFromCreosotelRate > 1 && fuelFromSeedOilRate > 1)
			addRefineryRecipe(FluidRegistry.getFluidStack("seedoil", seedOilToFuelRate),
					FluidRegistry.getFluidStack("fuel", fuelFromSeedOilRate), 12, 1);

		if (creosoteToFuelRate > 1 && fuelFromCreosotelRate > 1)
			addRefineryRecipe(FluidRegistry.getFluidStack("Creosote Oil", creosoteToFuelRate),
					FluidRegistry.getFluidStack("fuel", fuelFromCreosotelRate), 12, 1);

		addRefineryRecipe(new FluidStack(LiquidFuels.dieselFluid, 1), FluidRegistry.getFluidStack("fuel", 1), 12, 1);
		addRefineryRecipe(new FluidStack(LiquidFuels.gasolineFluid, 1), FluidRegistry.getFluidStack("fuel", 1), 12, 1);
		addRefineryRecipe(new FluidStack(LiquidFuels.keroseneFluid, 1), FluidRegistry.getFluidStack("fuel", 1), 12, 1);

		IronEngineFuel.addFuel(LiquidFuels.keroseneFluid, 4, 47250);
		IronEngineFuel.addFuel(LiquidFuels.gasolineFluid, 8, 14766);
		IronEngineFuel.addFuel(LiquidFuels.dieselFluid, 4, 153563);

	}

	private void addRefineryRecipe(FluidStack input, FluidStack output, int energy, int delay) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("input", input.writeToNBT(new NBTTagCompound()));
		tag.setInteger("energy", energy);
		tag.setInteger("delay", delay);
		tag.setTag("output", output.writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage(Mods.BCEnergy.getId(), "add-refinery-recipe", tag);
	}

	@Override
	public void postInit(ModUtils inst) {

	}
}
