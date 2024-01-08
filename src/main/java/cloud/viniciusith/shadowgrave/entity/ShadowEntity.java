package cloud.viniciusith.shadowgrave.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.Collections;

public class ShadowEntity extends LivingEntity {
    private DefaultedList<ItemStack> items;
    private int xp;
    private GameProfile shadowOwner;

    public void setShadowOwner(GameProfile shadowOwner) {
        this.shadowOwner = shadowOwner;
    }

    public void setItems(DefaultedList<ItemStack> items) {
        this.items = items;
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

        nbt.putInt("ItemCount", this.items.size());
        nbt.put("Items", Inventories.writeNbt(new NbtCompound(), this.items, true));
        nbt.putInt("xp", xp);
        nbt.put("ShadowOwner", NbtHelper.writeGameProfile(new NbtCompound(), this.shadowOwner));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.items = DefaultedList.ofSize(nbt.getInt("ItemCount"));
        Inventories.readNbt(nbt.getCompound("Items"), this.items);
        this.xp = nbt.getInt("xp");
        this.shadowOwner = NbtHelper.toGameProfile(nbt.getCompound("ShadowOwner"));
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.singleton(new ItemStack(Items.AIR));
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {}

    @Override
    public Arm getMainArm() {
        return null;
    }


}
