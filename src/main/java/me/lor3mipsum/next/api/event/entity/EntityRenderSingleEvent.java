package me.lor3mipsum.next.api.event.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class EntityRenderSingleEvent extends EntityRenderEvent {
    public EntityRenderSingleEvent(Entity entity, MatrixStack matrix, VertexConsumerProvider vertex) {
        super(entity, matrix, vertex);
    }

    public EntityRenderSingleEvent(Entity entity, MatrixStack matrix, VertexConsumerProvider vertex, Era era) {
        super(entity, matrix, vertex, era);
    }
}