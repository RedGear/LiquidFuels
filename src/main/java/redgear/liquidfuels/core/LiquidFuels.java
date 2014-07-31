package redgear.liquidfuels.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.core.api.item.ISimpleItem;
import redgear.core.block.BlockGeneric;
import redgear.core.block.MetaTile;
import redgear.core.block.SubTile;
import redgear.core.block.SubTileMachine;
import redgear.core.fluids.FluidUtil;
import redgear.core.imc.IMCEventHandler;
import redgear.core.item.MetaItem;
import redgear.core.item.MetaItemBucket;
import redgear.core.item.SubItem;
import redgear.core.item.SubItemBucket;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.util.CoreFuelHandler;
import redgear.liquidfuels.block.BlockRubberLeaves;
import redgear.liquidfuels.block.BlockRubberLog;
import redgear.liquidfuels.block.BlockRubberSapling;
import redgear.liquidfuels.fluid.ColoredFluid;
import redgear.liquidfuels.generators.gasoline.TileFactoryGasGen;
import redgear.liquidfuels.item.MetaItemColoredBucket;
import redgear.liquidfuels.machines.bioreactor.TileFactoryBioReactor;
import redgear.liquidfuels.machines.boiler.TileFactoryBoiler;
import redgear.liquidfuels.machines.dryer.TileFactoryDryer;
import redgear.liquidfuels.machines.fermenter.TileFactoryFermenter;
import redgear.liquidfuels.machines.masher.TileFactoryMasher;
import redgear.liquidfuels.machines.mixer.TileFactoryMixer;
import redgear.liquidfuels.machines.molder.TileFactoryMolder;
import redgear.liquidfuels.machines.still.TileFactoryStill;
import redgear.liquidfuels.machines.tap.TileFactoryTap;
import redgear.liquidfuels.machines.tower.TileFactoryCrackingBase;
import redgear.liquidfuels.machines.tower.TileFactoryCrackingTower;
import redgear.liquidfuels.machines.watergen.TileFactoryWaterGen;
import redgear.liquidfuels.plugins.BuildcraftPlugin;
import redgear.liquidfuels.plugins.CraftingRecipes;
import redgear.liquidfuels.plugins.FermenterRecipes;
import redgear.liquidfuels.plugins.FluidBoilerPlugin;
import redgear.liquidfuels.plugins.IC2Plugin;
import redgear.liquidfuels.plugins.MasherRecipes;
import redgear.liquidfuels.plugins.RailcraftPlugin;
import redgear.liquidfuels.plugins.ThermalExpansionPlugin;
import redgear.liquidfuels.recipes.MessageHandlerBoiler;
import redgear.liquidfuels.recipes.MessageHandlerFermenter;
import redgear.liquidfuels.recipes.MessageHandlerMasher;
import redgear.liquidfuels.world.MineOilShale;
import redgear.liquidfuels.world.OilShaleGenerator;
import redgear.liquidfuels.world.RubberTreeGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "redgear_liquidfuels", name = "Liquid Fuels", version = "@ModVersion@", dependencies = "required-after:redgear_core;after:Forestry; after:BuildCraft|Energy")
public class LiquidFuels extends ModUtils {

	@Instance("redgear_liquidfuels")
	public static ModUtils inst;

	public static MetaTile machines;
	public static MetaItem<SubItem> items;
	public static MetaItemBucket buckets;
	public static MetaItemColoredBucket coloredBuckets;
	public static Block oilShale;

	public static Block bioReactorMulit;
	public static Block asphaltBlock;

	public static BlockRubberSapling rubberSapling;
	public static Block rubberWood;
	public static Block rubberWoodDrained;
	public static Block rubberLeaves;
	public static Block rubberPlanks;

	public static ISimpleItem asphaltBucket;

	public static ISimpleItem masherBlades;
	public static ISimpleItem ptCoke;
	public static ISimpleItem rawNaturalRubber;
	public static ISimpleItem naturalRubber;
	public static ISimpleItem rawSyntheticRubber;
	public static ISimpleItem sytheticRubber;
	public static ISimpleItem rubberGear;
	public static ISimpleItem plasticGear;

