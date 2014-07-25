package redgear.liquidfuels.machines.tap;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import redgear.core.api.tile.IFacedTile;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityTap extends TileEntityTank implements IFacedTile{

	ForgeDirection face;
	final AdvFluidTank tank;
	
	public TileEntityTap() {
		super(10);
		
		tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME);
		tank.addFluidMap(LiquidFuels.latexFluid, TransferRule.OUTPUT);
		addTank(tank);
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
		if(id > 1 && id < 6){
			face = ForgeDirection.getOrientation(id);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean setDirection(ForgeDirection side) {
		return setDirection(side.ordinal());
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		setDirection(ForgeDirection.WEST);
		
		if(world.getBlock(x, y, x - 1) == LiquidFuels.rubberWood)
			setDirection(ForgeDirection.NORTH);
		
		if(world.getBlock(x + 1, y, x) == LiquidFuels.rubberWood)
			setDirection(ForgeDirection.EAST);
		
		if(world.getBlock(x, y, x + 1) == LiquidFuels.rubberWood)
			setDirection(ForgeDirection.SOUTH);
		
		if(world.getBlock(x - 1, y, x) == LiquidFuels.rubberWood)
			setDirection(ForgeDirection.WEST);
			
	}

	@Override
	protected boolean doPreWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int checkWork() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean doWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean doPostWork() {
		// TODO Auto-generated method stub
		return false;
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
