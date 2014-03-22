package redgear.liquidfuels.machines;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.tile.TileEntityElectricMachine;
import redgear.core.world.MultiBlockMap;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityCrackingBase extends TileEntityElectricMachine {

	private final AdvFluidTank steamTank;
	private final AdvFluidTank oilTank;
	private static final int towerHeight = 5;

	private static final int oilRate = 42;
	private static final int steamRate = 21;
	private final int slotInput;
	private final int slotOutput;
	private static final FluidStack[] outputMap = {new FluidStack(LiquidFuels.asphaltFluid, 2),
			new FluidStack(LiquidFuels.petroleumCokeFluid, 5), new FluidStack(LiquidFuels.dieselFluid, 10),
			new FluidStack(LiquidFuels.keroseneFluid, 5), new FluidStack(LiquidFuels.gasolineFluid, 20), };

	private static final MultiBlockMap multi;

	public TileEntityCrackingBase() {
		super(4, 50000);

		slotInput = addSlot(new TankSlot(this, 120, 21, true, -1)); //input
		slotOutput = addSlot(new TankSlot(this, 120, 49, false, 1)); //output

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.INPUT);
		addTank(steamTank, 48, 13, 16, 60);

		oilTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		oilTank.addFluidMap(LiquidFuels.oilFluid, TransferRule.INPUT);
		addTank(oilTank, 97, 13, 16, 60);
	}

	static {
		multi = new MultiBlockMap(towerHeight);
		for (int i = 1; i <= towerHeight; i++)
			multi.addLocation(0, i, 0, LiquidFuels.crackingTowerBlock);
	}

	@Override
	protected void doPreWork() {
		fillTank(slotInput, slotOutput, oilTank);
	}

	@Override
	protected void checkWork() {
		if (checkMulitBlock() && oilTank.getAmount() >= oilRate && steamTank.getAmount() >= steamRate) {
			for (int i = 0; i <= outputMap.length - 1; i++)
				if (!(((TileEntityCrackingTower) worldObj.getTileEntity(xCoord, yCoord + 1 + i, zCoord))
						.getTank(0).canFill(outputMap[i])))
					return;

			oilTank.drain(oilRate, true);
			steamTank.drain(steamRate, true);
			addWork(1, 9400);
		}
	}

	@Override
	protected void doPostWork() {
		for (int i = 0; i <= outputMap.length - 1; i++)
			((TileEntityCrackingTower) worldObj.getTileEntity(xCoord, yCoord + 1 + i, zCoord)).getTank(0)
					.fill(outputMap[i], true);
	}

	private boolean checkMulitBlock() {
		return multi.check(worldObj, xCoord, yCoord, zCoord);
	}
}
