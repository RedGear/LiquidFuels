package redgear.liquidfuels.machines.molder;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import redgear.core.inventory.InvSlot;
import redgear.core.util.SimpleItem;
import redgear.liquidfuels.recipes.MolderRecipe;

public class MoldSlot extends InvSlot {

	public MoldSlot(IInventory inventory, int x, int y) {
		super(inventory, x, y);
	}
	
	
	/**
	 * Can be used in children to filter the stacks. 
	 * @param stack
	 * @return
	 */
	public boolean stackAllowed(ItemStack stack){
		return MolderRecipe.isValidMold(new SimpleItem(stack));
	}

}