	public static ISimpleItem masherBlock;
	public static ISimpleItem bioReactorBlock;
	public static ISimpleItem fermenterBlock;
	public static ISimpleItem boilerBlock;
	public static ISimpleItem stillBlock;
	public static ISimpleItem waterGenBlock;
	public static ISimpleItem crackingBaseBlock;
	public static ISimpleItem crackingTowerBlock;
	public static ISimpleItem dryerBlock;
	public static ISimpleItem gasGen;
	public static ISimpleItem treeTap;
	public static ISimpleItem mixer;
	public static ISimpleItem molder;

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
	public static Fluid ethyleneFluid;
	public static Fluid isopreneFluid;
	public static Fluid propaneFluid;
	public static Fluid latexFluid;
	public static Fluid plasticFluid;
	public static Fluid rubberFluid;
	public static Fluid dyeFluid;

	private static final String machineTexture = "Machine";
	private static final IMCEventHandler imcHandler = new IMCEventHandler();

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		addPlugin(new CraftingRecipes());
		addPlugin(new MasherRecipes());
		addPlugin(new FermenterRecipes());
		addPlugin(new RailcraftPlugin());
		addPlugin(new ThermalExpansionPlugin());
		addPlugin(new IC2Plugin());
		addPlugin(new BuildcraftPlugin());
		addPlugin(new FluidBoilerPlugin());

		imcHandler.addHandler("MasherRecipe", new MessageHandlerMasher());
		imcHandler.addHandler("FermenterRecipe", new MessageHandlerFermenter());
		imcHandler.addHandler("BoilerFuelRecipe", new MessageHandlerBoiler());

		items = new MetaItem<SubItem>("Items");
		masherBlades = items.addMetaItem(new SubItem("masherBlades"));
		ptCoke = items.addMetaItem(new SubItem("ptCoke"));
		rawNaturalRubber = items.addMetaItem(new SubItem("rawNaturalRubber"));
		naturalRubber = items.addMetaItem(new SubItem("naturalRubber"));
		rawSyntheticRubber = items.addMetaItem(new SubItem("rawSyntheticRubber"));
		sytheticRubber = items.addMetaItem(new SubItem("sytheticRubber"));
		rubberGear = items.addMetaItem(new SubItem("rubberGear"));
		plasticGear = items.addMetaItem(new SubItem("plasticGear"));

		CoreFuelHandler.addFuel(ptCoke, 3200);
		this.registerOre("fuelCoke", ptCoke);

		this.addSmelting(rawNaturalRubber, naturalRubber);
		this.addSmelting(rawSyntheticRubber, sytheticRubber);

		this.registerOre("itemRawRubber", rawNaturalRubber);
		this.registerOre("itemRubber", naturalRubber);
		this.registerOre("itemRawRubber", rawSyntheticRubber);
		this.registerOre("itemRubber", sytheticRubber);
		
		
		GameRegistry.addRecipe(new ShapedOreRecipe(rubberGear.getStack(), " R ", "RIR", " R ", 'R', "itemRubber", 'I', Items.iron_ingot));

