package redgear.liquidfuels.machines.still;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryStill implements ITileFactory {
	static int guiId = -1;

	public TileFactoryStill() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityStill.class, "TileEntityStill");
		}
	}

	@Override
	public TileEntityStill createTile() {
		return new TileEntityStill();
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
		if (tile instanceof TileEntityStill)
			return new GuiStill(new ContainerStill(inventoryPlayer, (TileEntityStill) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityStill)
			return new ContainerStill(inventoryPlayer, (TileEntityStill) tile);
		else
			return null;
	}
}
