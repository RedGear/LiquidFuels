package redgear.liquidfuels.machines.still;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerStill extends ContainerBase<TileEntityStill> {

	public ContainerStill(InventoryPlayer inventoryPlayer, TileEntityStill tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
