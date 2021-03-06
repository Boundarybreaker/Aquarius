package space.bbkr.aquarius.mixins;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTrident;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import space.bbkr.aquarius.Aquarius;

@Mixin(Item.class)
public class MixinTridentRepair {

    @Inject(method = "getIsRepairable", at = @At(value = "HEAD"), cancellable = true)
    private void getIsRepairable(ItemStack p_getIsRepairable_1_, ItemStack p_getIsRepairable_2_, CallbackInfoReturnable<Boolean> cir) {
        if (p_getIsRepairable_1_.getItem() instanceof ItemTrident) {
            cir.setReturnValue(p_getIsRepairable_2_.getItem() == Aquarius.PRISMARINE_ROD);
        }
    }
}
