package redgear.liquidfuels.machines.tower;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTank;

public class GuiCrackingTower extends GuiBase<ContainerCrackingTower>{
	
	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/WaterGen.png");

	public GuiCrackingTower(ContainerCrackingTower container) {
		super(container, texture);
		
		name = "gui.cracking.tower.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTank(this, 69, 13, myContainer.myTile.tank));
	}

	@Override
	protected void updateElements() {

	}
}
