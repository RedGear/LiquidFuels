package redgear.liquidfuels.block;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import redgear.core.util.StringHelper;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberLog extends BlockLog {

	protected final String name;
	protected final String modName;
	private IIcon topIcon;

	public BlockRubberLog(String name) {
		super();
		this.name = name;
		modName = StringHelper.parseModAsset();
		GameRegistry.registerBlock(this, ItemBlock.class, name);
	}

	/**
	 * Override this function if your item has an unusual icon/multiple icons
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(modName + name);
		topIcon = par1IconRegister.registerIcon(modName + name + "Top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int p_150163_1_) {
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getTopIcon(int p_150161_1_) {
		return topIcon;
	}

	/**
	 * Returns the unlocalized name of the block with "tile." appended to the
	 * front.
	 */
	@Override
	public String getUnlocalizedName() {
		return "tile." + name;
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int p_149692_1_) {
		return 0;
	}

	@Override
	public int func_150162_k(int p_150162_1_) {
		return 0;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(LiquidFuels.rubberWoodDrained);
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

}
