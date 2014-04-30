package redgear.liquidfuels.machines.fluidboiler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import redgear.core.network.CoreGuiHandler;
import redgear.core.tile.ITileFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileFactoryFluidBoiler implements ITileFactory {
	static int guiId = -1;

	public TileFactoryFluidBoiler() {
		if (guiId == -1) {
			guiId = CoreGuiHandler.addGuiMap(this);
			GameRegistry.registerTileEntity(TileEntityFluidBoiler.class, "TileEntityFluidBoiler");
		}
	}

	@Override
	public TileEntityFluidBoiler createTile() {
		return new TileEntityFluidBoiler();
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
		if (tile instanceof TileEntityFluidBoiler)
			return new GuiFluidBoiler(new ContainerFluidBoiler(inventoryPlayer, (TileEntityFluidBoiler) tile));
		else
			return null;
	}

	@Override
	public Object createContainer(InventoryPlayer inventoryPlayer, TileEntity tile) {
		if (tile instanceof TileEntityFluidBoiler)
			return new ContainerFluidBoiler(inventoryPlayer, (TileEntityFluidBoiler) tile);
		else
			return null;
	}

}
