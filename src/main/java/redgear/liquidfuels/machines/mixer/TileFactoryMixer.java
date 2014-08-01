package redgear.liquidfuels.machines.mixer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileFactoryMixer implements ITileFactory {

	public TileFactoryMixer() {
		GameRegistry.registerTileEntity(TileEntityMixer.class, "TileEntityMixer");
	}

	@Override
	public TileEntityMixer createTile() {
		return new TileEntityMixer();
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
