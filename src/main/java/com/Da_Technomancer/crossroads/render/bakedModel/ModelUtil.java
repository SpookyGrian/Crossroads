package com.Da_Technomancer.crossroads.render.bakedModel;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class ModelUtil{

	public static BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, EnumFacing side, TextureAtlasSprite sprite, VertexFormat vf){
		return createQuad(v1, v2, v3, v4, side, 16, 16, sprite, vf);
	}

	public static BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, EnumFacing side, int u, int v, TextureAtlasSprite sprite, VertexFormat vf){
		return createQuad(v1, v2, v3, v4, side, 0, 0, u, v, sprite, vf);
	}

	public static BakedQuad createQuad(Vec3d vertex0, Vec3d vertex1, Vec3d vertex2, Vec3d vertex3, @Nullable EnumFacing orient, int u, int v, int uEnd, int vEnd, TextureAtlasSprite sprite, VertexFormat vf){
		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(vf);
		builder.setQuadOrientation(orient);
		builder.setTexture(sprite);
		builder.setApplyDiffuseLighting(false);

		Vec3d normal = vertex2.subtract(vertex1).crossProduct(vertex0.subtract(vertex1));

		putVertex(builder, vf, normal, vertex0, u, v, sprite);
		putVertex(builder, vf, normal, vertex1, u, vEnd, sprite);
		putVertex(builder, vf, normal, vertex2, uEnd, vEnd, sprite);
		putVertex(builder, vf, normal, vertex3, uEnd, v, sprite);
		return builder.build();
	}

	private static void putVertex(UnpackedBakedQuad.Builder builder, VertexFormat vf, Vec3d normal, Vec3d pos, float u, float v, TextureAtlasSprite sprite){
		for(int e = 0; e < vf.getElementCount(); e++){
			switch(vf.getElement(e).getUsage()){
				case POSITION:
					builder.put(e, (float) pos.x, (float) pos.y, (float) pos.z, 1F);
					break;
				case COLOR:
					builder.put(e, 1F, 1F, 1F, 1F);
					break;
				case UV:
					if(vf.getElement(e).getIndex() == 0){
						u = sprite.getInterpolatedU(u);
						v = sprite.getInterpolatedV(v);
						builder.put(e, u, v, 0f, 1f);
						break;
					}
				case NORMAL:
					builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0F);
					break;
				default:
					builder.put(e);
					break;
			}
		}
	}
}