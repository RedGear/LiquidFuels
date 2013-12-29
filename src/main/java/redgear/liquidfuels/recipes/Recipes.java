package redgear.liquidfuels.recipes;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import redgear.core.compat.ModConfigHelper;
import redgear.core.compat.Mods;
import redgear.core.recipes.LeveledRecipe;
import redgear.core.recipes.RecipeMap;
import redgear.liquidfuels.api.recipes.RecipeManager;
import redgear.liquidfuels.core.LiquidFuels;
import buildcraft.api.fuels.IronEngineFuel;
import buildcraft.api.recipes.RefineryRecipes;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	
	public static void initAll(){
		initCrafting();
		initMasher();
		initFermenter();
		
		if(Mods.Railcraft.isIn())
			initBoilerRecipies();
		
		if(Mods.BuildcraftCore.isIn())
			initRefineryRecipies();
		
		if(Mods.IC2.isIn())
			initIC2Compat();
	}
	
	private static void initMasher(){
		ArrayList<ItemStack> saplings = OreDictionary.getOres("treeSapling"); //            water   power   work output amount
        for(ItemStack sapling : saplings)
        	RecipeManager.masherRegistry.addMasherRecipe(sapling, 							100, 	160, 	5, 	150, 	LiquidFuels.biomassFluid);
        
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.wheat), 			100, 	160,	5, 	150, 	LiquidFuels.biomassFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.carrot), 			150, 	240, 	5, 	150, 	LiquidFuels.biomassFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.netherStalkSeeds), 	200, 	480, 	5, 	100, 	LiquidFuels.biomassFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.potato), 			150, 	240, 	5, 	200, 	LiquidFuels.biomassFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.melon), 			20, 	160, 	5, 	40, 	LiquidFuels.biomassFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Block.cactus), 			20, 	160, 	5, 	40, 	LiquidFuels.biomassFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Block.leaves), 			100, 	160, 	5, 	150, 	LiquidFuels.biomassFluid);
        
        
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.reed), 				10, 	180, 	5, 	150, 	LiquidFuels.mashFluid);
        RecipeManager.masherRegistry.addMasherRecipe(new ItemStack(Item.sugar), 			10, 	180, 	5, 	150, 	LiquidFuels.mashFluid);
	}
	
	private static void initFermenter(){
		RecipeManager.fermenterRegistry.addFermenterRecipe(new FluidStack(LiquidFuels.mashFluid, 1000), 6, 6, new FluidStack(LiquidFuels.stillageFluid, 1000));
	}
	
	private static void initBoilerRecipies(){
		FMLInterModComms.sendMessage(Mods.Railcraft.getId(), "boiler-fuel-liquid", LiquidFuels.keroseneFluid.getName() + "@" + 163800);
		FMLInterModComms.sendMessage(Mods.Railcraft.getId(), "boiler-fuel-liquid", LiquidFuels.gasolineFluid.getName() + "@" + 6300);
		FMLInterModComms.sendMessage(Mods.Railcraft.getId(), "boiler-fuel-liquid", LiquidFuels.dieselFluid.getName() + "@" + 31500);
	}

	private static void initIC2Compat(){
		ic2.api.recipe.Recipes.semiFluidGenerator.addFluid(LiquidFuels.keroseneFluid.getName(), 2, 16);
		ic2.api.recipe.Recipes.semiFluidGenerator.addFluid(LiquidFuels.gasolineFluid.getName(), 1, 13);
		ic2.api.recipe.Recipes.semiFluidGenerator.addFluid(LiquidFuels.dieselFluid.getName(), 2, 20);
	}
	
	private static void initRefineryRecipies(){
		int ethanolToFuelRate = 5;
		int fuelFromEthanolRate = 2;

		int seedOilToFuelRate = 20;
		int fuelFromSeedOilRate = 1;

		int creosoteToFuelRate = 20;
		int fuelFromCreosotelRate = 1;
		
		String cat = "refineryRecipes";
		
		ethanolToFuelRate = LiquidFuels.inst.getInt(cat, "EthanolToFuelRate", "The amount of Ethanol, in millibuckets, needed to be refined: default is " + ethanolToFuelRate, ethanolToFuelRate);
		fuelFromEthanolRate = LiquidFuels.inst.getInt(cat, "fuelFromEthanolRate", "The amount of Fuel, in millibuckets, made from each amount of Ethanol: default is " + fuelFromEthanolRate, fuelFromEthanolRate);
		
		seedOilToFuelRate = LiquidFuels.inst.getInt(cat, "SeedOilToFuelRate", "The amount of Seed Oil, in millibuckets, needed to be refined: default is " + seedOilToFuelRate, seedOilToFuelRate);
		fuelFromSeedOilRate = LiquidFuels.inst.getInt(cat, "FuelFromSeedOilRate", "The amount of Fuel, in millibuckets, made from each amount of Seed Oil: default is " + fuelFromSeedOilRate, fuelFromSeedOilRate);
		
		creosoteToFuelRate = LiquidFuels.inst.getInt(cat, "CreosoteToFuelRate", "The amount of Creosote, in millibuckets, needed to be refined: default is " + creosoteToFuelRate, creosoteToFuelRate);
		fuelFromCreosotelRate = LiquidFuels.inst.getInt(cat, "FuelFromCreosotelRate", "The amount of Fuel, in millibuckets, made from each amount of Creosote: default is " + fuelFromCreosotelRate, fuelFromCreosotelRate);
		
        
       if(ethanolToFuelRate > 1 && fuelFromEthanolRate > 1)
    	   RefineryRecipes.addRecipe(FluidRegistry.getFluidStack("bioethanol", ethanolToFuelRate), FluidRegistry.getFluidStack("fuel", fuelFromEthanolRate), 12, 1);
       
       if(fuelFromCreosotelRate > 1 && fuelFromSeedOilRate > 1)
    	   RefineryRecipes.addRecipe(FluidRegistry.getFluidStack("seedoil", seedOilToFuelRate), FluidRegistry.getFluidStack("fuel", fuelFromSeedOilRate), 12, 1);
       
       if(creosoteToFuelRate > 1 && fuelFromCreosotelRate > 1)
    	   RefineryRecipes.addRecipe(FluidRegistry.getFluidStack("Creosote Oil", creosoteToFuelRate), FluidRegistry.getFluidStack("fuel", fuelFromCreosotelRate), 12, 1);
       
       
       RefineryRecipes.addRecipe(new FluidStack(LiquidFuels.dieselFluid, 1), FluidRegistry.getFluidStack("fuel", 1), 12, 1);
       RefineryRecipes.addRecipe(new FluidStack(LiquidFuels.gasolineFluid, 1), FluidRegistry.getFluidStack("fuel", 1), 12, 1);
       RefineryRecipes.addRecipe(new FluidStack(LiquidFuels.keroseneFluid, 1), FluidRegistry.getFluidStack("fuel", 1), 12, 1);
       
       
       IronEngineFuel.addFuel(LiquidFuels.keroseneFluid, 4, 47250);
       IronEngineFuel.addFuel(LiquidFuels.gasolineFluid, 8, 14766);
       IronEngineFuel.addFuel(LiquidFuels.dieselFluid, 4, 153563);
       
	}

	private static void initCrafting(){
		GameRegistry.addShapelessRecipe(LiquidFuels.asphaltBlock.getStack(16), new Object[]{Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel, Block.gravel, LiquidFuels.asphaltBucket.getStack()});
		
		
		
		boolean hasSteel = inOreDict("blockSteel");
		ItemStack buildcraftTank = ModConfigHelper.get("tile.tankBlock");
		ItemStack bcIronGear = ModConfigHelper.get("item.ironGearItem");
		ItemStack bcPower = ModConfigHelper.get("item.PipePowerGold");
		ItemStack bcLogic = ModConfigHelper.get("item.pipeGate");
		
		ItemStack forestryMachine = ModConfigHelper.get("item.sturdyMachine");
		
		ItemStack thermalMachine = ModConfigHelper.get("tile.thermalexpansion.machine");
		ItemStack thermalPower = ModConfigHelper.get("tile.thermalexpansion.conduit");
		
		ItemStack ic2Machine = ModConfigHelper.get("blockMachine", 0);
		ItemStack ic2Cable = ModConfigHelper.get("itemCable", 0);
		ItemStack ic2Logic = ModConfigHelper.get("itemPartCircuit");
		ItemStack ic2Motor = ModConfigHelper.get("itemRecipePart", 1);
		
		
		
		LeveledRecipe bladesRecipe = new LeveledRecipe(new RecipeMap(new String[]{"I I", " I ", "III"}, new Object[]{'I', Item.ingotIron}));
        bladesRecipe.addLevel(inOreDict("ingotSteel"), new Object[]{'I', "ingotSteel"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.masherBlades.getStack(), bladesRecipe.register()));
        
        
        LeveledRecipe multiTankRecipe = new LeveledRecipe(new RecipeMap(new String[]{"RTR", "RBR", "RRR"}, new Object[] {'R', Item.leather, 'T', Item.bucketEmpty, 'B', Block.blockIron}));
        multiTankRecipe.addLevel(hasSteel, new Object[]{'B', "blockSteel"});
        multiTankRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'T', buildcraftTank});
        multiTankRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'B', forestryMachine});
        multiTankRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'B', thermalMachine});
        multiTankRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'B', ic2Machine});
        multiTankRecipe.addLevel(inOreDict("itemRubber"), new Object[]{'R', "itemRubber"});
        multiTankRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'B', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.bioReactorMulit.getStack(4), multiTankRecipe.register()));
        
        
        LeveledRecipe masherRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "PMP"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'M', LiquidFuels.masherBlades.getStack()}));
        masherRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        masherRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
        masherRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        masherRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        masherRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        masherRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.masherBlock.getStack(), masherRecipe.register()));
        
        
        LeveledRecipe bioReactorRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', LiquidFuels.bioReactorMulit.getStack(), 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Block.mushroomBrown}));
        bioReactorRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        bioReactorRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic});
        bioReactorRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        bioReactorRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        bioReactorRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        bioReactorRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.bioReactorBlock.getStack(), bioReactorRecipe.register()));
		
		
        LeveledRecipe fermenterRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Block.mushroomBrown}));
        fermenterRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        fermenterRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
        fermenterRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        fermenterRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        fermenterRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        fermenterRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.fermenterBlock.getStack(), fermenterRecipe.register()));
        
        LeveledRecipe stillRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Block.glass}));
        stillRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        stillRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
        stillRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        stillRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        stillRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        stillRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.stillBlock.getStack(), stillRecipe.register()));
        
        LeveledRecipe boilerRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Item.blazePowder}));
        boilerRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        boilerRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
        boilerRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        boilerRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        boilerRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        boilerRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.boilerBlock.getStack(), boilerRecipe.register()));
        
        if(LiquidFuels.inst.getBoolean("WaterGenCraftable")){
        	LeveledRecipe waterGenRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Item.bucketWater}));
	        waterGenRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
	        waterGenRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
	        waterGenRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
	        waterGenRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
	        waterGenRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
	        waterGenRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
	        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.waterGenBlock.getStack(), waterGenRecipe.register()));
        }
        LeveledRecipe dryerRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Block.chest}));
        dryerRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        dryerRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
        dryerRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        dryerRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        dryerRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        dryerRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.dryerBlock.getStack(), dryerRecipe.register()));
        
        LeveledRecipe crackingBaseRecipe = new LeveledRecipe(new RecipeMap(new String[]{"GRG", "BIB", "WPW"}, new Object[]{'G', Item.ingotGold, 'R', Block.blockRedstone, 'B', Item.bucketEmpty, 'I', Block.blockIron, 'P', Block.pistonBase, 'W', Block.furnaceIdle}));
        crackingBaseRecipe.addLevel(hasSteel, new Object[]{'I', "blockSteel"});
        crackingBaseRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'G', bcPower, 'P', bcIronGear, 'R', bcLogic, 'B', buildcraftTank});
        crackingBaseRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'I', forestryMachine, 'P', "gearCopper"});
        crackingBaseRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'G', thermalPower, 'I', thermalMachine, 'P', "gearCopper"});
        crackingBaseRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'I', ic2Machine, 'R', ic2Logic, 'G', ic2Cable, 'P', ic2Motor});
        crackingBaseRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'I', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.crackingBaseBlock.getStack(), crackingBaseRecipe.register()));
        
        LeveledRecipe crackingTowerRecipe = new LeveledRecipe(new RecipeMap(new String[]{"RTR", "RBR", "RPR"}, new Object[] {'R', Item.leather, 'T', Item.bucketEmpty, 'P', Block.pistonBase, 'B', Block.blockIron}));
        crackingTowerRecipe.addLevel(hasSteel, new Object[]{'B', "blockSteel"});
        crackingTowerRecipe.addLevel(Mods.BuildcraftCore.isIn(), new Object[]{'T', buildcraftTank, 'P', bcIronGear});
        crackingTowerRecipe.addLevel(Mods.Forestry.isIn(), new Object[]{'B', forestryMachine, 'P', "gearCopper"});
        crackingTowerRecipe.addLevel(Mods.ThermalExpansion.isIn(), new Object[]{'B', thermalMachine, 'P', "gearCopper"});
        crackingTowerRecipe.addLevel(Mods.IC2.isIn(), new Object[]{'B', ic2Machine, 'P', ic2Motor});
        crackingTowerRecipe.addLevel(inOreDict("itemRubber"), new Object[]{'R', "itemRubber"});
        crackingTowerRecipe.addLevel(Mods.Greg.isIn(), new Object[]{'B', "craftingRawMachineTier00"});
        GameRegistry.addRecipe(new ShapedOreRecipe(LiquidFuels.crackingTowerBlock.getStack(), crackingTowerRecipe.register()));
	}
	
	private static boolean inOreDict(String ore){
		return !OreDictionary.getOres(ore).isEmpty();
	}
}
