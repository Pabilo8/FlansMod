package co.uk.flansmods.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;

import org.lwjgl.opengl.GL11;

import co.uk.flansmods.common.EntityDriveable;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler
{
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		
		// CAPTURE MOUSE INPUT!
		if (mc.currentScreen == null) // no screen is open. just the game screen.
		{
			MouseHelper mouse = mc.mouseHelper;
			Entity ridden = mc.thePlayer.ridingEntity;

			if (ridden instanceof EntityDriveable)
			{
				EntityDriveable entity = (EntityDriveable) ridden;
				entity.onMouseMoved(mouse.deltaX, mouse.deltaY);
			}
		}
	}

	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if (FlansModClient.zoomOverlay != null && ModLoader.isGUIOpen(null))
		{
			ScaledResolution scaledresolution = new ScaledResolution(FlansModClient.minecraft.gameSettings, FlansModClient.minecraft.displayWidth, FlansModClient.minecraft.displayHeight);
			int i = scaledresolution.getScaledWidth();
			int j = scaledresolution.getScaledHeight();
			FlansModClient.minecraft.entityRenderer.setupOverlayRendering();
			GL11.glEnable(3042 /* GL_BLEND */);
			GL11.glDisable(2929 /* GL_DEPTH_TEST */);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3008 /* GL_ALPHA_TEST */);
			GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, FlansModClient.minecraft.renderEngine.getTexture("/gui/" + FlansModClient.zoomOverlay + ".png"));
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(i / 2 - 2 * j, j, -90D, 0.0D, 1.0D);
			tessellator.addVertexWithUV(i / 2 + 2 * j, j, -90D, 1.0D, 1.0D);
			tessellator.addVertexWithUV(i / 2 + 2 * j, 0.0D, -90D, 1.0D, 0.0D);
			tessellator.addVertexWithUV(i / 2 - 2 * j, 0.0D, -90D, 0.0D, 0.0D);
			tessellator.draw();
			GL11.glDepthMask(true);
			GL11.glEnable(2929 /* GL_DEPTH_TEST */);
			GL11.glEnable(3008 /* GL_ALPHA_TEST */);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		if (FlansModClient.errorString != null && FlansModClient.errorStringTimer > 0)
		{
			ScaledResolution scaledresolution = new ScaledResolution(FlansModClient.minecraft.gameSettings, FlansModClient.minecraft.displayWidth, FlansModClient.minecraft.displayHeight);
			int i = scaledresolution.getScaledWidth();
			int j = scaledresolution.getScaledHeight();
			GL11.glEnable(3042 /* GL_BLEND */);
			GL11.glDisable(2929 /* GL_DEPTH_TEST */);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(770, 771);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(3008 /* GL_ALPHA_TEST */);
			GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, FlansModClient.minecraft.renderEngine.getTexture("/gui/gui.png"));
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(0, 20, -90D, 0D, 66D / 256D);
			tessellator.addVertexWithUV(200, 20, -90D, 200D / 256D, 66D / 256D);
			tessellator.addVertexWithUV(200, 0D, -90D, 200D / 256D, 46D / 256D);
			tessellator.addVertexWithUV(0, 0D, -90D, 0D, 46D / 256D);
			tessellator.draw();
			GL11.glDepthMask(true);
			GL11.glEnable(2929 /* GL_DEPTH_TEST */);
			GL11.glEnable(3008 /* GL_ALPHA_TEST */);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			FlansModClient.minecraft.fontRenderer.drawString(FlansModClient.errorString, 6, 6, 0x404040);
		}
	}

	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	public String getLabel()
	{
		return "FlansModClient";
	}

}