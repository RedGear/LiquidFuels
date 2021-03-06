package redgear.liquidfuels.machines.dryer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryDryer implements ITileFactory {
	static int guiId = -1;

	public TileFactoryDryer() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityDryer.class, "TileEntityDryer");
		}
	}

	@Override
	public TileEntityDryer createTile() {
		return new TileEntityDryer();
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
		if (tile instanceof TileEntityDryer)
			return new GuiDryer(new ContainerDryer(inventoryPlayer, (TileEntityDryer) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityDryer)
			return new ContainerDryer(inventoryPlayer, (TileEntityDryer) tile);
		else
			return null;
	}
}
