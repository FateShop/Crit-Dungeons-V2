package com.github.noamm9.untitled.mixins;

import com.github.noamm9.untitled.crit.control.ClientControlAdapter;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MouseHandler.class, priority = 2000)
public class MixinCritMouse {
    @Inject(method = "onButton", at = @At("HEAD"))
    private void critObserveButton(long window, MouseButtonInfo button, int action, CallbackInfo ci) {
        ClientControlAdapter.button(button.button(), action);
    }
    @Inject(method = "onMove", at = @At("HEAD"))
    private void critObserveMotion(long window, double x, double y, CallbackInfo ci) {
        ClientControlAdapter.motion();
    }
    @Inject(method = "onScroll", at = @At("HEAD"))
    private void critObserveScroll(long window, double x, double y, CallbackInfo ci) {
        ClientControlAdapter.motion();
    }
}
