package cloud.viniciusith.shadowgrave.client;

import cloud.viniciusith.shadowgrave.ShadowGraveMod;
import cloud.viniciusith.shadowgrave.client.entity.ShadowEntityRenderer;
import cloud.viniciusith.shadowgrave.client.entity.ShadowModel;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static cloud.viniciusith.shadowgrave.ShadowGraveMod.MOD_ID;

public class ShadowGraveClient implements ClientModInitializer {
    public static final EntityModelLayer SHADOW_LAYER = new EntityModelLayer(new Identifier(
            MOD_ID,
            "shadow_entity"
    ), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ShadowGraveMod.SHADOW_ENTITY, ShadowEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(
                SHADOW_LAYER,
                ShadowModel::getTexturedModelData
        );
    }
}
