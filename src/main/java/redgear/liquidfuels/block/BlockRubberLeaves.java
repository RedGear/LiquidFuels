package redgear.liquidfuels.block;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import redgear.core.util.StringHelper;
import redgear.liquidfuels.core.LiquidFuels;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberLeaves extends BlockLeaves {

	protected final String name;
	protected final String modName;

	public BlockRubberLeaves(String name) {
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
	}

	/**
	 * Returns the unlocalized name of the block with "tile." appended to the
	 * front.
	 */
	@Override
	public String getUnlocalizedName() {
		return "tile." + name;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return blockIcon;
	}

	@Override
	public String[] func_150125_e() {
		return new String[] {name };
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(LiquidFuels.rubberSapling);
	}

	@Override
	public boolean isOpaqueCube() {
		return Blocks.leaves.isOpaqueCube();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess iba, int x, int y, int z, int side) {
		return isOpaqueCube() ? super.shouldSideBeRendered(iba, x, y, z, side) : true;
	}

}
