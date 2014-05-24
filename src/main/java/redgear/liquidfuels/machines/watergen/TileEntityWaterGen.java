package redgear.liquidfuels.machines.watergen;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.api.tile.IFacedTile;
import redgear.core.api.util.FacedTileHelper;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;

public class TileEntityWaterGen extends TileEntityTank implements IFacedTile {

	ForgeDirection face;
	final AdvFluidTank tank;

	final int waterInput;
	final int waterOutput;
	static final FluidStack water = new FluidStack(FluidRegistry.WATER, 4000);

	public TileEntityWaterGen() {
		super(20);

		waterInput = addSlot(new TankSlot(this, 92, 21, false, -1)); //water empty
		waterOutput = addSlot(new TankSlot(this, 92, 49, true, 1)); //water full

		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4).addFluidMap(-1, TransferRule.OUTPUT);
		addTank(tank);//, 69, 13, 16, 60
	}

	@Override
	protected boolean doPreWork() {
		boolean check = false;
		check |= emptyTank(waterInput, waterOutput, tank);
		check |= ejectAllFluids();
		check |= tank.fill(water, true) > 0;
		return check;
	}

	@Override
	protected int checkWork() {
		return 0;
	}

	@Override
	protected boolean doPostWork() {
		return false;
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
		face = side;
		return true;
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
