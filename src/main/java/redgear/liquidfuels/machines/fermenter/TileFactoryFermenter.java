package redgear.liquidfuels.machines.fermenter;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryFermenter implements ITileFactory {
	static int guiId = -1;

	public TileFactoryFermenter() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityFermenter.class, "TileEntityFermenter");
		}
	}

	@Override
	public TileEntityFermenter createTile() {
		return new TileEntityFermenter();
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
		if (tile instanceof TileEntityFermenter)
			return new GuiFermenter(new ContainerFermenter(inventoryPlayer, (TileEntityFermenter) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityFermenter)
			return new ContainerFermenter(inventoryPlayer, (TileEntityFermenter) tile);
		else
			return null;
	}
}
