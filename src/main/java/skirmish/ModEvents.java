package skirmish;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import java.util.Set;

public class ModEvents {
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event){
        if(!(event.getEntity() instanceof EntityCreature))
            return;

        EntityCreature entity = (EntityCreature) event.getEntity();
        World world = entity.worldObj;
        if(world.isRemote)
            return;

        if(entity instanceof EntityMob && !(entity instanceof EntityEnderman)){
            entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100);

            entity.targetTasks.addTask(1, new EntityAINearestAttackableTarget(entity, EntityVillager.class, false));
            entity.targetTasks.addTask(1, new EntityAINearestAttackableTarget(entity, EntityPlayer.class, false));

            entity.tasks.addTask(0, new EntityAIAvoidSwellingCreeper(entity, EntityCreeper.class, 16.0F, 1.5D, 1.5D));

            if(!(entity instanceof EntityZombie))
                entity.tasks.addTask(6, new EntityAIMoveThroughVillage(entity, 1.0D, false));
        }

        if(entity instanceof EntitySkeleton || entity instanceof EntityWitch)
        {
            ((PathNavigateGround)entity.getNavigator()).setBreakDoors(true);
            entity.tasks.addTask(4, new EntityAIOpenDoor(entity, false));
        }

        if(entity instanceof EntityCreeper)
        {
            EntityCreeper creeper = (EntityCreeper) entity;
            creeper.tasks.addTask(1, new EntityAIOnTargetBlocked(creeper));
        }

        if(entity instanceof EntityVillager){
            entity.tasks.addTask(1, new EntityAIAvoidEntity(entity, EntityCreeper.class, 8.0F, 0.8D, 1D));
            entity.tasks.addTask(1, new EntityAIAvoidEntity(entity, EntitySkeleton.class, 16.0F, 0.7D, 0.7D));
            entity.tasks.addTask(1, new EntityAIAvoidEntity(entity, EntitySlime.class, 8.0F, 0.3D, 0.3D));
        }

        if(entity instanceof EntityCreeper || entity instanceof EntityZombie || entity instanceof EntitySkeleton || entity instanceof EntityWitch)
            entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
    }

    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) {
        Entity entity = event.getEntity();

        if(entity.isDead || entity.worldObj.isRemote)
            return;

        DamageSource source = event.getSource();
        if(entity.getClass().equals(EntityCreeper.class) && (source.isExplosion() || source.isFireDamage()))
            Common.explodeCreeper((EntityCreeper) entity);

        if(entity.getClass().equals(EntitySlime.class) && source.damageType == "arrow"){
            EntitySlime slime = (EntitySlime) entity;
            entity.worldObj.createExplosion(null, entity.posX, entity.posY, entity.posZ, slime.getSlimeSize(), false);
            entity.setDead();
        }
    }
}
