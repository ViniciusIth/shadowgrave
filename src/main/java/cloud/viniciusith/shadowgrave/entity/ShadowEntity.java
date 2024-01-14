package cloud.viniciusith.shadowgrave.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShadowEntity extends LivingEntity {
    private final ShadowInventory inventory = new ShadowInventory(this);
    private int xp;
    private GameProfile shadowOwner;

    public void setShadowOwner(GameProfile shadowOwner) {
        this.shadowOwner = shadowOwner;
    }

    public void setInventories(DefaultedList<ItemStack> main, DefaultedList<ItemStack> armor, DefaultedList<ItemStack> offHand) {
        this.inventory.main = main;
        this.inventory.armor = armor;
        this.inventory.offHand = offHand;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public ShadowEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put("Inventory", this.inventory.writeNbt(new NbtList()));
        nbt.putInt("xp", xp);
        nbt.put("ShadowOwner", NbtHelper.writeGameProfile(new NbtCompound(), this.shadowOwner));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        NbtList nbtList = nbt.getList("Inventory", NbtElement.COMPOUND_TYPE);
        this.inventory.readNbt(nbtList);
        this.xp = nbt.getInt("xp");
        this.shadowOwner = NbtHelper.toGameProfile(nbt.getCompound("ShadowOwner"));
    }

    @Override
    protected void dropInventory() {
        this.inventory.dropAll();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return this.inventory.armor;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        if (inventory.getStack(0) != null) {
            return inventory.getStack(0);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {}

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getSource() == null || !(source.getSource() instanceof PlayerEntity player)) {
            return false;
        }

        return super.damage(source, amount);
    }

    @Override
    protected void onKilledBy(@Nullable LivingEntity adversary) {
        if (!(adversary instanceof PlayerEntity player)) {
            return;
        }

        if (player.getGameProfile().equals(this.shadowOwner)) {
            this.inventory.dropAll();
        }
    }

    @Override
    protected boolean shouldAlwaysDropXp() {
        return true;
    }

    @Override
    public int getXpToDrop() {
        return xp;
    }

    @Override
    public boolean shouldRenderName() {
        return false;
    }
}
