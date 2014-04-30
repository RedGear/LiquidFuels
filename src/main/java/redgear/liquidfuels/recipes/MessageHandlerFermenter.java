package redgear.liquidfuels.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.imc.IMCMessageHandler;

public class MessageHandlerFermenter implements IMCMessageHandler{

	@Override
	public void handle(String value) {
		
	}

	@Override
	public void handle(NBTTagCompound tag) {
		FluidStack input = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("input"));
		int power = tag.getInteger("power");
		int work = tag.getInteger("work");
		FluidStack output = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("output"));
		FermenterRecipe.addFermenterRecipe(input, work, power, output);
	}

	@Override
	public void handle(ItemStack value) {
		
	}

}
