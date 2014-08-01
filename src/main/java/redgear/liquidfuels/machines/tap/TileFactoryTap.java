package redgear.liquidfuels.machines.tap;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileFactoryTap implements ITileFactory{
	
	public TileFactoryTap(){
		GameRegistry.registerTileEntity(TileEntityTap.class, "TileEntityTap");
	}

	@Override
	public TileEntityTap createTile() {
		return new TileEntityTap();
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
