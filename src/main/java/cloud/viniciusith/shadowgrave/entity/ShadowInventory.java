package cloud.viniciusith.shadowgrave.entity;

import cloud.viniciusith.shadowgrave.ShadowGraveMod;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;

public class ShadowInventory implements Inventory {
    private final ShadowEntity shadow;
    public DefaultedList<ItemStack> main = DefaultedList.ofSize(36, ItemStack.EMPTY);
    public DefaultedList<ItemStack> armor = DefaultedList.ofSize(4, ItemStack.EMPTY);
    public DefaultedList<ItemStack> offHand = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public ShadowInventory(ShadowEntity shadow) {
        this.shadow = shadow;
    }


    @Override
    public int size() {
        return this.main.size() + this.armor.size() + this.offHand.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {}

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {}

    public void dropAll() {
        ShadowGraveMod.LOGGER.info("Dropping all items");

        for (int i = 0; i < this.main.size(); i++) {
            ItemStack itemStack = this.main.get(i);
            if (itemStack.isEmpty()) {
                continue;
            }
            this.main.set(i, ItemStack.EMPTY);
            this.dropStack(itemStack);
        }
        for (int i = 0; i < this.armor.size(); i++) {
            ItemStack itemStack = this.armor.get(i);
            if (itemStack.isEmpty()) {
                continue;
            }
            this.armor.set(i, ItemStack.EMPTY);
            this.dropStack(itemStack);
        }

        dropStack(this.offHand.get(0));
        this.offHand.set(0, ItemStack.EMPTY);
    }

    private void dropStack(ItemStack stack) {
        var itemEntity = new ItemEntity(this.shadow.getWorld(), this.shadow.getX(), this.shadow.getY(), this.shadow.getZ(), stack);
        itemEntity.setPickupDelay(10);

        float f = this.shadow.getRandom().nextFloat() * 0.5f;
        float g = this.shadow.getRandom().nextFloat() * ((float)Math.PI * 2);
        itemEntity.setVelocity(-MathHelper.sin(g) * f, 0.2f, MathHelper.cos(g) * f);

        this.shadow.getWorld().spawnEntity(itemEntity);
    }

    public NbtList writeNbt(NbtList nbtList) {
        NbtCompound nbtCompound;
        int i;
        for (i = 0; i < this.main.size(); ++i) {
            if (this.main.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)i);
            this.main.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        for (i = 0; i < this.armor.size(); ++i) {
            if (this.armor.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + 100));
            this.armor.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        for (i = 0; i < this.offHand.size(); ++i) {
            if (this.offHand.get(i).isEmpty()) continue;
            nbtCompound = new NbtCompound();
            nbtCompound.putByte("Slot", (byte)(i + 150));
            this.offHand.get(i).writeNbt(nbtCompound);
            nbtList.add(nbtCompound);
        }
        return nbtList;
    }

    public void readNbt(NbtList nbtList) {
        ShadowGraveMod.LOGGER.info("Reading items");
        ShadowGraveMod.LOGGER.info(nbtList.toString());

        this.main.clear();
        this.armor.clear();
        this.offHand.clear();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 0xFF;
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            if (itemStack.isEmpty()) continue;
            if (j < this.main.size()) {
                this.main.set(j, itemStack);
                continue;
            }
            if (j >= 100 && j < this.armor.size() + 100) {
                this.armor.set(j - 100, itemStack);
                continue;
            }
            if (j < 150 || j >= this.offHand.size() + 150) continue;
            this.offHand.set(j - 150, itemStack);
        }
    }
}
