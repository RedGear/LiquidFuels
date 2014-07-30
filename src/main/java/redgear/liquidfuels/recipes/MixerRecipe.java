package redgear.liquidfuels.recipes;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import redgear.liquidfuels.core.LiquidFuels;

public class MixerRecipe {

	private static List<MixerRecipe> recipes = new LinkedList<MixerRecipe>();

	public final FluidStack firstInput, secondInput, output;

	static {
		
		FluidStack whitePlastic = new FluidStack(LiquidFuels.plasticFluid, 100);
		whitePlastic.tag = new NBTTagCompound();
		whitePlastic.tag.setInteger("color", 0xF0F0F0);
		
		addRecipe(new FluidStack(LiquidFuels.ethyleneFluid, 100), new FluidStack(LiquidFuels.propaneFluid, 50), whitePlastic);
		
		addRecipe(new FluidStack(LiquidFuels.isopreneFluid, 100), new FluidStack(LiquidFuels.propaneFluid, 50),
				new FluidStack(LiquidFuels.rubberFluid, 100));
		
		
	}

	private MixerRecipe(FluidStack firstInput, FluidStack secondInput, FluidStack output) {
		this.firstInput = firstInput;
		this.secondInput = secondInput;
		this.output = output;
	}

	public static boolean addRecipe(FluidStack firstInput, FluidStack secondInput, FluidStack output) {
		if (firstInput.isFluidEqual(secondInput)) //sanity check. You can't mix a fluid with itself.
			return false;

		if (recipeExists(firstInput, secondInput))
			return false;

		recipes.add(new MixerRecipe(firstInput, secondInput, output));
		return true;
	}

	public static FluidStack getOutput(FluidStack firstInput, FluidStack secondInput) {
		MixerRecipe recipe = getRecipe(firstInput, secondInput);

		if (recipe == null)
			return null;

		if (firstInput.containsFluid(recipe.firstInput) || firstInput.containsFluid(recipe.secondInput))
			if (secondInput.containsFluid(recipe.firstInput) || secondInput.containsFluid(recipe.secondInput))
				return recipe.output;

		return null;
	}

	public static MixerRecipe getRecipe(FluidStack firstInput, FluidStack secondInput) {
		if (firstInput.isFluidEqual(secondInput)) //sanity check. You can't mix a fluid with itself.
			return null;

		for (MixerRecipe recipe : recipes)
			if (firstInput.isFluidEqual(recipe.firstInput) || firstInput.isFluidEqual(recipe.secondInput))
				if (secondInput.isFluidEqual(recipe.firstInput) || secondInput.isFluidEqual(recipe.secondInput))
					return recipe;

		return null;
	}

	public static boolean recipeExists(FluidStack firstInput, FluidStack secondInput) {
		return getRecipe(firstInput, secondInput) != null;
	}

	public static List<MixerRecipe> getRecipes() {
		return recipes;
	}

}
