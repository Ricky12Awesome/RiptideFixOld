package com.github.ricky12awesome.riptidefix.mixins;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class TridentFixMixin {

    @Inject(at = @At("RETURN"), method = "getDepthStrider(Lnet/minecraft/entity/LivingEntity;)I", cancellable = true)
    private static void getDepthStrider(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        final int result = EnchantmentHelper.getEquipmentLevel(Enchantments.DEPTH_STRIDER, entity);

        if (entity.isUsingRiptide()) {
            cir.setReturnValue(0);
        } else {
            cir.setReturnValue(result);
        }
    }
}
