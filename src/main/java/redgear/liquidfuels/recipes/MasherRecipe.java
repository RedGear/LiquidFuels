package redgear.liquidfuels.recipes;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.util.SimpleItem;

public class MasherRecipe {

	private static Set<MasherRecipe> recipes = new HashSet<MasherRecipe>();

	public final int power;
	public final int water;
	public final int work;
	public final SimpleItem item;
	public final FluidStack output;

	public MasherRecipe(SimpleItem item, int water, int power, int work, FluidStack output) {
		this.item = item;
		this.water = water;
		this.power = power;
		this.work = work;
		this.output = output;
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

	public static void addMasherRecipe(MasherRecipe recipe) {
		recipes.add(recipe);
	}

	public static void addMasherRecipe(ItemStack item, int water, int power, int work, Fluid output, int amount) {
		addMasherRecipe(new SimpleItem(item), water, power, work, new FluidStack(output, amount));
	}

	public static void addMasherRecipe(SimpleItem item, int water, int power, int work, Fluid output, int amount) {
		 addMasherRecipe(item, water, power, work, new FluidStack(output, amount));
	}

	public static void addMasherRecipe(ItemStack item, int water, int power, int work, FluidStack output) {
		 addMasherRecipe(new SimpleItem(item), water, power, work, output);
	}

	public static void addMasherRecipe(SimpleItem item, int water, int power, int work, FluidStack output) {
		if (item != null && water > 0 && work > 0 && output != null && output.amount > 0)
			 addMasherRecipe(new MasherRecipe(item, water, power, work, output));

	}

	public static MasherRecipe getMasherRecipe(SimpleItem input) {
		for (MasherRecipe entry : recipes)
			if (entry.item.equals(input))
				return entry;
		return null;
	}

}
