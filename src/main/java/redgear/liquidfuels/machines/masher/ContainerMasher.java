package redgear.liquidfuels.machines.masher;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerMasher extends ContainerBase<TileEntityMasher> {

	public ContainerMasher(InventoryPlayer inventoryPlayer, TileEntityMasher tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
