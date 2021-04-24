package co.eltrut.morerespawnanchors.client.renderer;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import co.eltrut.morerespawnanchors.common.tileentities.EndRespawnAnchorTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.EndPortalTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix4f;

public class EndRespawnAnchorTileEntityRenderer extends EndPortalTileEntityRenderer<EndRespawnAnchorTileEntity> {

	private static final Random RANDOM = new Random(31100L);
	private static final List<RenderType> RENDER_TYPES = IntStream.range(0, 16).mapToObj((p_228882_0_) -> {
		return RenderType.endPortal(p_228882_0_ + 1);
	}).collect(ImmutableList.toImmutableList());

	public EndRespawnAnchorTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	@Override
	public void render(EndRespawnAnchorTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
			IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		if (tileEntityIn.getBlockState().getValue(tileEntityIn.getCharges()) > 0) {
			RANDOM.setSeed(31100L);
			double d0 = tileEntityIn.getBlockPos().distSqr(this.renderer.camera.getPosition(), true);
			int i = this.getPasses(d0);
			float f = this.getOffset();
			Matrix4f matrix4f = matrixStackIn.last().pose();
			this.renderCube(tileEntityIn, f, 0.15F, matrix4f, bufferIn.getBuffer(RENDER_TYPES.get(0)));

			for (int j = 1; j < i; ++j) {
				this.renderCube(tileEntityIn, f, 2.0F / (float) (18 - j), matrix4f,
						bufferIn.getBuffer(RENDER_TYPES.get(j)));
			}
		}

	}

	private void renderCube(EndRespawnAnchorTileEntity tileEntityIn, float p_228883_2_, float p_228883_3_,
			Matrix4f p_228883_4_, IVertexBuilder p_228883_5_) {
		float f = (RANDOM.nextFloat() * 0.5F + 0.1F) * p_228883_3_;
		float f1 = (RANDOM.nextFloat() * 0.5F + 0.4F) * p_228883_3_;
		float f2 = (RANDOM.nextFloat() * 0.5F + 0.5F) * p_228883_3_;
		this.renderFace(tileEntityIn, p_228883_4_, p_228883_5_, p_228883_2_, p_228883_2_, f, f1, f2);
	}

	private void renderFace(EndRespawnAnchorTileEntity tileEntityIn, Matrix4f matrix4f, IVertexBuilder vertexBuilder, float h, float i, float n, float o, float p) {
		if (tileEntityIn.shouldRenderFace(Direction.UP)) {
			vertexBuilder.vertex(matrix4f, 0.1860F, h, 0.8140F).color(n, o, p, 1.0F).endVertex();
			vertexBuilder.vertex(matrix4f, 0.8140F, h, 0.8140F).color(n, o, p, 1.0F).endVertex();
            vertexBuilder.vertex(matrix4f, 0.8140F, i, 0.1860F).color(n, o, p, 1.0F).endVertex();
            vertexBuilder.vertex(matrix4f, 0.1860F, i, 0.1860F).color(n, o, p, 1.0F).endVertex();
		}
	}

	@Override
	protected int getPasses(double p_191286_1_) {
		return super.getPasses(p_191286_1_ * 3.0D) + 1;
	}

	@Override
	protected float getOffset() {
		return 1.0F;
	}

}
