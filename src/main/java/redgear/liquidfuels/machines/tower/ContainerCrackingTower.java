package redgear.liquidfuels.machines.tower;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerCrackingTower extends ContainerBase<TileEntityCrackingTower>{

	public ContainerCrackingTower(InventoryPlayer inventoryPlayer, TileEntityCrackingTower tile) {
		super(inventoryPlayer, tile);

		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
