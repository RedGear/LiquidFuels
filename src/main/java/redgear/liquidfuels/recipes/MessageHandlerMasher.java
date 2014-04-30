package redgear.liquidfuels.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.imc.IMCMessageHandler;

public class MessageHandlerMasher implements IMCMessageHandler{

	@Override
	public void handle(String value) {
		
	}

	@Override
	public void handle(NBTTagCompound tag) {
		ItemStack item = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item"));
		int water = tag.getInteger("water");
		int power = tag.getInteger("power");
		int work = tag.getInteger("work");
		FluidStack output = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("output"));
		MasherRecipe.addMasherRecipe(item, water, power, work, output);
	}

	@Override
	public void handle(ItemStack value) {
		
	}

}
