package redgear.liquidfuels.machines.molder;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileFactoryMolder implements ITileFactory {

	public TileFactoryMolder() {
		GameRegistry.registerTileEntity(TileEntityMolder.class, "TileEntityMolder");
	}

	@Override
	public TileEntity createTile() {
		return new TileEntityMolder();
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
