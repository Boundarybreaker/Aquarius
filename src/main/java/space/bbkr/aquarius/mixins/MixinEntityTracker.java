package space.bbkr.aquarius.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import space.bbkr.aquarius.EntityHookshot;

@Mixin(EntityTracker.class)
public class MixinEntityTracker
{
    @Inject(method = "track", at = @At("RETURN"))
    public void trackAquariusEntity(Entity entityIn, CallbackInfo ci) {
        if (entityIn instanceof EntityHookshot) {
            ((EntityTracker) (Object) this).track(entityIn, 160, 20, true);
        }
    }
}
