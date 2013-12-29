package redgear.liquidfuels.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import redgear.core.block.MetaBlock;
import redgear.core.block.MetaTile;
import redgear.core.block.SubBlock;
import redgear.core.block.SubTile;
import redgear.core.block.SubTileMachine;
import redgear.core.fluids.BlockFluid;
import redgear.core.item.MetaItem;
import redgear.core.item.MetaItemBucket;
import redgear.core.item.SubItem;
import redgear.core.item.SubItemBucket;
import redgear.core.mod.ModUtils;
import redgear.core.mod.RedGear;
import redgear.core.network.CoreGuiHandler;
import redgear.core.util.CoreFuelHandler;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.api.recipes.RecipeManager;
import redgear.liquidfuels.machines.TileEntityBioReactor;
import redgear.liquidfuels.machines.TileEntityBoiler;
import redgear.liquidfuels.machines.TileEntityCrackingBase;
import redgear.liquidfuels.machines.TileEntityCrackingTower;
import redgear.liquidfuels.machines.TileEntityDryer;
import redgear.liquidfuels.machines.TileEntityFermenter;
import redgear.liquidfuels.machines.TileEntityMasher;
import redgear.liquidfuels.machines.TileEntityStill;
import redgear.liquidfuels.machines.TileEntityWaterGenerator;
import redgear.liquidfuels.recipes.FermenterRegistry;
import redgear.liquidfuels.recipes.MasherRegistry;
import redgear.liquidfuels.recipes.Recipes;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RedGear.LiquidFuelsID, name= RedGear.LiquidFuelsName, version= RedGear.LiquidFuelsVersion, dependencies= RedGear.LiquidFuelsDepend)
public class LiquidFuels extends ModUtils{
	
	
	@Instance(RedGear.LiquidFuelsID)
	public static ModUtils inst;
	
    public static MetaTile machines;
    public static MetaItem items;
    public static MetaItemBucket buckets;
    public static MetaBlock blocks;
    
    public static SimpleItem bioReactorMulit;
    public static SimpleItem asphaltBlock;
    
    public static SimpleItem asphaltBucket;
    
    public static SimpleItem masherBlades;
    public static SimpleItem ptCoke;
    
    public static SimpleItem masherBlock;
    public static SimpleItem bioReactorBlock;
    public static SimpleItem fermenterBlock;
    public static SimpleItem boilerBlock;
    public static SimpleItem stillBlock;
    public static SimpleItem waterGenBlock;
    public static SimpleItem crackingBaseBlock;
    public static SimpleItem crackingTowerBlock;
    public static SimpleItem dryerBlock;
    
    public static Fluid biomassFluid;
    public static Fluid mashFluid;
    public static Fluid stillageFluid;
    public static Fluid steamFluid;
    public static Fluid ethanolFluid;
    public static Fluid oilFluid;
    public static Fluid asphaltFluid;
    public static Fluid petroleumCokeFluid;
    public static Fluid dieselFluid;
    public static Fluid keroseneFluid;
    public static Fluid gasolineFluid;
    
    
    private static final String machineTexture = "Machine";
    
    public LiquidFuels() {
		super(2600, 24500);
	}
    
    @Override
    public void PreInit(FMLPreInitializationEvent event){
        RecipeManager.masherRegistry = new MasherRegistry();
        RecipeManager.fermenterRegistry = new FermenterRegistry();
    }

    @Override
    public void Init(FMLInitializationEvent event){
    	items = new MetaItem(getItemId("items"), "RedGear.LiquidFuels.Items");
    	masherBlades = items.addMetaItem(new SubItem("masherBlades", "Masher Blades"));
    	ptCoke = items.addMetaItem(new SubItem("ptCoke", "Petroleum Coke"));
    	
    	CoreFuelHandler.addFuel(ptCoke, 3200);
    	
        machines = new MetaTile(getBlockId("machines"), Material.iron, "RedGear.LiquidFuels.Machine");
        machines.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundMetalFootstep);
        
