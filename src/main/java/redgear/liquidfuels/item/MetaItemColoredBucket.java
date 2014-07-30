package redgear.liquidfuels.item;

import java.util.Map.Entry;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import redgear.core.item.MetaItemBucket;
import redgear.core.item.SubItemBucket;
import redgear.core.world.WorldLocation;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MetaItemColoredBucket extends MetaItemBucket {

	@SideOnly(Side.CLIENT)
	public static IIcon bucket;
	@SideOnly(Side.CLIENT)
	public static IIcon fluid;

	public MetaItemColoredBucket(String name) {
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack item, int par2) {
		SubItemBucket bucket = getMetaItem(item.getItemDamage());

		FluidStack stack = new FluidStack(bucket.fluid.getID(), 1000, item.getTagCompound());

		return par2 > 0 || bucket == null ? 16777215 : bucket.fluid.getColor(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	public FluidStack containedFluid(ItemStack item) {
		SubItemBucket bucket = getMetaItem(item.getItemDamage());
		return new FluidStack(bucket.fluid.getID(), 1000, item.getTagCompound());
	}

	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event) {
		//
		if (!event.world.isRemote && event.action == Action.RIGHT_CLICK_BLOCK && event.entityPlayer != null
				&& event.entityPlayer.getHeldItem() != null) {
			if (event.entityPlayer.getHeldItem().getItem() == Items.bucket) {
				WorldLocation loc = new WorldLocation(event.x, event.y, event.z, event.world);
				ForgeDirection side = ForgeDirection.getOrientation(event.face);
				TileEntity tile = loc.getTile();

				if (tile != null && tile instanceof IFluidHandler) {
					IFluidHandler fluidTile = (IFluidHandler) tile;
					FluidTankInfo[] tankInfo = fluidTile.getTankInfo(side);
					for (Entry<Integer, SubItemBucket> sub : items.entrySet())
						for (FluidTankInfo info : tankInfo)
							if (info != null && info.fluid != null && info.fluid.fluidID == sub.getValue().fluid.getID())
								if (fluidTile.canDrain(side, sub.getValue().fluid)) {

									FluidStack target = new FluidStack(sub.getValue().fluid.getID(), 1000,
											info.fluid.tag);
									FluidStack recive = fluidTile.drain(side, target, false);

									if (recive != null && recive.amount == 1000) {
										recive = fluidTile.drain(side, target, true);

										ItemStack result = new ItemStack(this, 1, sub.getKey());
										result.stackTagCompound = recive.tag == null ? new NBTTagCompound() : (NBTTagCompound) recive.tag.copy();

										int heldIndex = event.entityPlayer.inventory.currentItem;

										if (!event.entityPlayer.capabilities.isCreativeMode){
											if (--event.entityPlayer.getHeldItem().stackSize <= 0)
												event.entityPlayer.inventory
												.setInventorySlotContents(heldIndex, result);
											else
												if(!event.entityPlayer.inventory.addItemStackToInventory(result))
													event.entityPlayer.dropPlayerItemWithRandomChoice(result, false);
										
											event.entityPlayer.inventory.markDirty();
										}

										event.setCanceled(true);
										return;
									}
								}
				}
			}

			if (event.entityPlayer.getHeldItem().getItem() == this) {
				WorldLocation loc = new WorldLocation(event.x, event.y, event.z, event.world);
				ForgeDirection side = ForgeDirection.getOrientation(event.face);
				TileEntity tile = loc.getTile();

				if (tile != null && tile instanceof IFluidHandler) {
					IFluidHandler fluidTile = (IFluidHandler) tile;

					FluidStack contents = containedFluid(event.entityPlayer.getHeldItem());
					int accepted = fluidTile.fill(side, contents, false);

					if (accepted == contents.amount) {
						fluidTile.fill(side, contents, true);

						ItemStack result = new ItemStack(Items.bucket);
						int heldIndex = event.entityPlayer.inventory.currentItem;

						if (!event.entityPlayer.capabilities.isCreativeMode)
							if (--event.entityPlayer.getHeldItem().stackSize <= 0)
								event.entityPlayer.inventory.setInventorySlotContents(heldIndex, result);
							else
								event.entityPlayer.inventory.addItemStackToInventory(result);
						event.setCanceled(true);
						return;
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return bucket;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 0 ? fluid : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		bucket = par1IconRegister.registerIcon(modName + name + "Empty");
		fluid = par1IconRegister.registerIcon(modName + name + "Full");
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack item) {
		SubItemBucket bucket = getMetaItem(item.getItemDamage());

		FluidStack stack = new FluidStack(bucket.fluid.getID(), 1000, item.getTagCompound());
		
		return stack.getFluid().getLocalizedName(stack);
	}

}
