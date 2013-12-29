package redgear.liquidfuels.machines;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import redgear.core.fluids.AdvFluidTank;
import redgear.core.inventory.TankSlot;
import redgear.core.render.ProgressBar;
import redgear.core.tile.TileEntityElectricMachine;
import redgear.core.util.SimpleItem;
import redgear.core.world.Location;
import redgear.core.world.MultiBlockMap;
import redgear.liquidfuels.core.LiquidFuels;

public class TileEntityBioReactor extends TileEntityElectricMachine
{
	private boolean multiFormed = false;
	private final AdvFluidTank tank;
	private final int inputSlot;
	private final int outputSlot;
	private final int workCycle = 120;//length of work always the same for this machine (unlike some others)
	private final int mainProgressBar;
	
	private static final MultiBlockMap multi;
	
	
    public TileEntityBioReactor(){
        super(60, 2000);

        inputSlot = addSlot(new TankSlot(this, 92, 21, true, true)); //masher bottom
        outputSlot = addSlot(new TankSlot(this, 92, 49, true, true)); //masher left
        
        tank = new AdvFluidTank(FluidContainerRegistry.BUCKET_VOLUME * 24);
        
        tank.addFluidMap(LiquidFuels.biomassFluid);
        //tank.addFluidMap(LiquidFuels.mashFluid);
        tank.setPressure(-1);
        addTank(tank, 69, 13, 16, 60); 
        
        mainProgressBar = addProgressBar(60, 13, 3, 60);
    }
    
    static{
		multi  = new MultiBlockMap(36);
		
		SimpleItem block = LiquidFuels.bioReactorMulit;
		SimpleItem air = new SimpleItem(0, 0);
		
		for(int x = -1; x <= 1; x++)
			for(int z = -1; z <= 1; z++)
				for(int y = -4; y <= -1; y++)
					multi.addLocation(x, y, z, ((y == -3 || y == -2)) && (x == 0 && z == 0) ? air : block);
    }
    
    @Override	
	protected void doPreWork() {
    	if(!checkMulitBlock()){
			tank.drain(tank.getCapacity(), true); //if the tank isn't formed, destroy all contents.
			work = 0;
			return;
		}
    	
    	fillTank(inputSlot, outputSlot, 0);
    	emptyTank(inputSlot, outputSlot, 0);
    	ejectAllFluids();
    	
    	if(tank.isEmpty() || tank.contains(LiquidFuels.biomassFluid))//either needs to be filled with biomass or it is full and is fermenting.
			tank.setPressure(-1);
		else
			tank.setPressure(1);
    }
    
    @Override
    protected void checkWork(){
    	if(tank.contains(LiquidFuels.biomassFluid) && tank.isFull())
			addWork(workCycle, 12);
    }
	
	@Override	
	protected void doPostWork() {
		tank.fillOverride(new FluidStack(LiquidFuels.mashFluid, tank.drain(tank.getCapacity(), true).amount), true);
	}
	
	@Override
    public ProgressBar updateProgressBars(ProgressBar prog){
    	if(prog.id == mainProgressBar){
    		prog.total = workCycle;
    		prog.value = work;
    	}
    	
    	return prog;
    }
	
	private boolean checkMulitBlock(){
		return multi.check(worldObj, new Location(this));
	}
}
