package cloud.viniciusith.shadowgrave.client.entity;

import cloud.viniciusith.shadowgrave.ShadowGraveMod;
import cloud.viniciusith.shadowgrave.client.ShadowGraveClient;
import cloud.viniciusith.shadowgrave.entity.ShadowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.util.Identifier;

public class ShadowEntityRenderer extends LivingEntityRenderer<ShadowEntity, ShadowModel<ShadowEntity>> {
    public ShadowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ShadowModel<>(context.getPart(ShadowGraveClient.SHADOW_LAYER)), .5F);
    }

    @Override
    public Identifier getTexture(ShadowEntity entity) {
        return new Identifier(ShadowGraveMod.MOD_ID, "textures/entity/shadow.png");
    }
}
