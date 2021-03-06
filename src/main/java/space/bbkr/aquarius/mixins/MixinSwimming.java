package space.bbkr.aquarius.mixins;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.aquarius.Aquarius;

@Mixin(EntityPlayer.class)
public abstract class MixinSwimming extends EntityLivingBase {

    @Shadow public abstract boolean isSwimming();

    public MixinSwimming(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "updateTurtleHelmet", at = @At("HEAD"))
    private void updateTurtleHelmet(CallbackInfo ci) {
        ItemStack stackFeet = this.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        if (stackFeet.getItem() == Aquarius.FLIPPERS) {
            if (this.isInWater()) this.addPotionEffect(new PotionEffect(MobEffects.DOLPHINS_GRACE, 20, 0, true, false, true));
            else this.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 0, true, false, true));
        }
    }

    @Override
    public boolean canSwim() {
        return (this.eyesInWater && this.isInWater()) || this.isPotionActive(Aquarius.ATLANTEAN);
    }

    @Override
    public boolean isWet() {
        if (this.isPotionActive(Aquarius.ATLANTEAN)) return true;
        return super.isWet();
    }

    @Inject(method = "updateSwimming", at = @At("TAIL"))
    public void updateAirSwimming(CallbackInfo ci) {
        if (this.isPotionActive(Aquarius.ATLANTEAN)) {
            this.setSwimming(this.isSprinting() && !this.isRiding());
            this.inWater = this.isSwimming();
            if (this.isSwimming()) {
                this.fallDistance = 0.0F;
                Vec3d look = this.getLookVec();
                move(MoverType.SELF, look.x/4, look.y/4, look.z/4);
            }
        }
    }

}
