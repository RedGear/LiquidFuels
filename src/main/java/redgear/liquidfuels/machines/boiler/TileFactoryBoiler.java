package redgear.liquidfuels.machines.boiler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryBoiler implements ITileFactory {
	static int guiId = -1;

	public TileFactoryBoiler() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityBoiler.class, "TileEntityBoiler");
		}
	}

	@Override
	public TileEntityBoiler createTile() {
		return new TileEntityBoiler();
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
		if (tile instanceof TileEntityBoiler)
			return new GuiBoiler(new ContainerBoiler(inventoryPlayer, (TileEntityBoiler) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityBoiler)
			return new ContainerBoiler(inventoryPlayer, (TileEntityBoiler) tile);
		else
			return null;
	}
}
