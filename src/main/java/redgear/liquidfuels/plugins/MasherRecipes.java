package redgear.liquidfuels.plugins;

import static redgear.liquidfuels.recipes.MasherRecipe.addMasherRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.api.item.ISimpleItem;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.recipes.MixerRecipe;
import cpw.mods.fml.common.LoaderState.ModState;

public class MasherRecipes implements IPlugin {

	@Override
	public String getName() {
		return "MasherRecipes";
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
		final int defaultOutput = 50; //based on forestry smallest value
		final int defaultTime = 50; //40 ticks, about 2 seconds.
		final int defaultEnergy = 450; //based on forestry
		final float defaultWaterRate = .8f; //multiply this by the fluid output to get water intput.
		final float defaultWater = defaultOutput * defaultWaterRate; //based on defaultOutput and defaultWaterRate

		addMasher(new SimpleOre("treeSapling"), 250 * defaultWaterRate, 2250, defaultTime, LiquidFuels.biomassFluid,
				250);

		addMasher(Items.wheat, defaultWater, defaultEnergy, defaultTime, LiquidFuels.biomassFluid, defaultOutput);
		addMasher(new SimpleItem(Items.reeds), defaultWater, defaultEnergy, defaultTime, LiquidFuels.biomassFluid,
				defaultOutput);
		addMasher(new SimpleItem(Blocks.cactus), 20, defaultEnergy, defaultTime, LiquidFuels.biomassFluid,
				defaultOutput);
		addMasher(new SimpleItem(Blocks.red_mushroom), defaultWater, defaultEnergy, defaultTime,
				LiquidFuels.biomassFluid, defaultOutput);
		addMasher(new SimpleItem(Blocks.brown_mushroom), defaultWater, defaultEnergy, defaultTime,
				LiquidFuels.biomassFluid, defaultOutput);

		addMasher(Items.nether_wart, defaultWater * 3, defaultEnergy, defaultTime + 20, LiquidFuels.biomassFluid,
				defaultOutput);

		addMasher(Items.carrot, defaultWater * 2, defaultEnergy * 2, defaultTime, LiquidFuels.biomassFluid,
				(int) (defaultOutput * 1.5f));
		addMasher(Items.potato, defaultWater * 2, defaultEnergy * 2, defaultTime, LiquidFuels.biomassFluid,
				(int) (defaultOutput * 1.5f));
		addMasher(Items.melon, 20, defaultEnergy / 2, defaultTime / 3, LiquidFuels.biomassFluid, defaultOutput / 3);

		addMasher(new SimpleOre("treeLeaves"), 250 * defaultWaterRate, 225, defaultTime, LiquidFuels.biomassFluid, 25);

		String[] dyes = {"Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink",
				"Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };

		for (int i = 0; i < 16; i++)
			addDye("dye" + dyes[i], ItemDye.field_150922_c[i]);
	}

	@Override
	public void postInit(ModUtils mod) {

	}

	private void addDye(String name, int color) {
		ISimpleItem item = new SimpleOre(name);
		FluidStack dye = new FluidStack(LiquidFuels.dyeFluid, 1000);
		dye.tag = new NBTTagCompound();
		dye.tag.setInteger("color", color);
		addMasherRecipe(item, 100, 450, 50, dye);
		
		dye = dye.copy();
		dye.amount = 100;

		for (int startColor : ItemDye.field_150922_c)
			if (color != startColor) {
				FluidStack plastic = new FluidStack(LiquidFuels.plasticFluid, 100);
				plastic.tag = new NBTTagCompound();
				plastic.tag.setInteger("color", color);

				FluidStack startPlastic = new FluidStack(LiquidFuels.plasticFluid, 100);
				startPlastic.tag = new NBTTagCompound();
				startPlastic.tag.setInteger("color", startColor);

				MixerRecipe.addRecipe(dye, startPlastic, plastic);
			}
	}

	private void addMasher(Item item, float water, int power, int work, Fluid fluid, int amount) {
		addMasher(new SimpleItem(item), water, power, work, fluid, amount);
	}

	private void addMasher(ISimpleItem item, float water, int power, int work, Fluid fluid, int amount) {
		addMasherRecipe(item, (int) water, power, work, new FluidStack(fluid, amount));
	}

}
