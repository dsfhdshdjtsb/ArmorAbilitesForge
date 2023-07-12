package com.dsfhdshdjtsb.ArmorAbilities.mixin;

import com.dsfhdshdjtsb.ArmorAbilities.util.TimerAccess;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class AabiliitesEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {
    protected AabiliitesEntityRendererMixin(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V")
    private void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pEntity instanceof Player) {
            TimerAccess timerAccess = (TimerAccess) pEntity;
            int fuse = (int) timerAccess.aabilities_getFuse();
            if (fuse >= 0) {
                pMatrixStack.pushPose();
                pMatrixStack.translate(0.0F, 0.5F, 0.0F);
                int i = fuse;
                if ((float) i - pPartialTicks + 1.0F < 10.0F) {
                    float f = 1.0F - ((float) i - pPartialTicks + 1.0F) / 10.0F;
                    f = Mth.clamp(f, 0.0F, 1.0F);
                    f *= f;
                    f *= f;
                    float f1 = 1.0F + f * 0.3F;
                    pMatrixStack.scale(f1, f1, f1);
                }

                pMatrixStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                pMatrixStack.translate(-0.5F, -0.5F, 0.5F);
                pMatrixStack.mulPose(Axis.YP.rotationDegrees(90.0F));
                TntMinecartRenderer.renderWhiteSolidBlock(Minecraft.getInstance().getBlockRenderer(), Blocks.TNT.defaultBlockState(), pMatrixStack, pBuffer, pPackedLight, i / 5 % 2 == 0);
                pMatrixStack.popPose();
                ci.cancel();
            }
            if(timerAccess.aabilities_getShouldAnvilRender())
            {
                BlockState blockstate = Blocks.ANVIL.defaultBlockState();
                if (blockstate.getRenderShape() == RenderShape.MODEL) {
                    Level level = pEntity.level();
                    if (blockstate != level.getBlockState(pEntity.blockPosition()) && blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                        pMatrixStack.pushPose();
                        BlockPos blockpos = BlockPos.containing(pEntity.getX(), pEntity.getBoundingBox().maxY, pEntity.getZ());
                        pMatrixStack.translate(-0.5D, 0.0D, -0.5D);
                        var model = Minecraft.getInstance().getBlockRenderer().getBlockModel(blockstate);
                        for (var renderType : model.getRenderTypes(blockstate, RandomSource.create(blockstate.getSeed(pEntity.blockPosition())), net.minecraftforge.client.model.data.ModelData.EMPTY))
                            Minecraft.getInstance().getBlockRenderer().getModelRenderer().tesselateBlock(level, model, blockstate, blockpos, pMatrixStack, pBuffer.getBuffer(renderType), false, RandomSource.create(), blockstate.getSeed(pEntity.blockPosition()), OverlayTexture.NO_OVERLAY, net.minecraftforge.client.model.data.ModelData.EMPTY, renderType);
                        pMatrixStack.popPose();
                        ci.cancel();
                    }
                }
            }
        }
    }

}
