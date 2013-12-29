package redgear.liquidfuels.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

public class MasherRecipe {
	public final int power;
	public final int water;
	public final int work;
	public final int amount;
	public final ItemStack item;
	public final Fluid output;
	
	public MasherRecipe(ItemStack item, int water, int power, int work, int amount, Fluid output){
		this.item = item.copy();
		this.water = water;
		this.power = power;
		this.work = work;
		this.amount = amount;
		this.output = output;
	}
	
	@Override
	public int hashCode(){
		return item.hashCode();
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof MasherRecipe)
			return ItemStack.areItemStacksEqual(this.item, ((MasherRecipe) other).item);
		else
			return false;
	}
}
