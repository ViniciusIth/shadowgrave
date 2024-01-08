package cloud.viniciusith.shadowgrave;

import cloud.viniciusith.shadowgrave.entity.ShadowEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class ShadowGraveMod implements ModInitializer {
    public static final String MOD_ID = "shadowgrave";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);
    public static final EntityType<ShadowEntity> SHADOW_ENTITY = FabricEntityTypeBuilder.create(
            SpawnGroup.MISC,
            ShadowEntity::new
    ).dimensions(EntityType.DROWNED.getDimensions()).build();

    public static DefaultAttributeContainer.Builder createShadowAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, .1D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public void onInitialize() {
        Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier(MOD_ID, "shadow_entity"),
                SHADOW_ENTITY
        );

        FabricDefaultAttributeRegistry.register(SHADOW_ENTITY, createShadowAttributes());
    }
}
