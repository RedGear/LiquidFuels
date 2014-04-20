package redgear.liquidfuels.machines.fermenter;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerFermenter extends ContainerBase<TileEntityFermenter> {

	public ContainerFermenter(InventoryPlayer inventoryPlayer, TileEntityFermenter tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
