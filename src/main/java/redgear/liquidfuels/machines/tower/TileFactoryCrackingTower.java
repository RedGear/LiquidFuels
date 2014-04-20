package redgear.liquidfuels.machines.tower;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryCrackingTower implements ITileFactory {
	static int guiId = -1;

	public TileFactoryCrackingTower() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityCrackingTower.class, "TileEntityCrackingTower");
		}
	}

	@Override
	public TileEntityCrackingTower createTile() {
		return new TileEntityCrackingTower();
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
		if (tile instanceof TileEntityCrackingTower)
			return new GuiCrackingTower(new ContainerCrackingTower(inventoryPlayer, (TileEntityCrackingTower) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityCrackingTower)
			return new ContainerCrackingTower(inventoryPlayer, (TileEntityCrackingTower) tile);
		else
			return null;
	}
}
