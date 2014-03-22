package redgear.liquidfuels.core;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.core.compat.ModConfigHelper;
import redgear.core.compat.Mods;
import redgear.core.mod.IPlugin;
import redgear.core.mod.ModUtils;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.recipes.RecipeMap;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.api.recipes.FermenterRecipe;
import redgear.liquidfuels.api.recipes.MasherRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class LiquidFuelsRecipes implements IPlugin {

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
			addMasher(new SimpleItem(sapling), 100, 45024L, 5, LiquidFuels.biomassFluid, 150);

		addMasher(new SimpleItem(Item.wheat), 100, 45024L, 5, LiquidFuels.biomassFluid, 150);
		addMasher(new SimpleItem(Item.carrot), 150, 67536L, 5, LiquidFuels.biomassFluid, 150);
		addMasher(new SimpleItem(Item.netherStalkSeeds), 200, 135072L, 5, LiquidFuels.biomassFluid, 100);
		addMasher(new SimpleItem(Item.potato), 150, 67536L, 5, LiquidFuels.biomassFluid, 200);
		addMasher(new SimpleItem(Item.melon), 20, 45024L, 5, LiquidFuels.biomassFluid, 40);
		addMasher(new SimpleItem(Block.cactus), 20, 45024l, 5, LiquidFuels.biomassFluid, 40);
		
		items = OreDictionary.getOres("treeLeaves");
		for (ItemStack leaves : items)
			addMasher(new SimpleItem(leaves), 100, 45024L, 5, LiquidFuels.biomassFluid, 150);

		addMasher(new SimpleItem(Item.reed), 10, 50652L, 5, LiquidFuels.mashFluid, 150);
		addMasher(new SimpleItem(Item.sugar), 10, 50652L, 5, LiquidFuels.mashFluid, 150);
	}

	private void addMasher(SimpleItem item, int water, long power, int work, Fluid fluid, int amount) {
		MasherRecipe.addMasherRecipe(item, water, power, work, new FluidStack(fluid, amount));
	}

	private void initFermenter() {
		FermenterRecipe.addFermenterRecipe(new FluidStack(LiquidFuels.mashFluid, 1000), 6, 1688L, new FluidStack(
				LiquidFuels.stillageFluid, 1000));
	}

	private void initCrafting(ModUtils inst) {
		GameRegistry.addShapelessRecipe(LiquidFuels.asphaltBlock.getStack(16), new Object[] {Block.gravel,
				Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel,
				LiquidFuels.asphaltBucket.getStack() });

		boolean hasSteel = inOreDict("blockSteel");
		ItemStack buildcraftTank = ModConfigHelper.get("tile.tankBlock");
		ItemStack bcIronGear = ModConfigHelper.get("item.ironGearItem");
		ItemStack bcPower = ModConfigHelper.get("item.PipePowerGold");
		ItemStack bcLogic = ModConfigHelper.get("item.pipeGate");

		ItemStack forestryMachine = ModConfigHelper.get("item.sturdyMachine");

		ItemStack thermalMachine = ModConfigHelper.get("tile.thermalexpansion.machine");
		ItemStack thermalPower = GameRegistry.findItemStack("ThermalExpansion", "conduitEnergyHardened", 1);;

		ItemStack ic2Machine = getIc2Item("machine");
		ItemStack ic2Cable = getIc2Item("insulatedCopperCableItem");
		ItemStack ic2Logic = getIc2Item("electronicCircuit");
		ItemStack ic2Motor = getIc2Item("elemotor");

		LeveledRecipe bladesRecipe = new LeveledRecipe(new RecipeMap(new String[] {"I I", " I ", "III" }, new Object[] {
				'I', Item.ingotIron }));
		bladesRecipe.addLevel(inOreDict("ingotSteel"), new Object[] {'I', "ingotSteel" });
		GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.masherBlades.getStack(), bladesRecipe.register()));

		LeveledRecipe multiTankRecipe = new LeveledRecipe(new RecipeMap(new String[] {"RTR", "RBR", "RRR" },
				new Object[] {'R', Item.leather, 'T', Item.bucketEmpty, 'B', Block.blockIron }));
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
				'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P',
				Block.pistonBase, 'M', LiquidFuels.masherBlades.getStack() }));
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
				new Object[] {'G', Item.ingotGold, 'R', Block.blockRedstone, 'B',
						LiquidFuels.bioReactorMulit.getStack(), 'I', Block.blockIron, 'P', Block.pistonBase, 'W',
						Block.mushroomBrown }));
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
				new Object[] {'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I',
						Block.blockIron, 'P', Block.pistonBase, 'W', Block.mushroomBrown }));
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
				'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P',
				Block.pistonBase, 'W', Block.glass }));
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
				'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P',
				Block.pistonBase, 'W', Item.blazePowder }));
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
					new Object[] {'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I',
							Block.blockIron, 'P', Block.pistonBase, 'W', Item.bucketWater }));
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
				'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P',
				Block.pistonBase, 'W', Block.chest }));
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
				new Object[] {'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I',
						Block.blockIron, 'P', Block.pistonBase, 'W', Block.furnaceIdle }));
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
				new Object[] {'R', Item.leather, 'T', Item.bucketEmpty, 'P', Block.pistonBase, 'B', Block.blockIron }));
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
	
	
	public static ItemStack getIc2Item(String name) {
		try {
			Class<?> Ic2Items = Class.forName("ic2.core.Ic2Items");

			Object ret = Ic2Items.getField(name).get(null);

			if (ret instanceof ItemStack) {
				return (ItemStack) ret;
			}
			return null;
		} catch (Exception e) {
			System.out.println("IC2 API: Call getItem failed for "+name);

			return null;
		}
	}
}
