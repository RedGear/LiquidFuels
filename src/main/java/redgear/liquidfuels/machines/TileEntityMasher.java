package redgear.liquidfuels.machines;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.render.ProgressBar;
import redgear.core.tile.TileEntityElectricMachine;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.api.recipes.MasherRecipe;

public class TileEntityMasher extends TileEntityElectricMachine {
	public final int waterTank;
	public final int biomassTank;

	private final int slotWaterFull;
	private final int slotWaterEmpty;

	private final int slotBiomassEmpty;
	private final int slotBiomassFull;

	private final int mainProgressBar;

	private FluidStack output = null;

	public TileEntityMasher() {
		super(8);

		addSlot(80, 39); //masher bottom
		addSlot(62, 21); //masher left
		addSlot(80, 21); //masher center
		addSlot(98, 21); //masher right
		slotWaterFull = addSlot(new TankSlot(this, 34, 21, true, -1)); //water full
		slotWaterEmpty = addSlot(new TankSlot(this, 34, 49, false, 1)); //water empty
		slotBiomassEmpty = addSlot(new TankSlot(this, 126, 21, false, -1)); //biomass empty
		slotBiomassFull = addSlot(new TankSlot(this, 126, 49, true, 1)); //biomass full

		AdvFluidTank water = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(
				FluidRegistry.WATER, TransferRule.INPUT);
		AdvFluidTank biomass = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1,
				TransferRule.OUTPUT);

		waterTank = addTank(water, 11, 13, 16, 60);
		biomassTank = addTank(biomass, 149, 13, 16, 60);

		mainProgressBar = addProgressBar(86, 59, 3, 20);
	}

	@Override
	protected void doPreWork() {
		fillTank(slotWaterFull, slotWaterEmpty, waterTank);
		emptyTank(slotBiomassEmpty, slotBiomassFull, biomassTank);
		ejectFluidAllSides(biomassTank);
	}

	@Override
	protected void checkWork() {
		MasherRecipe currRecipe;
		ItemStack stack;
		for (int i = 0; i <= 3; i++) {
			stack = getStackInSlot(i);

			if (stack == null)
				continue;

			currRecipe = MasherRecipe.getMasherRecipe(new SimpleItem(stack));

			if (currRecipe != null && getTank(waterTank).canDrain(currRecipe.water)
					&& getTank(biomassTank).canFill(currRecipe.output, true)) {
				addWork(currRecipe.work);
				setEnergyRate(currRecipe.power / currRecipe.work);
				decrStackSize(i, 1);
				getTank(waterTank).drain(currRecipe.water, true);// use water
				output = currRecipe.output;
				return;
			}

		}
	}

	@Override
	protected void doPostWork() {
		getTank(biomassTank).fill(output, true);
		output = null;
	}

	@Override
	public ProgressBar updateProgressBars(ProgressBar prog) {
		if (prog.id == mainProgressBar) {
			prog.total = workTotal;
			prog.value = work;
		}

		return prog;
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		writeFluidStack(tag, "output", output);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		output = readFluidStack(tag, "output");
	}
}
