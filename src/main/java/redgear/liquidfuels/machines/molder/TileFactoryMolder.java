package redgear.liquidfuels.machines.molder;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryMolder implements ITileFactory {
	static int guiId = -1;

	public TileFactoryMolder() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityMolder.class, "TileEntityMolder");
		}
	}

	@Override
	public TileEntity createTile() {
		return new TileEntityMolder();
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
		if (tile instanceof TileEntityMolder)
			return new GuiMolder(new ContainerMolder(inventoryPlayer, (TileEntityMolder) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityMolder)
			return new ContainerMolder(inventoryPlayer, (TileEntityMolder) tile);
		else
			return null;
	}
}
