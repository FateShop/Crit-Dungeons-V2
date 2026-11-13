package com.github.noamm9.untitled.mixins;

import com.github.noamm9.utils.ActionUtils;
import java.util.concurrent.PriorityBlockingQueue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ActionUtils.class, remap = false)
public interface ActionUtilsAccessor {
    @Accessor("actionQueue") static PriorityBlockingQueue<?> critQueue() { throw new AssertionError(); }
    @Accessor("running") static boolean critRunning() { throw new AssertionError(); }
    @Accessor("lock") static Object critLock() { throw new AssertionError(); }
    @Accessor("isBlocked") static boolean critBlocked() { throw new AssertionError(); }
}
