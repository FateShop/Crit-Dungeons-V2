package com.github.noamm9.untitled.mixins;

import com.github.noamm9.untitled.crit.control.ClientControlAdapter;
import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinCritWindow {
    @Inject(method = "onFocus", at = @At("HEAD"))
    private void critFocus(long window, boolean focused, CallbackInfo ci) { ClientControlAdapter.focus(focused); }
}
