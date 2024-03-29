package me.lor3mipsum.next.mixin;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockHitResult.class)
public interface BlockHitResultAccessor {
    @Accessor("side")
    void setSide(Direction direction);
}
