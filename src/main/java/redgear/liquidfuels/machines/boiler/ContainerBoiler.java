package redgear.liquidfuels.machines.boiler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerBoiler extends ContainerBase<TileEntityBoiler>{

	public ContainerBoiler(InventoryPlayer inventoryPlayer, TileEntityBoiler tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
