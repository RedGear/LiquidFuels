package redgear.liquidfuels.machines.dryer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerDryer extends ContainerBase<TileEntityDryer>{

	public ContainerDryer(InventoryPlayer inventoryPlayer, TileEntityDryer tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
