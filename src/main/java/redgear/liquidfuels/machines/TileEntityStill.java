package redgear.liquidfuels.machines;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.tile.TileEntityElectricMachine;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityStill extends TileEntityElectricMachine  {

	private final AdvFluidTank steamTank;
	private final AdvFluidTank stillageTank;
	private final AdvFluidTank ethanolTank;
	private final int stillageInput;
	private final int stillageOutput;
	private final int ethanolInput;
	private final int ethanolOutput;
	private final int steamRatio = 144; //How much steam is needed for each operation
	private final int stillageRatio = 4; //How much stillage is needed for one mB of ethanol
	private final int ethanolRatio = 160; //Amount of ethanol made each doWork.
	
	public TileEntityStill() {
		super(4, 2000);
		
		stillageInput = addSlot(new TankSlot(this, 76, 21, true, -1)); //stillage full
		stillageOutput = addSlot(new TankSlot(this, 76, 49, false, 1)); //stillage empty
		ethanolInput = addSlot(new TankSlot(this, 104, 21, false, -1)); //ethanol empty
		ethanolOutput =  addSlot(new TankSlot(this, 104, 49, true, 1)); //ethanol full
		
		steamTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 8);
		steamTank.addFluidMap(LiquidFuels.steamFluid);
        steamTank.setPressure(-1);
        addTank(steamTank, 18, 13, 16, 60);
        
        stillageTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
        stillageTank.addFluidMap(LiquidFuels.stillageFluid);
        stillageTank.setPressure(-1);
        addTank(stillageTank, 53, 13, 16, 60);
        
        ethanolTank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 4);
        ethanolTank.addFluidMap(LiquidFuels.ethanolFluid);
        ethanolTank.setPressure(1);
        addTank(ethanolTank, 132, 13, 16, 60);
	}
	
	@Override
	protected void doPreWork(){
    	fillTank(stillageInput, stillageOutput, stillageTank);
		emptyTank(ethanolInput, ethanolOutput, ethanolTank);
		
		ejectFluidAllSides(ethanolTank);
		
		
	}
	
	@Override
	protected void checkWork() {
		if(ethanolRatio <= ethanolTank.getSpace() && steamTank.getFluidAmount() >= steamRatio && stillageTank.getFluidAmount() >= stillageRatio * ethanolRatio)
			addWork(1, 3);
	}
	
	@Override
	protected void doPostWork(){
		ethanolTank.fillOverride(new FluidStack(LiquidFuels.ethanolFluid, ethanolRatio), true);
		steamTank.drain(steamRatio, true);
		stillageTank.drain(stillageRatio * ethanolRatio, true);
	}
}
