package redgear.liquidfuels.machines.tower;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.inventory.TransferRule;
import redgear.core.world.MultiBlockMap;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.machines.TileEntityElectricFluidMachine;

public class TileEntityCrackingBase extends TileEntityElectricFluidMachine {

	final AdvFluidTank steamTank;
	final AdvFluidTank oilTank;

	static final int oilRate = 42;
	static final int steamRate = 21;
	final int slotInput;
	final int slotOutput;
	static final FluidStack[] outputMap = {new FluidStack(LiquidFuels.asphaltFluid, 2),
			new FluidStack(LiquidFuels.petroleumCokeFluid, 5), new FluidStack(LiquidFuels.dieselFluid, 10),
			new FluidStack(LiquidFuels.keroseneFluid, 5), new FluidStack(LiquidFuels.gasolineFluid, 5), 
			new FluidStack(LiquidFuels.napthaFluid, 10), new FluidStack(LiquidFuels.propaneFluid, 5)  };

	static final MultiBlockMap multi;

	public TileEntityCrackingBase() {
		super(8);

		slotInput = addSlot(new TankSlot(this, 120, 21, true, -1)); //input
		slotOutput = addSlot(new TankSlot(this, 120, 49, false, 1)); //output

		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
		steamTank.addFluidMap(LiquidFuels.steamFluid, TransferRule.INPUT);
		addTank(steamTank);//, 48, 13, 16, 60

		oilTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
		oilTank.addFluidMap(LiquidFuels.oilFluid, TransferRule.INPUT);
		addTank(oilTank);//, 97, 13, 16, 60
		
		this.setEnergyRate(4);
	}

	static {
		multi = new MultiBlockMap(outputMap.length);
		for (int i = 1; i <= outputMap.length; i++)
			multi.addLocation(0, i, 0, LiquidFuels.crackingTowerBlock);
	}

	@Override
	protected boolean doPreWork() {
		return fillTank(slotInput, slotOutput, oilTank);
	}

	@Override
	protected int checkWork() {
		if (checkMulitBlock() && oilTank.getAmount() >= oilRate && steamTank.getAmount() >= steamRate) {
			for (int i = 0; i <= outputMap.length - 1; i++)
				if (!(((TileEntityCrackingTower) worldObj.getTileEntity(xCoord, yCoord + 1 + i, zCoord))
						.getTank(0).canFill(outputMap[i], true)))
					return 0;

			oilTank.drain(oilRate, true);
			steamTank.drain(steamRate, true);
			return 4;//, 9400
		}
		
		return 0;
	}
	
	@Override
	protected boolean doWork() {
		return false;
	}

	@Override
	protected boolean doPostWork() {
		for (int i = 0; i <= outputMap.length - 1; i++)
			fillTower((TileEntityCrackingTower) worldObj.getTileEntity(xCoord, yCoord + 1 + i, zCoord), outputMap[i]);
		return false;
	}
	
	private void fillTower(TileEntityCrackingTower other, FluidStack resource){
		other.getTank(0).fill(resource, true);
		other.forceSync();
	}

	boolean checkMulitBlock() {
		return multi.check(worldObj, xCoord, yCoord, zCoord);
	}
}
