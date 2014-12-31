package redgear.liquidfuels.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import redgear.core.api.item.ISimpleItem;
import redgear.core.util.SimpleItem;
import redgear.core.util.SimpleOre;
import redgear.core.world.WorldLocation;
import redgear.core.world.gen.VeinHelper;
import redgear.geocraft.api.MineManager;
import redgear.geocraft.api.mine.MineCylinder;
import redgear.liquidfuels.core.LiquidFuels;

public class MineOilShale extends MineCylinder {
	
	public MineOilShale() {
		super("OilShale", new SimpleItem(LiquidFuels.oilShale), new SimpleOre("stone", new SimpleItem(Blocks.stone)), 8, 4, 16);
	}
	
	public static void register(){
		MineManager.oreRegistry.registerMine(new MineOilShale());
	}

	@Override
	protected void generateVein(WorldLocation start, ISimpleItem block, ISimpleItem target, Random rand, int size) {
		VeinHelper.generateSphere(start, block, target, rand, size);
	}

}
