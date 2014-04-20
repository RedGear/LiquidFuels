package redgear.liquidfuels.machines.watergen;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryWaterGen implements ITileFactory {
	static int guiId = -1;

	public TileFactoryWaterGen() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityWaterGen.class, "TileEntityWaterGen");
		}
	}

	@Override
	public TileEntityWaterGen createTile() {
		return new TileEntityWaterGen();
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
		if (tile instanceof TileEntityWaterGen)
			return new GuiWaterGen(new ContainerWaterGen(inventoryPlayer, (TileEntityWaterGen) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityWaterGen)
			return new ContainerWaterGen(inventoryPlayer, (TileEntityWaterGen) tile);
		else
			return null;
	}
}
