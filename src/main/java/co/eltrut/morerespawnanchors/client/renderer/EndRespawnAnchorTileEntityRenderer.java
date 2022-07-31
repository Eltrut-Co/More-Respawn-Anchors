package co.eltrut.morerespawnanchors.client.renderer;

import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.core.Direction;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class EndRespawnAnchorTileEntityRenderer extends TheEndPortalRenderer<EndRespawnAnchorTileEntity> {

	private static final Random RANDOM = new Random(31100L);
	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> {
		return RenderType.endPortal();
	}).collect(ImmutableList.toImmutableList());

	public EndRespawnAnchorTileEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(EndRespawnAnchorTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn,
					   MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (tileEntityIn.getBlockState().getValue(tileEntityIn.getCharges()) > 0) {
			Matrix4f matrix4f = matrixStackIn.last().pose();
			this.renderSide(tileEntityIn, matrix4f, bufferIn.getBuffer(this.renderType()), 0.1860F, 0.8140F,
					getOffsetUp(), getOffsetUp(), 0.8140F, 0.8140F, 0.1860F, 0.1860F);
		}

	}

	private void renderSide(EndRespawnAnchorTileEntity entity, Matrix4f model, VertexConsumer vertices, float x1, float x2,
							float y1, float y2, float z1, float z2, float z3, float z4) {
		if (entity.shouldRenderFace(Direction.UP)) {
			vertices.vertex(model, x1, y1, z1).endVertex();
			vertices.vertex(model, x2, y1, z2).endVertex();
			vertices.vertex(model, x2, y2, z3).endVertex();
			vertices.vertex(model, x1, y2, z4).endVertex();
		}
	}

	@Override
	protected float getOffsetUp() {
		return 1.0F;
	}

}
