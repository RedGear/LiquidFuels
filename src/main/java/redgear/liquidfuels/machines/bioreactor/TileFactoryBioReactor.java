package redgear.liquidfuels.machines.bioreactor;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryBioReactor implements ITileFactory {
	static int guiId = -1;

	public TileFactoryBioReactor() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityBioReactor.class, "TileEntityBioReactor");
		}
	}

	@Override
	public TileEntityBioReactor createTile() {
		return new TileEntityBioReactor();
	}

	@Override
	public boolean hasGui() {
		return true;
	}

	@Override
	public int guiId() {
		return guiId;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Object createGui(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBioReactor)
			return new GuiBioReactor(new ContainerBioReactor(inventoryPlayer, (TileEntityBioReactor) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBioReactor)
			return new ContainerBioReactor(inventoryPlayer, (TileEntityBioReactor) tile);
		else
			return null;
	}
}
