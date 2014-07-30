package redgear.liquidfuels.fluid;

import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class ColoredFluid extends Fluid {

	public ColoredFluid(String fluidName) {
		super(fluidName);
	}
	
	@Override
	public int getColor() {
		return 0xF0F0F0;
	}

	@Override
	public int getColor(FluidStack stack) {
		if (stack == null || stack.tag == null || !stack.tag.hasKey("color"))
			return getColor();
		else
			return stack.tag.getInteger("color");
	}

	/**
	 * Returns the localized name of this fluid.
	 */
	@Override
	public String getLocalizedName(FluidStack stack) {
		return super.getLocalizedName(stack).replace("#", StatCollector.translateToLocal("tooltip.liquidfuels.color." + Integer.toHexString(getColor(stack))));
	}
}
