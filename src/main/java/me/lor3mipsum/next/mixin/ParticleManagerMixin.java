package me.lor3mipsum.next.mixin;

import me.lor3mipsum.next.client.event.EventManager;
import me.lor3mipsum.next.client.impl.events.ParticleEvent;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V", at = @At("HEAD"), cancellable = true)
    public void addParticle(Particle particle, CallbackInfo ci) {
        // pls send help
        ParticleEvent.Normal event = new ParticleEvent.Normal(particle);
        EventManager.call(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;)V", at = @At("HEAD"), cancellable = true)
    public void addEmitter(Entity entity, ParticleEffect particleEffect, CallbackInfo ci) {
        ParticleEvent.Emitter event = new ParticleEvent.Emitter(particleEffect);
        EventManager.call(event);

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V", at = @At("HEAD"), cancellable = true)
    public void addEmitter_(Entity entity, ParticleEffect particleEffect, int maxAge, CallbackInfo ci) {
        ParticleEvent.Emitter event = new ParticleEvent.Emitter(particleEffect);
        EventManager.call(event);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}
