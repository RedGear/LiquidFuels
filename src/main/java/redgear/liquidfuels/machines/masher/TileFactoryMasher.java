package redgear.liquidfuels.machines.masher;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryMasher implements ITileFactory {
	static int guiId = -1;

	public TileFactoryMasher() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityMasher.class, "TileEntityMasher");
		}
	}

	@Override
	public TileEntityMasher createTile() {
		return new TileEntityMasher();
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
		if (tile instanceof TileEntityMasher)
			return new GuiMasher(new ContainerMasher(inventoryPlayer, (TileEntityMasher) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityMasher)
			return new ContainerMasher(inventoryPlayer, (TileEntityMasher) tile);
		else
			return null;
	}
}
