package redgear.liquidfuels.machines.tower;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.api.tile.IFacedTile;
import redgear.core.api.util.FacedTileHelper;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityTank;
import redgear.core.world.WorldLocation;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityCrackingBase extends TileEntityTank implements IFacedTile {

	ForgeDirection face;
	final AdvFluidTank steamTank;
	final AdvFluidTank oilTank;

	static final int fluidRate = 5;
	static final int steamRate = 20;

	static final List<Fluid> outputMap = new ArrayList<Fluid>();

	static {
		outputMap.add(LiquidFuels.oilFluid);
		outputMap.add(LiquidFuels.asphaltFluid);
		outputMap.add(LiquidFuels.petroleumCokeFluid);
		outputMap.add(LiquidFuels.dieselFluid);
		outputMap.add(LiquidFuels.keroseneFluid);
		outputMap.add(LiquidFuels.gasolineFluid);
		outputMap.add(LiquidFuels.ethyleneFluid);
		outputMap.add(LiquidFuels.isopreneFluid);
		outputMap.add(LiquidFuels.propaneFluid);
	}

	public TileEntityCrackingBase() {
		super(8);
		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.INPUT);
		addTank(steamTank);//, 48, 13, 16, 60

		oilTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		oilTank.addFluidMapFluids(outputMap.subList(0, outputMap.size() - 2), TransferRule.INPUT);
		addTank(oilTank);//, 97, 13, 16, 60
	}

	@Override
	protected boolean doPreWork() {
		return false;
	}

	@Override
	protected int checkWork() {
		if (oilTank.getAmount() > 0 && steamTank.getAmount() >= steamRate) {
			int tower = checkTower();
			if (tower > 0 && oilTank.getAmount() >= tower * fluidRate) {
				int index = getIndex();
				if (index < 0)
					return 0;

				WorldLocation loc = getLocation();
				for (int i = 1; i <= tower; i++) {
					TileEntityCrackingTower tile = (TileEntityCrackingTower) loc.translate(ForgeDirection.UP, i)
							.getTile();

					if (!tile.getTank(0).canFill(new FluidStack(outputMap.get(i + index), fluidRate), true))
						return 0;
				}

				return 4;
			}
		}

		return 0;
	}

	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean doPostWork() {
		int tower = checkTower();

		int index = getIndex();

		if (index < 0)
			return false;

		WorldLocation loc = getLocation();
		for (int i = 1; i <= tower; i++) {
			TileEntityCrackingTower tile = (TileEntityCrackingTower) loc.translate(ForgeDirection.UP, i).getTile();

			oilTank.drain(fillTower(tile, new FluidStack(outputMap.get(i + index), fluidRate)), true);

		}
		return true;
	}

	private int fillTower(TileEntityCrackingTower other, FluidStack resource) {
		other.forceSync();
		return other.getTank(0).fill(resource, true);
	}

	private int checkTower() {
		int count = 0;

		int index = getIndex();
		WorldLocation loc = getLocation();

		for (int i = 1; i < outputMap.size() - index; i++)
			if (loc.translate(ForgeDirection.UP, i).check(TileEntityCrackingTower.class))
				count++;
			else
				return count;

		return count;
	}

	private int getIndex() {
		int value = oilTank.getFluid().fluidID;

		for (int i = 0; i < outputMap.size(); i++)
			if (value == outputMap.get(i).getID())
				return i;

		return -1;
	}

	@Override
	protected boolean tryUseEnergy(int energy) {
		if (steamTank.canDrain(steamRate)) {
			steamTank.drain(steamRate, true);
			return true;
		} else
			return false;
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
