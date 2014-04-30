package redgear.liquidfuels.machines.fluidboiler;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import redgear.core.render.ContainerBase;

public class ContainerFluidBoiler extends ContainerBase<TileEntityFluidBoiler>{

	public ContainerFluidBoiler(InventoryPlayer inventoryPlayer, TileEntityFluidBoiler tile) {
		super(inventoryPlayer, tile);
		
		bindPlayerInventory(inventoryPlayer);

		for (Slot s : tile.getSlots())
			addSlotToContainer(s);
	}

}
