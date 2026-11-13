package com.github.noamm9.untitled.mixins;

import com.github.noamm9.untitled.crit.control.ClientControlAdapter;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyboardHandler.class, priority = 2000)
public class MixinCritKeyboard {
    @Inject(method = "keyPress", at = @At("HEAD"))
    private void critObserveKey(long window, int action, KeyEvent event, CallbackInfo ci) {
        ClientControlAdapter.key(event.key(), action);
    }
}
