package redgear.liquidfuels.machines.tap;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;

public class TileFactoryTap implements ITileFactory{

	@Override
	public TileEntity createTile() {
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
