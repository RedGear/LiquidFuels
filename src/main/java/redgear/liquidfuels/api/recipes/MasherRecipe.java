package redgear.liquidfuels.api.recipes;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.api.item.ISimpleItem;
import redgear.core.api.item.SimpleItemFactory;
import redgear.liquidfuels.core.LiquidFuels;

public class MasherRecipe {

	private static Map<ISimpleItem, MasherRecipe> recipes = new HashMap<ISimpleItem, MasherRecipe>();

	public final long power;
	public final int water;
	public final int work;
	public final ISimpleItem item;
	public final FluidStack output;

	public MasherRecipe(ISimpleItem item, int water, long power, int work, FluidStack output) {
		this.item = item;
		this.water = water;
		this.power = power;
		this.work = work;
		this.output = output;
		//LiquidFuels.inst.logDebug("Adding Masher Recpie for item: ", item);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof MasherRecipe)
			return item.equals(((MasherRecipe) other).item);
		else
			return item.equals(other);
	}

	public static MasherRecipe addMasherRecipe(MasherRecipe recipe) {
		return recipes.put(recipe.item, recipe);
	}
	
	public static MasherRecipe addMasherRecipe(ItemStack item, int water, long power, int work, Fluid output, int amount) {
		return addMasherRecipe(SimpleItemFactory.create(item), water, power, work, new FluidStack(output, amount));
	}

	public static MasherRecipe addMasherRecipe(ISimpleItem item, int water, long power, int work, Fluid output, int amount) {
		return addMasherRecipe(item, water, power, work, new FluidStack(output, amount));
	}
	
	public static MasherRecipe addMasherRecipe(ItemStack item, int water, long power, int work, FluidStack output) {
		return addMasherRecipe(SimpleItemFactory.create(item), water, power, work, output);
	}

	public static MasherRecipe addMasherRecipe(ISimpleItem item, int water, long power, int work, FluidStack output) {
		if (item != null && water > 0 && work > 0 && output != null && output.amount > 0)
			return addMasherRecipe(new MasherRecipe(item, water, power, work, output));
		else
			return null;
	}

	public static MasherRecipe getMasherRecipe(ISimpleItem input) {
		//return recipes.get(input);
		for(Entry<ISimpleItem, MasherRecipe> entry : recipes.entrySet())
			if(entry.getKey().equals(input))
				return entry.getValue();
		return null;
	}

}
