package skirmish;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityAIAvoidSwellingCreeper extends EntityAIAvoidEntity<EntityCreeper> {
    public EntityAIAvoidSwellingCreeper(EntityCreature theEntityIn, Class<EntityCreeper> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(theEntityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
    }

    @Override
    public boolean shouldExecute() {
        boolean result = super.shouldExecute();
        return result && closestLivingEntity.getCreeperState() == 1 && closestLivingEntity != theEntity;
    }
}
