package redgear.liquidfuels.machines.watergen;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerWaterGen extends ContainerBase<TileEntityWaterGen> {

	public ContainerWaterGen(InventoryPlayer inventoryPlayer, TileEntityWaterGen tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
