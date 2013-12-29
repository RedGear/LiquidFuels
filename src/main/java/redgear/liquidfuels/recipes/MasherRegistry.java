package redgear.liquidfuels.recipes;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import redgear.core.util.ItemStackUtil;
import redgear.liquidfuels.api.recipes.IMasherManager;
import redgear.liquidfuels.api.recipes.MasherRecipe;

public class MasherRegistry implements IMasherManager {

	private static ArrayList<MasherRecipe> masherRecipes = new ArrayList();
	
	
	public boolean addMasherRecipe( MasherRecipe recipe){
		boolean output = false;
		if(masherRecipes.contains(recipe))
			output = true;
		
		masherRecipes.add(recipe);
		return output;
	}
	
	
	public boolean addMasherRecipe(ItemStack item, int water, int power, int work, int amount, Fluid output){
		return addMasherRecipe(new MasherRecipe(item, water, power, work, amount, output));
	}
	
	public MasherRecipe getMasherRecipe(ItemStack input){
		for(MasherRecipe recipe : masherRecipes){
			if(ItemStackUtil.areStacksEqual(recipe.item, input))
				return recipe;
		}
		return null;
	}
	
}
