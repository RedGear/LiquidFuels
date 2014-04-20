package redgear.liquidfuels.machines.bioreactor;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerBioReactor extends ContainerBase<TileEntityBioReactor> {

	public ContainerBioReactor(InventoryPlayer inventoryPlayer, TileEntityBioReactor tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
