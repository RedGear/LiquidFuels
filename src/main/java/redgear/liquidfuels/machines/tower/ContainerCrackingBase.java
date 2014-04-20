package redgear.liquidfuels.machines.tower;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerCrackingBase extends ContainerBase<TileEntityCrackingBase>{

	public ContainerCrackingBase(InventoryPlayer inventoryPlayer, TileEntityCrackingBase tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
