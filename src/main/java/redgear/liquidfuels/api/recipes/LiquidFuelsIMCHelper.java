package redgear.liquidfuels.api.recipes;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class LiquidFuelsIMCHelper {

	public static void addMasherRecipe(ItemStack item, int water, int power, int work, FluidStack output){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("item", item.writeToNBT(new NBTTagCompound()));
		tag.setInteger("water", water);
		tag.setInteger("power", power);
		tag.setInteger("work", work);
		tag.setTag("output", output.writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("redgear_liquidfuels", "MasherRecipe", tag);
	}
	
	
	public static void addFermenterRecipe(FluidStack input, int work, int power, FluidStack output){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("input", input.writeToNBT(new NBTTagCompound()));
		tag.setInteger("power", power);
		tag.setInteger("work", work);
		tag.setTag("output", output.writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("redgear_liquidfuels", "FermenterRecipe", tag);
	}
	
	public static void addBoilerFuelRecipe(FluidStack fuel, int work, int power){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("fuel", fuel.writeToNBT(new NBTTagCompound()));
		tag.setInteger("power", power);
		tag.setInteger("work", work);
		FMLInterModComms.sendMessage("redgear_liquidfuels", "BoilerFuelRecipe", tag);
	}
}
