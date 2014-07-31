package redgear.liquidfuels.machines.dryer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.api.tile.IFacedTile;
import redgear.core.api.util.FacedTileHelper;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.InvSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.liquidfuels.recipes.DryerRecipe;

public class TileEntityDryer extends TileEntityTank implements IFacedTile {

	ForgeDirection face;
	final AdvFluidTank tank;
	static final int workCycle = 100;

	public TileEntityDryer() {
		super(10);
		addSlot(97, 31); //dryer top left
		addSlot(115, 31); //dryer top middle
		addSlot(133, 31); //dryer top right
		addSlot(97, 49); //dryer bottom left
		addSlot(115, 49); //dryer bottom middle
		addSlot(133, 49); //dryer bottom right
		
		for(InvSlot slot : this.getSlots()){
			slot.setMachineRule(TransferRule.OUTPUT);
			slot.setPlayerRule(TransferRule.OUTPUT);
		}

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		tank.addFluidMapIds(DryerRecipe.getFluidIds(), TransferRule.INPUT);
		addTank(tank);
	}

	@Override
	protected boolean doPreWork() {
		return false;
	}

	@Override
	protected int checkWork() {
		ItemStack out = DryerRecipe.getOutput(tank, false);
		
		if (out != null && out.stackSize > 0 && this.canAddStack(out)) 
			return workCycle;
		else
			return 0;
	}

	@Override
	protected boolean doPostWork() {
		addStack(DryerRecipe.getOutput(tank, true));
		return true;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		return true;
	}

	@Override
	public int getDirectionId() {
		return face.ordinal();
	}

	@Override
	public ForgeDirection getDirection() {
		return face;
	}

	@Override
	public boolean setDirection(int id) {
		if (id >= 0 && id < 6) {
			face = ForgeDirection.getOrientation(id);
			return true;
		} else
			return false;
	}

	@Override
	public boolean setDirection(ForgeDirection side) {
		return setDirection(side.ordinal());
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		face = FacedTileHelper.facePlayerFlat(entity);
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setByte("face", (byte) face.ordinal());
	}

	/**
	 * Don't forget to override this function in all children if you want more
	 * vars!
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		face = ForgeDirection.getOrientation(tag.getByte("face"));
	}
}
