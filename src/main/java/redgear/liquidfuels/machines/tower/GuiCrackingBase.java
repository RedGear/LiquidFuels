package redgear.liquidfuels.machines.tower;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTank;

public class GuiCrackingBase extends GuiBase<ContainerCrackingBase>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/CrackingBase.png");

	public GuiCrackingBase(ContainerCrackingBase container) {
		super(container, texture);
		
		name = "gui.cracking.base.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTank(this, 48, 13, myContainer.myTile.steamTank).setGauge(1));
		addElement(new ElementFluidTank(this, 97, 13, myContainer.myTile.oilTank).setGauge(1));
	}

	@Override
	protected void updateElements() {

	}
}
