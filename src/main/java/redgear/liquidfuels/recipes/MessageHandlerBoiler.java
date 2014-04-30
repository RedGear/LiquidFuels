package redgear.liquidfuels.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.imc.IMCMessageHandler;

public class MessageHandlerBoiler implements IMCMessageHandler{

	@Override
	public void handle(String value) {
		
	}

	@Override
	public void handle(NBTTagCompound tag) {
		FluidStack fuel = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("fuel"));
		int power = tag.getInteger("power");
		int work = tag.getInteger("work");
		FluidBoilerRecipe.addBoilerRecipe(fuel, work, power);
	}

	@Override
	public void handle(ItemStack value) {
		
	}

}