        masherBlock = machines.addMetaBlock(new SubTileMachine("Masher", machineTexture, TileEntityMasher.class, CoreGuiHandler.addGuiMap("Masher")));
        bioReactorBlock = machines.addMetaBlock(new SubTileMachine("BioReactor", machineTexture, "Bio Reactor", TileEntityBioReactor.class, CoreGuiHandler.addGuiMap("BioReactor", "Bio Reactor")));
        fermenterBlock = machines.addMetaBlock(new SubTileMachine("Fermenter", machineTexture, TileEntityFermenter.class, CoreGuiHandler.addGuiMap("Fermenter")));
        boilerBlock = machines.addMetaBlock(new SubTileMachine("Boiler", machineTexture, "Electric Boiler", TileEntityBoiler.class, CoreGuiHandler.addGuiMap("Boiler", "Electric Boiler")));
        stillBlock = machines.addMetaBlock(new SubTileMachine("Still", machineTexture, TileEntityStill.class, CoreGuiHandler.addGuiMap("Still")));
        waterGenBlock = machines.addMetaBlock(new SubTileMachine("WaterGen", machineTexture, "Water Generator", TileEntityWaterGenerator.class, CoreGuiHandler.addGuiMap("WaterGen", "Water Generator")));
        crackingBaseBlock = machines.addMetaBlock(new SubTileMachine("CrackingBase", machineTexture, "Cracking Tower Base", TileEntityCrackingBase.class, CoreGuiHandler.addGuiMap("CrackingBase", "Cracking Tower Base")));
        crackingTowerBlock = machines.addMetaBlock(new SubTile("CrackingTower", "Cracking Tower", TileEntityCrackingTower.class, CoreGuiHandler.addGuiMap("WaterGen", "Cracking Tower")));
        dryerBlock = machines.addMetaBlock(new SubTileMachine("Dryer", machineTexture, TileEntityDryer.class, CoreGuiHandler.addGuiMap("Dryer")));
        
        biomassFluid = createFluid("LFbiomass", "Biomass");
        mashFluid = createFluid("Mash");
        stillageFluid = createFluid("Stillage");
        steamFluid = createFluid("Steam");
        ethanolFluid = createFluid("bioethanol", "Ethanol");
        oilFluid = createFluid("Oil");
        asphaltFluid = createFluid("Asphalt");
        petroleumCokeFluid = createFluid("ptcoke", "ptCoke", "Petroleum Coke Fluid");
        dieselFluid = createFluid("Diesel");
        keroseneFluid = createFluid("Kerosene");
        gasolineFluid = createFluid("Gasoline");
        
    	
    	buckets = new MetaItemBucket(getItemId("buckets"), "RedGear.LiquidFuels.Buckets");
    	buckets.addMetaItem(new SubItemBucket("bucketBiomass", "Biomass Bucket", biomassFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketMash", "Mash Bucket", mashFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketStillage", "Stillage Bucket", stillageFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketEthanol", "Ethanol Bucket", ethanolFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketOil", "Crude Oil Bucket", oilFluid));
    	asphaltBucket = buckets.addMetaItem(new SubItemBucket("bucketAsphalt", "Asphalt Bucket", asphaltFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketPtCoke", "Petroleum Coke Fluid Bucket", petroleumCokeFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketDiesel", "Diesel Bucket", dieselFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketKerosene", "Kerosene Bucket", keroseneFluid));
    	buckets.addMetaItem(new SubItemBucket("bucketGasoline", "Gasoline Bucket", gasolineFluid));
    	
    	
    	blocks = new MetaBlock(getBlockId("blocks"), Material.iron, "RedGear.LiquidFuels.Blocks");
    	blocks.setHardness(5.0F).setStepSound(Block.soundMetalFootstep);
    	bioReactorMulit = blocks.addMetaBlock(new SubBlock("BioReactorMulti", "Bio Reactor Tank"));
    	asphaltBlock = blocks.addMetaBlock(new SubBlock("Asphalt"));
    }
    
    @Override
    public void PostInit(FMLPostInitializationEvent event){
    	Recipes.initAll();
    }
    
    private Fluid createFluid(String name){
    	return createFluid(name.toLowerCase(), name);
    }
    
    private Fluid createFluid(String fluidName, String name){
    	return createFluid(fluidName, name.toLowerCase(), name);
    }
    
    private Fluid createFluid(String fluidName, String name, String displayName){
    	Fluid fluid = new Fluid(fluidName);
        if(!FluidRegistry.registerFluid(fluid))
        	fluid = FluidRegistry.getFluid(fluidName);
        if(fluid.getBlockID() == -1){
        	Block block = new BlockFluid(getBlockId(name), fluid, name);
        	GameRegistry.registerBlock(block, displayName);
        }
        return fluid;
    }
    
    @EventHandler public void PreInitialization(FMLPreInitializationEvent event){super.PreInitialization(event);}
	@EventHandler public void Initialization(FMLInitializationEvent event){super.Initialization(event);}
	@EventHandler public void PostInitialization(FMLPostInitializationEvent event){super.PostInitialization(event);}
}