		machines = new MetaTile(Material.iron, "Machine");
		machines.setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal);

		masherBlock = machines.addMetaBlock(new SubTileMachine("Masher", machineTexture, new TileFactoryMasher()));
		bioReactorBlock = machines.addMetaBlock(new SubTileMachine("BioReactor", machineTexture,
				new TileFactoryBioReactor()));
		fermenterBlock = machines.addMetaBlock(new SubTileMachine("Fermenter", machineTexture,
				new TileFactoryFermenter()));
		boilerBlock = machines.addMetaBlock(new SubTileMachine("Boiler", machineTexture, new TileFactoryBoiler()));
		stillBlock = machines.addMetaBlock(new SubTileMachine("Still", machineTexture, new TileFactoryStill()));
		waterGenBlock = machines
				.addMetaBlock(new SubTileMachine("WaterGen", machineTexture, new TileFactoryWaterGen()));
		crackingBaseBlock = machines.addMetaBlock(new SubTileMachine("CrackingBase", machineTexture,
				new TileFactoryCrackingBase()));
		crackingTowerBlock = machines.addMetaBlock(new SubTile("CrackingTower", new TileFactoryCrackingTower()));
		dryerBlock = machines.addMetaBlock(new SubTileMachine("Dryer", machineTexture, new TileFactoryDryer()));
		gasGen = machines.addMetaBlock(new SubTileMachine("GasGen", machineTexture, new TileFactoryGasGen()));
		treeTap = machines.addMetaBlock(new SubTileMachine("TreeTap", machineTexture, new TileFactoryTap()));
		mixer = machines.addMetaBlock(new SubTileMachine("Mixer", machineTexture, new TileFactoryMixer()));
		molder = machines.addMetaBlock(new SubTileMachine("Molder", machineTexture, new TileFactoryMolder()));

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
		ethyleneFluid = FluidUtil.createFluid("Ethylene").setGaseous(true).setDensity(10);
		isopreneFluid = FluidUtil.createFluid("Isoprene").setGaseous(true).setDensity(10);
		propaneFluid = FluidUtil.createFluid("Propane").setGaseous(true).setDensity(10);
		latexFluid = FluidUtil.createFluid("Latex");
		plasticFluid = FluidUtil.createFluid(new ColoredFluid("Plastic"), "Plastic").setTemperature(600);
		rubberFluid = FluidUtil.createFluid("Rubber");
		dyeFluid = FluidUtil.createFluid(new ColoredFluid("Dye"), "Dye");
		

		buckets = new MetaItemBucket("Buckets");
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
		buckets.addMetaItem(new SubItemBucket("bucektEthylene", ethyleneFluid));
		buckets.addMetaItem(new SubItemBucket("bucektIsoprene", isopreneFluid));
		buckets.addMetaItem(new SubItemBucket("bucektPropane", propaneFluid));
		buckets.addMetaItem(new SubItemBucket("bucketLatex", latexFluid));
		buckets.addMetaItem(new SubItemBucket("bucketRubber", rubberFluid));
		
		coloredBuckets = new MetaItemColoredBucket("coloredBucket");
		coloredBuckets.addMetaItem(new SubItemBucket("bucketPlastic", plasticFluid));
		coloredBuckets.addMetaItem(new SubItemBucket("bucketDye", dyeFluid));
		

		bioReactorMulit = new BlockGeneric(Material.iron, "BioReactorMulti");
		bioReactorMulit.setHardness(5.0F).setStepSound(Block.soundTypeMetal);

		asphaltBlock = new BlockGeneric(Material.rock, "Asphalt");
		asphaltBlock.setHardness(5f).setStepSound(Block.soundTypeStone).setHarvestLevel("pickaxe", 1);

		oilShale = new BlockGeneric(Material.rock, "OilShale");
		oilShale.setHardness(3f).setStepSound(Block.soundTypeStone).setHarvestLevel("pickaxe", 1);

		rubberSapling = new BlockRubberSapling("RubberSapling");

		rubberWood = new BlockRubberLog("RubberWood");
		rubberWoodDrained = new BlockRubberLog("RubberWoodDrained");

		rubberLeaves = new BlockRubberLeaves("RubberLeaves");

		rubberPlanks = new BlockGeneric(Material.wood, "RubberPlanks");
		rubberPlanks.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood).setHarvestLevel("axe", 0);

		if (Mods.Geocraft.isIn())
			MineOilShale.register();
		else
			GameRegistry.registerWorldGenerator(new OilShaleGenerator(), 0);

		this.logDebug("Found Geo: ", Mods.Geocraft.isIn());

		GameRegistry.registerWorldGenerator(new RubberTreeGenerator(), 10);
	}

	@Override
	public void Init(FMLInitializationEvent event) {

		GameRegistry.addShapelessRecipe(new ItemStack(rubberPlanks, 4, 0), rubberWoodDrained);

		this.registerOre("logWood", new ItemStack(rubberWoodDrained, 1, 0));
		this.registerOre("plankWood", new ItemStack(rubberPlanks, 1, 0));

	}

	@Override
	public void PostInit(FMLPostInitializationEvent event) {

	}

	@EventHandler
	public void IMC(IMCEvent event) {
		imcHandler.handle(event);
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
