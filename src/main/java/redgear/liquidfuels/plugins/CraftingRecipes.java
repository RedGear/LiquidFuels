package redgear.liquidfuels.plugins;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.mod.Mods;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.util.ItemRegUtil;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.LoaderState.ModState;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingRecipes implements IPlugin {

	@Override
	public String getName() {
		return "CraftingRecipes";
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
	public void preInit(ModUtils mod) {

	}

	@Override
	public void Init(ModUtils mod) {
		GameRegistry.addShapelessRecipe(new ItemStack(LiquidFuels.asphaltBlock, 16), new Object[] {Blocks.gravel,
				Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel,
				Blocks.gravel, LiquidFuels.asphaltBucket.getStack() });

		boolean hasSteel = mod.inOreDict("blockSteel");
		SimpleItem buildcraftTank = ItemRegUtil.findItem(Mods.BCFactory, "tankBlock");
		SimpleItem bcIronGear = ItemRegUtil.findItem(Mods.BCCore, "ironGearItem");
		SimpleItem bcPower = ItemRegUtil.findItem(Mods.BCTransport, "item.buildcraftPipe.pipepowergold");
		SimpleItem bcLogic = ItemRegUtil.findItem(Mods.BCTransport, "pipeGate");

		SimpleItem forestryMachine = null; //ModConfigHelper.get("item.sturdyMachine");

		SimpleItem thermalMachine = null; //ModConfigHelper.get("tile.thermalexpansion.machine");
		SimpleItem thermalPower = null; //ModConfigHelper.get("tile.thermalexpansion.conduit");

		SimpleItem ic2Machine = null; //ModConfigHelper.get("blockMachine", 0);
		SimpleItem ic2Cable = null; //ModConfigHelper.get("itemCable", 0);
		SimpleItem ic2Logic = null; //ModConfigHelper.get("itemPartCircuit");
		SimpleItem ic2Motor = null; //ModConfigHelper.get("itemRecipePart", 1);

		LeveledRecipe bladesRecipe = new LeveledRecipe("I I", " I ", "III");
		bladesRecipe.addLevel(true, 'I', Items.iron_ingot);
		bladesRecipe.addLevel(mod.inOreDict("ingotSteel"), 'I', "ingotSteel");
		bladesRecipe.registerShaped(LiquidFuels.masherBlades);

		LeveledRecipe multiTankRecipe = new LeveledRecipe("RTR", "RBR", "RRR");
		multiTankRecipe.addLevel(true, 'R', Items.leather, 'T', Items.bucket, 'B', Blocks.iron_block);
		multiTankRecipe.addLevel(hasSteel, 'B', "blockSteel");
		multiTankRecipe.addLevel(Mods.BCCore.isIn(), 'T', buildcraftTank);
		multiTankRecipe.addLevel(Mods.Forestry.isIn(), 'B', forestryMachine);
		multiTankRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'B', thermalMachine);
		multiTankRecipe.addLevel(Mods.IC2.isIn(), 'B', ic2Machine);
		multiTankRecipe.addLevel(mod.inOreDict("itemRubber"), 'R', "itemRubber");
		multiTankRecipe.addLevel(Mods.Greg.isIn(), 'B', "craftingRawMachineTier00");
		multiTankRecipe.registerShaped(new SimpleItem(LiquidFuels.bioReactorMulit), 4);

		LeveledRecipe masherRecipe = new LeveledRecipe("GRG", "BIB", "PMP");
		masherRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
				Blocks.iron_block, 'P', Blocks.piston, 'M', LiquidFuels.masherBlades);
		masherRecipe.addLevel(hasSteel, 'I', "blockSteel");
		masherRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank);
		masherRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		masherRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper");
		masherRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		masherRecipe.addLevel(Mods.Greg.isIn(), 'I', "craftingRawMachineTier00");
		masherRecipe.registerShaped(LiquidFuels.masherBlock);

		LeveledRecipe bioReactorRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
		bioReactorRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B',
				new ItemStack(LiquidFuels.bioReactorMulit, 1), 'I', Blocks.iron_block, 'P', Blocks.piston, 'W',
				Blocks.brown_mushroom);
		bioReactorRecipe.addLevel(hasSteel, 'I', "blockSteel");
		bioReactorRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic);
		bioReactorRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		bioReactorRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper");
		bioReactorRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		bioReactorRecipe.addLevel(Mods.Greg.isIn(), 'I', "craftingRawMachineTier00");
		bioReactorRecipe.registerShaped(LiquidFuels.bioReactorBlock);

		LeveledRecipe fermenterRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
		fermenterRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
				Blocks.iron_block, 'P', Blocks.piston, 'W', Blocks.brown_mushroom);
		fermenterRecipe.addLevel(hasSteel, 'I', "blockSteel");
		fermenterRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank);
		fermenterRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		fermenterRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper");
		fermenterRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		fermenterRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		fermenterRecipe.registerShaped(LiquidFuels.fermenterBlock);

		LeveledRecipe stillRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
		stillRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
				Blocks.iron_block, 'P', Blocks.piston, 'W', Blocks.glass);
		stillRecipe.addLevel(hasSteel, 'I', "blockSteel");
		stillRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank);
		stillRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		stillRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper");
		stillRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		stillRecipe.addLevel(Mods.Greg.isIn(), 'I', "craftingRawMachineTier00");
		stillRecipe.registerShaped(LiquidFuels.stillBlock);

		LeveledRecipe boilerRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
		boilerRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
				Blocks.iron_block, 'P', Blocks.piston, 'W', Items.blaze_powder);
		boilerRecipe.addLevel(hasSteel, 'I', "blockSteel");
		boilerRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank);
		boilerRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		boilerRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper");
		boilerRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		boilerRecipe.addLevel(Mods.Greg.isIn(), 'I', "craftingRawMachineTier00");
		boilerRecipe.registerShaped(LiquidFuels.boilerBlock);

		if (mod.getBoolean("WaterGenCraftable")) {
			LeveledRecipe waterGenRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
			waterGenRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
					Blocks.iron_block, 'P', Blocks.piston, 'W', Items.water_bucket);
			waterGenRecipe.addLevel(hasSteel, 'I', "blockSteel");
			waterGenRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B',
					buildcraftTank);
			waterGenRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
			waterGenRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P',
					"gearCopper");
			waterGenRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
			waterGenRecipe.addLevel(Mods.Greg.isIn(), 'I', "craftingRawMachineTier00");
			waterGenRecipe.registerShaped(LiquidFuels.waterGenBlock);
		}

		LeveledRecipe dryerRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
		dryerRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
				Blocks.iron_block, 'P', Blocks.piston, 'W', Blocks.chest);
		dryerRecipe.addLevel(hasSteel, 'I', "blockSteel");
		dryerRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank);
		dryerRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		dryerRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper");
		dryerRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		dryerRecipe.addLevel(Mods.Greg.isIn(), 'I', "craftingRawMachineTier00");
		dryerRecipe.registerShaped(LiquidFuels.dryerBlock);

		LeveledRecipe crackingBaseRecipe = new LeveledRecipe("GRG", "BIB", "WPW");
		crackingBaseRecipe.addLevel(true, 'G', Items.gold_ingot, 'R', Blocks.redstone_block, 'B', Items.bucket, 'I',
				Blocks.iron_block, 'P', Blocks.piston, 'W', Blocks.furnace);
		crackingBaseRecipe.addLevel(hasSteel, 'I', "blockSteel");
		crackingBaseRecipe.addLevel(Mods.BCCore.isIn(), 'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B',
				buildcraftTank);
		crackingBaseRecipe.addLevel(Mods.Forestry.isIn(), 'I', forestryMachine, 'P', "gearCopper");
		crackingBaseRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'G', thermalPower, 'I', thermalMachine, 'P',
				"gearCopper");
		crackingBaseRecipe.addLevel(Mods.IC2.isIn(), 'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor);
		crackingBaseRecipe.addLevel(Mods.Greg.isIn(), new Object[] {'I', "craftingRawMachineTier00" });
		crackingBaseRecipe.registerShaped(LiquidFuels.crackingBaseBlock);

		LeveledRecipe crackingTowerRecipe = new LeveledRecipe("RTR", "RBR", "RPR");
		crackingTowerRecipe.addLevel(true, 'R', Items.leather, 'T', Items.bucket, 'P', Blocks.piston, 'B',
				Blocks.iron_block);
		crackingTowerRecipe.addLevel(hasSteel, 'B', "blockSteel");
		crackingTowerRecipe.addLevel(Mods.BCCore.isIn(), 'T', buildcraftTank, 'P', bcIronGear);
		crackingTowerRecipe.addLevel(Mods.Forestry.isIn(), 'B', forestryMachine, 'P', "gearCopper");
		crackingTowerRecipe.addLevel(Mods.ThermalExpansion.isIn(), 'B', thermalMachine, 'P', "gearCopper");
		crackingTowerRecipe.addLevel(Mods.IC2.isIn(), 'B', ic2Machine, 'P', ic2Motor);
		crackingTowerRecipe.addLevel(mod.inOreDict("itemRubber"), 'R', "itemRubber");
		crackingTowerRecipe.addLevel(Mods.Greg.isIn(), 'B', "craftingRawMachineTier00");
		crackingTowerRecipe.registerShaped(LiquidFuels.crackingTowerBlock);
	}

	@Override
	public void postInit(ModUtils mod) {

	}
}
