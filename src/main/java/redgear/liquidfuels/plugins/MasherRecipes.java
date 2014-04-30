package redgear.liquidfuels.plugins;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.LoaderState.ModState;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.recipes.MasherRecipe;

public class MasherRecipes implements IPlugin{

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
		
		List<ItemStack> items = OreDictionary.getOres("treeSapling"); //            water   power   work output amount
		for (ItemStack sapling : items)
			addMasher(new SimpleItem(sapling), 250 * defaultWaterRate, 2250, defaultTime, LiquidFuels.biomassFluid, 250);

		addMasher(Items.wheat, defaultWater, defaultEnergy, defaultTime, LiquidFuels.biomassFluid, defaultOutput);
		addMasher(new SimpleItem(Items.reeds), defaultWater, defaultEnergy, defaultTime, LiquidFuels.biomassFluid, defaultOutput);
		addMasher(new SimpleItem(Blocks.cactus), 20, defaultEnergy, defaultTime, LiquidFuels.biomassFluid, defaultOutput);
		addMasher(new SimpleItem(Blocks.red_mushroom), defaultWater, defaultEnergy, defaultTime, LiquidFuels.biomassFluid, defaultOutput); 
		addMasher(new SimpleItem(Blocks.brown_mushroom), defaultWater, defaultEnergy, defaultTime, LiquidFuels.biomassFluid, defaultOutput); 		
				
		addMasher(Items.nether_wart, defaultWater * 3, defaultEnergy, defaultTime + 20, LiquidFuels.biomassFluid, defaultOutput);
		
		addMasher(Items.carrot, defaultWater * 2, defaultEnergy * 2, defaultTime, LiquidFuels.biomassFluid, (int) (defaultOutput * 1.5f));
		addMasher(Items.potato, defaultWater * 2, defaultEnergy * 2, defaultTime, LiquidFuels.biomassFluid, (int) (defaultOutput * 1.5f));
		addMasher(Items.melon, 20, defaultEnergy / 2, defaultTime / 3, LiquidFuels.biomassFluid, defaultOutput / 3);
		
		
		items = OreDictionary.getOres("treeLeaves");
		for (ItemStack leaves : items)
			addMasher(new SimpleItem(leaves), 250 * defaultWaterRate, 225, defaultTime, LiquidFuels.biomassFluid, 25);

	}

	@Override
	public void postInit(ModUtils mod) {
		
	}
	
	private void addMasher(Item item, float water, int power, int work, Fluid fluid, int amount) {
		addMasher(new SimpleItem(item), water, power, work, fluid, amount);
	}

	private void addMasher(SimpleItem item, float water, int power, int work, Fluid fluid, int amount) {
		MasherRecipe.addMasherRecipe(item, (int)water, power, work, new FluidStack(fluid, amount));
	}

}
