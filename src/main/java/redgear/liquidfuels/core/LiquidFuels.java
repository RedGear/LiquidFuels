package redgear.liquidfuels.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import redgear.core.block.BlockGeneric;
import redgear.core.block.MetaBlock;
import redgear.core.block.MetaTile;
import redgear.core.block.SubBlock;
import redgear.core.block.SubTile;
import redgear.core.block.SubTileMachine;
import redgear.core.fluids.FluidUtil;
import redgear.core.item.MetaItem;
import redgear.core.item.MetaItemBucket;
import redgear.core.item.SubItem;
import redgear.core.item.SubItemBucket;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.network.CoreGuiHandler;
import redgear.core.util.CoreFuelHandler;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.machines.TileEntityBioReactor;
import redgear.liquidfuels.machines.TileEntityBoiler;
import redgear.liquidfuels.machines.TileEntityCrackingBase;
import redgear.liquidfuels.machines.TileEntityCrackingTower;
import redgear.liquidfuels.machines.TileEntityDryer;
import redgear.liquidfuels.machines.TileEntityFermenter;
import redgear.liquidfuels.machines.TileEntityMasher;
import redgear.liquidfuels.machines.TileEntityStill;
import redgear.liquidfuels.machines.TileEntityWaterGenerator;
import redgear.liquidfuels.plugins.BuildcraftPlugin;
import redgear.liquidfuels.plugins.CraftingRecipes;
import redgear.liquidfuels.plugins.FermenterRecipes;
import redgear.liquidfuels.plugins.IC2Plugin;
import redgear.liquidfuels.plugins.MasherRecipes;
import redgear.liquidfuels.plugins.RailcraftPlugin;
import redgear.liquidfuels.plugins.ThermalExpansionPlugin;
import redgear.liquidfuels.world.MineOilSands;
import redgear.liquidfuels.world.OilSandsGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "redgear_liquidfuels", name = "Liquid Fuels", version = "@ModVersion@", dependencies = "required-after:redgear_core;after:Forestry; after:BuildCraft|Core")
public class LiquidFuels extends ModUtils {

	@Instance("redgear_liquidfuels")
	public static ModUtils inst;

	public static MetaTile machines;
	public static MetaItem items;
	public static MetaItemBucket buckets;
	public static MetaBlock blocks;
	public static Block oilSands;

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

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		addPlugin(new CraftingRecipes());
		addPlugin(new MasherRecipes());
		addPlugin(new FermenterRecipes());
		addPlugin(new RailcraftPlugin());
		addPlugin(new ThermalExpansionPlugin());
		addPlugin(new IC2Plugin());
		addPlugin(new BuildcraftPlugin());

		items = new MetaItem("RedGear.LiquidFuels.Items");
		masherBlades = items.addMetaItem(new SubItem("masherBlades"));
		ptCoke = items.addMetaItem(new SubItem("ptCoke"));

		CoreFuelHandler.addFuel(ptCoke, 3200);

