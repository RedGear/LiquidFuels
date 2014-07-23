package redgear.liquidfuels.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import redgear.core.util.StringHelper;
import redgear.liquidfuels.core.LiquidFuels;
import redgear.liquidfuels.world.RubberTreeGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRubberSapling extends BlockSapling {

	protected final String name;
	protected final String modName;
	private static final RubberTreeGenerator treeGen = new RubberTreeGenerator(true);

	public BlockRubberSapling(String name) {
		this.name = name;
		modName = StringHelper.parseModAsset();
		GameRegistry.registerBlock(this, ItemBlock.class, name);
		this.setHardness(0.0F).setStepSound(soundTypeGrass);
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
	public IIcon getIcon(int side, int metadata)
	{
		return blockIcon;
	}
	
	@Override
	public int damageDropped(int par1)
	{
		return 0;
	}

	/**
	 * Grow tree.
	 */
	@Override
	public void func_149878_d(World world, int x, int y, int z, Random rand) {
		LiquidFuels.inst.logDebug("Trying Tree Growth...");
		
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, x, y, z)){
			LiquidFuels.inst.logDebug("Tree failed to grow due to: Sapling Grow Event");
			return;
		}

		if (world.isRemote){
			LiquidFuels.inst.logDebug("Tree failed to grow due to: Client side");
			return;
		}

		world.setBlockToAir(x, y, z);
		if(!treeGen.growTree(world, x, y, z, rand))
			world.setBlock(x, y, z, this, 0, 4);
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
    }
}
