package redgear.liquidfuels.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public interface IMasherManager {

	public boolean addMasherRecipe(MasherRecipe recipe);
	
	public boolean addMasherRecipe(ItemStack item, int water, int power, int work, int amount, Fluid output);
	
	public MasherRecipe getMasherRecipe(ItemStack input);
}