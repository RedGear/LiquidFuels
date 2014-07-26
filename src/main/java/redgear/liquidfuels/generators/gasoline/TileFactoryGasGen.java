package redgear.liquidfuels.generators.gasoline;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileFactoryGasGen implements ITileFactory{
	
	
	public TileFactoryGasGen() {
		GameRegistry.registerTileEntity(TileEntityGasGen.class, "TileEntityGasGen");
	}

	@Override
	public TileEntity createTile() {
		return new TileEntityGasGen();
	}

	@Override
	public boolean hasGui() {
		return false;
	}

	@Override
	public int guiId() {
		return 0;
	}

	@Override
	public Object createGui(InventoryPlayer inventoryPlayer, TileEntity tile) {
		return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		return null;
	}

}
