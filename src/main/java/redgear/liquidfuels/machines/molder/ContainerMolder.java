package redgear.liquidfuels.machines.molder;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerMolder  extends ContainerBase<TileEntityMolder> {

	public ContainerMolder(InventoryPlayer inventoryPlayer, TileEntityMolder tile) {
		super(inventoryPlayer, tile);
		
		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