		machines = new MetaTile(Material.iron, "RedGear.LiquidFuels.Machine");
		machines.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);

		masherBlock = machines.addMetaBlock(new SubTileMachine("Masher", machineTexture, TileEntityMasher.class,
				CoreGuiHandler.addGuiMap("Masher")));
		bioReactorBlock = machines.addMetaBlock(new SubTileMachine("BioReactor", machineTexture,
				TileEntityBioReactor.class, CoreGuiHandler.addGuiMap("BioReactor", "Bio Reactor")));
		fermenterBlock = machines.addMetaBlock(new SubTileMachine("Fermenter", machineTexture,
				TileEntityFermenter.class, CoreGuiHandler.addGuiMap("Fermenter")));
		boilerBlock = machines.addMetaBlock(new SubTileMachine("Boiler", machineTexture, TileEntityBoiler.class,
				CoreGuiHandler.addGuiMap("Boiler", "Electric Boiler")));
		stillBlock = machines.addMetaBlock(new SubTileMachine("Still", machineTexture, TileEntityStill.class,
				CoreGuiHandler.addGuiMap("Still")));
		waterGenBlock = machines.addMetaBlock(new SubTileMachine("WaterGen", machineTexture,
				TileEntityWaterGenerator.class, CoreGuiHandler.addGuiMap("WaterGen", "Water Generator")));
		crackingBaseBlock = machines.addMetaBlock(new SubTileMachine("CrackingBase", machineTexture,
				TileEntityCrackingBase.class, CoreGuiHandler.addGuiMap("CrackingBase", "Cracking Tower Base")));
		crackingTowerBlock = machines.addMetaBlock(new SubTile("CrackingTower", TileEntityCrackingTower.class,
				CoreGuiHandler.addGuiMap("WaterGen", "Cracking Tower")));
		dryerBlock = machines.addMetaBlock(new SubTileMachine("Dryer", machineTexture, TileEntityDryer.class,
				CoreGuiHandler.addGuiMap("Dryer")));

		biomassFluid = FluidUtil.createFluid("biomass");
		mashFluid = FluidUtil.createFluid("Mash");
		stillageFluid = FluidUtil.createFluid("Stillage");
		steamFluid = FluidUtil.createFluid("Steam");
		ethanolFluid = FluidUtil.createFluid("bioethanol");
		oilFluid = FluidUtil.createFluid("Oil");
		asphaltFluid = FluidUtil.createFluid("Asphalt");
		petroleumCokeFluid = FluidUtil.createFluid("ptCoke");
		dieselFluid = FluidUtil.createFluid("Diesel");
		keroseneFluid = FluidUtil.createFluid("Kerosene");
		gasolineFluid = FluidUtil.createFluid("Gasoline");

		buckets = new MetaItemBucket("RedGear.LiquidFuels.Buckets");
		buckets.addMetaItem(new SubItemBucket("bucketBiomass", biomassFluid));
		buckets.addMetaItem(new SubItemBucket("bucketMash", mashFluid));
		buckets.addMetaItem(new SubItemBucket("bucketStillage", stillageFluid));
		buckets.addMetaItem(new SubItemBucket("bucketEthanol", ethanolFluid));
		buckets.addMetaItem(new SubItemBucket("bucketOil", oilFluid));
		asphaltBucket = buckets.addMetaItem(new SubItemBucket("bucketAsphalt", asphaltFluid));
		buckets.addMetaItem(new SubItemBucket("bucketPtCoke", petroleumCokeFluid));
		buckets.addMetaItem(new SubItemBucket("bucketDiesel", dieselFluid));
		buckets.addMetaItem(new SubItemBucket("bucketKerosene", keroseneFluid));
		buckets.addMetaItem(new SubItemBucket("bucketGasoline", gasolineFluid));

		blocks = new MetaBlock(Material.iron, "RedGear.LiquidFuels.Blocks");
		blocks.setHardness(5.0F).setStepSound(Block.soundTypeMetal);
		bioReactorMulit = blocks.addMetaBlock(new SubBlock("BioReactorMulti"));
		asphaltBlock = blocks.addMetaBlock(new SubBlock("Asphalt"));

		oilSands = new BlockGeneric(Material.rock, "OilSands");

		if (Mods.Geocraft.isIn())
			MineOilSands.register();
		else
			GameRegistry.registerWorldGenerator(new OilSandsGenerator(), 0);
	}

	@Override
	public void Init(FMLInitializationEvent event) {

	}

	@Override
	public void PostInit(FMLPostInitializationEvent event) {

	}

	@Override
	@EventHandler
	public void PreInitialization(FMLPreInitializationEvent event) {
		super.PreInitialization(event);
	}

	@Override
	@EventHandler
	public void Initialization(FMLInitializationEvent event) {
		super.Initialization(event);
	}

	@Override
	@EventHandler
	public void PostInitialization(FMLPostInitializationEvent event) {
		super.PostInitialization(event);
	}
}
