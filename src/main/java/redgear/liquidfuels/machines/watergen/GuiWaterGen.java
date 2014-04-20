package redgear.liquidfuels.machines.watergen;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTank;

public class GuiWaterGen extends GuiBase<ContainerWaterGen> {

	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/WaterGen.png");

	public GuiWaterGen(ContainerWaterGen container) {
		super(container, texture);

		name = "gui.watergen.title";
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
