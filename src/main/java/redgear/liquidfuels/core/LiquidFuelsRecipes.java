package redgear.liquidfuels.core;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.recipes.RecipeMap;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.api.recipes.FermenterRecipe;
import redgear.liquidfuels.api.recipes.MasherRecipe;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class LiquidFuelsRecipes implements IPlugin {
	
	@Override
	public String getName() {
		return "Recipes";
	}

	@Override
	public boolean shouldRun(ModUtils inst, ModState state) {
		return true;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public void preInit(ModUtils inst) {

	}

	@Override
	public void Init(ModUtils inst) {
		initCrafting(inst);
		initMasher();
		initFermenter();
	}

	@Override
	public void postInit(ModUtils inst) {

	}

	private void initMasher() {
		List<ItemStack> items = OreDictionary.getOres("treeSapling"); //            water   power   work output amount
		for (ItemStack sapling : items)
			addMasher(new SimpleItem(sapling), 100, 45024000L, 5, LiquidFuels.biomassFluid, 150);

		addMasher(new SimpleItem(Items.wheat), 100, 45024000L, 5, LiquidFuels.biomassFluid, 150);
		addMasher(new SimpleItem(Items.carrot), 150, 67536000L, 5, LiquidFuels.biomassFluid, 150);
		addMasher(new SimpleItem(Items.nether_wart), 200, 135072000, 5, LiquidFuels.biomassFluid, 100);
		addMasher(new SimpleItem(Items.potato), 150, 67536000L, 5, LiquidFuels.biomassFluid, 200);
		addMasher(new SimpleItem(Items.melon), 20, 45024000L, 5, LiquidFuels.biomassFluid, 40);
		addMasher(new SimpleItem(Blocks.cactus), 20, 45024000l, 5, LiquidFuels.biomassFluid, 40);

		items = OreDictionary.getOres("treeLeaves");
		for (ItemStack leaves : items)
			addMasher(new SimpleItem(leaves), 100, 45024000L, 5, LiquidFuels.biomassFluid, 150);

		addMasher(new SimpleItem(Items.reeds), 10, 50652000L, 5, LiquidFuels.mashFluid, 150);
		addMasher(new SimpleItem(Items.sugar), 10, 50652000L, 5, LiquidFuels.mashFluid, 150);
	}

	private void addMasher(SimpleItem item, int water, long power, int work, Fluid fluid, int amount) {
		MasherRecipe.addMasherRecipe(item, water, power, work, new FluidStack(fluid, amount));
	}

	private void initFermenter() {
		FermenterRecipe.addFermenterRecipe(new FluidStack(LiquidFuels.mashFluid, 1000), 6, 1688400L, new FluidStack(
				LiquidFuels.stillageFluid, 1000));
	}

	private void initCrafting(ModUtils inst) {
		GameRegistry.addShapelessRecipe(LiquidFuels.asphaltBlock.getStack(16), new Object[] {Blocks.gravel,
				Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel,
				Blocks.gravel, LiquidFuels.asphaltBucket.getStack() });

		boolean hasSteel = inOreDict("blockSteel");
		ItemStack buildcraftTank = null; //ModConfigHelper.get("tile.tankBlock");
		ItemStack bcIronGear = null; //ModConfigHelper.get("item.ironGearItem");
		ItemStack bcPower = null; //ModConfigHelper.get("item.PipePowerGold");
		ItemStack bcLogic = null; //ModConfigHelper.get("item.pipeGate");

		ItemStack forestryMachine = null; //ModConfigHelper.get("item.sturdyMachine");

		ItemStack thermalMachine = null; //ModConfigHelper.get("tile.thermalexpansion.machine");
		ItemStack thermalPower = null; //ModConfigHelper.get("tile.thermalexpansion.conduit");

		ItemStack ic2Machine = null; //ModConfigHelper.get("blockMachine", 0);
		ItemStack ic2Cable = null; //ModConfigHelper.get("itemCable", 0);
		ItemStack ic2Logic = null; //ModConfigHelper.get("itemPartCircuit");
		ItemStack ic2Motor = null; //ModConfigHelper.get("itemRecipePart", 1);

		LeveledRecipe bladesRecipe = new LeveledRecipe(new RecipeMap(new String[] {"I I", " I ", "III" }, new Object[] {
				'I', Items.iron_ingot }));
		bladesRecipe.addLevel(inOreDict("ingotSteel"), new Object[] {'I', "ingotSteel" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.masherBlades.getStack(), bladesRecipe.register()));

		LeveledRecipe multiTankRecipe = new LeveledRecipe(new RecipeMap(new String[] {"RTR", "RBR", "RRR" },
				new Object[] {'R', Items.leather, 'T', Items.bucket, 'B', Blocks.iron_block }));
		multiTankRecipe.addLevel(hasSteel, new Object[] {'B', "blockSteel" });
		multiTankRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'T', buildcraftTank });
		multiTankRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'B', forestryMachine });
		multiTankRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'B', thermalMachine });
		multiTankRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'B', ic2Machine });
		multiTankRecipe.addLevel(inOreDict("itemRubber"), new Object[] {'R', "itemRubber" });
		multiTankRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'B', "craftingRawMachineTier00" });
		GameRegistry
				.addRecipe(new ShapedOreRecipe(LiquidFuels.bioReactorMulit.getStack(4), multiTankRecipe.register()));

		LeveledRecipe masherRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "PMP" }, new Object[] {
				'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I', Blocks.iron_block, 'P',
				Blocks.piston, 'M', LiquidFuels.masherBlades.getStack() }));
		masherRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		masherRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R', bcLogic,
				'B', buildcraftTank });
		masherRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		masherRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper" });
		masherRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		masherRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.masherBlock.getStack(), masherRecipe.register()));

		LeveledRecipe bioReactorRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" },
				new Object[] {'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B',
						LiquidFuels.bioReactorMulit.getStack(), 'I', Blocks.iron_block, 'P', Blocks.piston, 'W',
						Blocks.brown_mushroom }));
		bioReactorRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		bioReactorRecipe.addLevel(Mods.BuildcraftCore.isIn(),
				new Object[] {'G', bcPower, 'P', bcIronGear, 'R', bcLogic });
		bioReactorRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		bioReactorRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine,
				'P', "gearCopper" });
		bioReactorRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		bioReactorRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry
				.addRecipe(new ShapedOreRecipe(LiquidFuels.bioReactorBlock.getStack(), bioReactorRecipe.register()));

		LeveledRecipe fermenterRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" },
				new Object[] {'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
						Blocks.iron_block, 'P', Blocks.piston, 'W', Blocks.brown_mushroom }));
		fermenterRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		fermenterRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R', bcLogic,
				'B', buildcraftTank });
		fermenterRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		fermenterRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine,
				'P', "gearCopper" });
		fermenterRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		fermenterRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.fermenterBlock.getStack(), fermenterRecipe.register()));

		LeveledRecipe stillRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" }, new Object[] {
				'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I', Blocks.iron_block, 'P',
				Blocks.piston, 'W', Blocks.glass }));
		stillRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		stillRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R', bcLogic,
				'B', buildcraftTank });
		stillRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		stillRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper" });
		stillRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		stillRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.stillBlock.getStack(), stillRecipe.register()));

		LeveledRecipe boilerRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" }, new Object[] {
				'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I', Blocks.iron_block, 'P',
				Blocks.piston, 'W', Items.blaze_powder }));
		boilerRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		boilerRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R', bcLogic,
				'B', buildcraftTank });
		boilerRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		boilerRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper" });
		boilerRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		boilerRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.boilerBlock.getStack(), boilerRecipe.register()));

		if (inst.getBoolean("WaterGenCraftable")) {
			LeveledRecipe waterGenRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" },
					new Object[] {'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
							Blocks.iron_block, 'P', Blocks.piston, 'W', Items.water_bucket }));
			waterGenRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
			waterGenRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R',
					bcLogic, 'B', buildcraftTank });
			waterGenRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
			waterGenRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine,
					'P', "gearCopper" });
			waterGenRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
					ic2Motor });
			waterGenRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
			GameRegistry
					.addRecipe(new ShapedOreRecipe(LiquidFuels.waterGenBlock.getStack(), waterGenRecipe.register()));
		}
		LeveledRecipe dryerRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" }, new Object[] {
				'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I', Blocks.iron_block, 'P',
				Blocks.piston, 'W', Blocks.chest }));
		dryerRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		dryerRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R', bcLogic,
				'B', buildcraftTank });
		dryerRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		dryerRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper" });
		dryerRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		dryerRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.dryerBlock.getStack(), dryerRecipe.register()));

		LeveledRecipe crackingBaseRecipe = new LeveledRecipe(new RecipeMap(new String[] {"GRG", "BIB", "WPW" },
				new Object[] {'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
						Blocks.iron_block, 'P', Blocks.piston, 'W', Blocks.furnace }));
		crackingBaseRecipe.addLevel(hasSteel, new Object[] {'I', "blockSteel" });
		crackingBaseRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'G', bcPower, 'P', bcIronGear, 'R',
				bcLogic, 'B', buildcraftTank });
		crackingBaseRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'I', forestryMachine, 'P', "gearCopper" });
		crackingBaseRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[] {'G', thermalPower, 'I', thermalMachine,
				'P', "gearCopper" });
		crackingBaseRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P',
				ic2Motor });
		crackingBaseRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.crackingBaseBlock.getStack(), crackingBaseRecipe
				.register()));

		LeveledRecipe crackingTowerRecipe = new LeveledRecipe(new RecipeMap(new String[] {"RTR", "RBR", "RPR" },
				new Object[] {'R', Items.leather, 'T', Items.bucket, 'P', Blocks.piston, 'B', Blocks.iron_block }));
		crackingTowerRecipe.addLevel(hasSteel, new Object[] {'B', "blockSteel" });
		crackingTowerRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[] {'T', buildcraftTank, 'P', bcIronGear });
		crackingTowerRecipe.addLevel(Mods.Forestry.isIn(), new Object[] {'B', forestryMachine, 'P', "gearCopper" });
		crackingTowerRecipe.addLevel(Mods.ThermalExpansion.isIn(),
				new Object[] {'B', thermalMachine, 'P', "gearCopper" });
		crackingTowerRecipe.addLevel(Mods.IC2.isIn(), new Object[] {'B', ic2Machine, 'P', ic2Motor });
		crackingTowerRecipe.addLevel(inOreDict("itemRubber"), new Object[] {'R', "itemRubber" });
		crackingTowerRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'B', "craftingRawMachineTier00" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.crackingTowerBlock.getStack(), crackingTowerRecipe
				.register()));
	}

	private boolean inOreDict(String ore) {
		return !OreDictionary.getOres(ore).isEmpty();
	}
}
