package redgear.liquidfuels.machines.tower;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryCrackingBase implements ITileFactory {
	static int guiId = -1;

	public TileFactoryCrackingBase() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityCrackingBase.class, "TileEntityCrackingBase");
		}
	}

	@Override
	public TileEntityCrackingBase createTile() {
		return new TileEntityCrackingBase();
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
		if (tile instanceof TileEntityCrackingBase)
			return new GuiCrackingBase(new ContainerCrackingBase(inventoryPlayer, (TileEntityCrackingBase) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityCrackingBase)
			return new ContainerCrackingBase(inventoryPlayer, (TileEntityCrackingBase) tile);
		else
			return null;
	}
}
