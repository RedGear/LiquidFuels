package redgear.liquidfuels.machines.bioreactor;

import net.minecraft.util.ResourceLocation;
import redgear.core.render.GuiBase;
import redgear.core.render.gui.element.ElementFluidTank;

public class GuiBioReactor extends GuiBase<ContainerBioReactor> {

	private static final ResourceLocation texture = new ResourceLocation("redgear_liquidfuels:textures/gui/BioReactor.png");

	public GuiBioReactor(ContainerBioReactor container) {
		super(container, texture);

		name = "gui.bioreactor.title";
	}

	@Override
	public void initGui() {
		super.initGui();

		addElement(new ElementFluidTank(this, 69, 13, myContainer.myTile.tank).setGauge(1));
	}

	@Override
	protected void updateElements() {

	}

}
