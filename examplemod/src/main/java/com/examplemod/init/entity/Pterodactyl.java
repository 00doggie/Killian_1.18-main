package com.examplemod.init.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Enemy;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class Pterodactyl extends FlyingMob implements Enemy{
    public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
    public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
    private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(Pterodactyl.class, EntityDataSerializers.INT);
    Vec3 moveTargetPoint = Vec3.ZERO;
    BlockPos anchorPoint = BlockPos.ZERO;
    Pterodactyl.AttackPhase attackPhase = Pterodactyl.AttackPhase.CIRCLE;

    public Pterodactyl(EntityType<? extends Pterodactyl> p_33101_, Level p_33102_) {
        super(p_33101_, p_33102_);
        this.xpReward = 5;
        this.moveControl = new Pterodactyl.PterodactylMoveControl(this);
        this.lookControl = new Pterodactyl.PterodactylLookControl(this);
    }

    public boolean isFlapping() {
        return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
    }

    protected BodyRotationControl createBodyControl() {
        return new Pterodactyl.PterodactylBodyRotationControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new Pterodactyl.PterodactylAttackStrategyGoal());
        this.goalSelector.addGoal(2, new Pterodactyl.PterodactylSweepAttackGoal());
        this.goalSelector.addGoal(3, new Pterodactyl.PterodactylCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new Pterodactyl.PterodactylAttackPlayerTargetGoal());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ID_SIZE, 0);
    }

    public void setPterodactylSize(int p_33109_) {
        this.entityData.set(ID_SIZE, Mth.clamp(p_33109_, 0, 64));
    }

    private void updatePterodactylSizeInfo() {
        this.refreshDimensions();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)(6 + this.getPterodactylSize()));
    }

    public int getPterodactylSize() {
        return this.entityData.get(ID_SIZE);
    }

    protected float getStandingEyeHeight(Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.height * 0.35F;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_33134_) {
        if (ID_SIZE.equals(p_33134_)) {
            this.updatePterodactylSizeInfo();
        }

        super.onSyncedDataUpdated(p_33134_);
    }

    public int getUniqueFlapTickOffset() {
        return this.getId() * 3;
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    public void tick() {
        super.tick();
        if (this.level.isClientSide) {
            float f = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
            float f1 = Mth.cos((float)(this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }

            int i = this.getPterodactylSize();
            float f2 = Mth.cos(this.getYRot() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f3 = Mth.sin(this.getYRot() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
            float f4 = (0.3F + f * 0.45F) * ((float)i * 0.2F + 1.0F);
            this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() + (double)f2, this.getY() + (double)f4, this.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
            this.level.addParticle(ParticleTypes.MYCELIUM, this.getX() - (double)f2, this.getY() + (double)f4, this.getZ() - (double)f3, 0.0D, 0.0D, 0.0D);
        }

    }

    public void aiStep() {
        if (this.isAlive() && this.isSunBurnTick()) {
            this.setSecondsOnFire(8);
        }

        super.aiStep();
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
        this.anchorPoint = this.blockPosition().above(5);
        this.setPterodactylSize(0);
        return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
    }

    public void readAdditionalSaveData(CompoundTag p_33132_) {
        super.readAdditionalSaveData(p_33132_);
        if (p_33132_.contains("AX")) {
            this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
        }

        this.setPterodactylSize(p_33132_.getInt("Size"));
    }

    public void addAdditionalSaveData(CompoundTag p_33141_) {
        super.addAdditionalSaveData(p_33141_);
        p_33141_.putInt("AX", this.anchorPoint.getX());
        p_33141_.putInt("AY", this.anchorPoint.getY());
        p_33141_.putInt("AZ", this.anchorPoint.getZ());
        p_33141_.putInt("Size", this.getPterodactylSize());
    }

    public boolean shouldRenderAtSqrDistance(double p_33107_) {
        return true;
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33152_) {
        return SoundEvents.PHANTOM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    protected float getSoundVolume() {
        return 1.0F;
    }

    public boolean canAttackType(EntityType<?> p_33111_) {
        return true;
    }

    public EntityDimensions getDimensions(Pose p_33113_) {
        int i = this.getPterodactylSize();
        EntityDimensions entitydimensions = super.getDimensions(p_33113_);
        float f = (entitydimensions.width + 0.2F * (float)i) / entitydimensions.width;
        return entitydimensions.scale(f);
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;
    }

    class PterodactylAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
                return false;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = Pterodactyl.this.level.getNearbyPlayers(this.attackTargeting, Pterodactyl.this, Pterodactyl.this.getBoundingBox().inflate(16.0D, 64.0D, 16.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for(Player player : list) {
                        if (Pterodactyl.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            Pterodactyl.this.setTarget(player);
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = Pterodactyl.this.getTarget();
            return livingentity != null ? Pterodactyl.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }
    }

    class PterodactylAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        public boolean canUse() {
            LivingEntity livingentity = Pterodactyl.this.getTarget();
            return livingentity != null ? Pterodactyl.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(10);
            Pterodactyl.this.attackPhase = Pterodactyl.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            Pterodactyl.this.anchorPoint = Pterodactyl.this.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, Pterodactyl.this.anchorPoint).above(10 + Pterodactyl.this.random.nextInt(20));
        }

        public void tick() {
            if (Pterodactyl.this.attackPhase == Pterodactyl.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    Pterodactyl.this.attackPhase = Pterodactyl.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((8 + Pterodactyl.this.random.nextInt(4)) * 20);
                    Pterodactyl.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.95F + Pterodactyl.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            Pterodactyl.this.anchorPoint = Pterodactyl.this.getTarget().blockPosition().above(20 + Pterodactyl.this.random.nextInt(20));
            if (Pterodactyl.this.anchorPoint.getY() < Pterodactyl.this.level.getSeaLevel()) {
                Pterodactyl.this.anchorPoint = new BlockPos(Pterodactyl.this.anchorPoint.getX(), Pterodactyl.this.level.getSeaLevel() + 1, Pterodactyl.this.anchorPoint.getZ());
            }

        }
    }

    class PterodactylBodyRotationControl extends BodyRotationControl {
        public PterodactylBodyRotationControl(Mob p_33216_) {
            super(p_33216_);
        }

        public void clientTick() {
            Pterodactyl.this.yHeadRot = Pterodactyl.this.yBodyRot;
            Pterodactyl.this.yBodyRot = Pterodactyl.this.getYRot();
        }
    }

    class PterodactylCircleAroundAnchorGoal extends Pterodactyl.PterodactylMoveTargetGoal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        public boolean canUse() {
            return Pterodactyl.this.getTarget() == null || Pterodactyl.this.attackPhase == Pterodactyl.AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + Pterodactyl.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + Pterodactyl.this.random.nextFloat() * 9.0F;
            this.clockwise = Pterodactyl.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        public void tick() {
            if (Pterodactyl.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + Pterodactyl.this.random.nextFloat() * 9.0F;
            }

            if (Pterodactyl.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (Pterodactyl.this.random.nextInt(this.adjustedTickDelay(450)) == 0) {
                this.angle = Pterodactyl.this.random.nextFloat() * 2.0F * (float)Math.PI;
                this.selectNext();
            }

            if (this.touchingTarget()) {
                this.selectNext();
            }

            if (Pterodactyl.this.moveTargetPoint.y < Pterodactyl.this.getY() && !Pterodactyl.this.level.isEmptyBlock(Pterodactyl.this.blockPosition().below(1))) {
                this.height = Math.max(1.0F, this.height);
                this.selectNext();
            }

            if (Pterodactyl.this.moveTargetPoint.y > Pterodactyl.this.getY() && !Pterodactyl.this.level.isEmptyBlock(Pterodactyl.this.blockPosition().above(1))) {
                this.height = Math.min(-1.0F, this.height);
                this.selectNext();
            }

        }

        private void selectNext() {
            if (BlockPos.ZERO.equals(Pterodactyl.this.anchorPoint)) {
                Pterodactyl.this.anchorPoint = Pterodactyl.this.blockPosition();
            }

            this.angle += this.clockwise * 15.0F * ((float)Math.PI / 180F);
            Pterodactyl.this.moveTargetPoint = Vec3.atLowerCornerOf(Pterodactyl.this.anchorPoint).add((double)(this.distance * Mth.cos(this.angle)), (double)(-4.0F + this.height), (double)(this.distance * Mth.sin(this.angle)));
        }
    }

    class PterodactylLookControl extends LookControl {
        public PterodactylLookControl(Mob p_33235_) {
            super(p_33235_);
        }

        public void tick() {
        }
    }

    class PterodactylMoveControl extends MoveControl {
        private float speed = 0.1F;

        public PterodactylMoveControl(Mob p_33241_) {
            super(p_33241_);
        }

        public void tick() {
            if (Pterodactyl.this.horizontalCollision) {
                Pterodactyl.this.setYRot(Pterodactyl.this.getYRot() + 180.0F);
                this.speed = 0.1F;
            }

            double d0 = Pterodactyl.this.moveTargetPoint.x - Pterodactyl.this.getX();
            double d1 = Pterodactyl.this.moveTargetPoint.y - Pterodactyl.this.getY();
            double d2 = Pterodactyl.this.moveTargetPoint.z - Pterodactyl.this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            if (Math.abs(d3) > (double)1.0E-5F) {
                double d4 = 1.0D - Math.abs(d1 * (double)0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = Pterodactyl.this.getYRot();
                float f1 = (float)Mth.atan2(d2, d0);
                float f2 = Mth.wrapDegrees(Pterodactyl.this.getYRot() + 90.0F);
                float f3 = Mth.wrapDegrees(f1 * (180F / (float)Math.PI));
                Pterodactyl.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                Pterodactyl.this.yBodyRot = Pterodactyl.this.getYRot();
                if (Mth.degreesDifferenceAbs(f, Pterodactyl.this.getYRot()) < 3.0F) {
                    this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
                } else {
                    this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
                }

                float f4 = (float)(-(Mth.atan2(-d1, d3) * (double)(180F / (float)Math.PI)));
                Pterodactyl.this.setXRot(f4);
                float f5 = Pterodactyl.this.getYRot() + 90.0F;
                double d6 = (double)(this.speed * Mth.cos(f5 * ((float)Math.PI / 180F))) * Math.abs(d0 / d5);
                double d7 = (double)(this.speed * Mth.sin(f5 * ((float)Math.PI / 180F))) * Math.abs(d2 / d5);
                double d8 = (double)(this.speed * Mth.sin(f4 * ((float)Math.PI / 180F))) * Math.abs(d1 / d5);
                Vec3 vec3 = Pterodactyl.this.getDeltaMovement();
                Pterodactyl.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
            }

        }
    }

    abstract class PterodactylMoveTargetGoal extends Goal {
        public PterodactylMoveTargetGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        protected boolean touchingTarget() {
            return Pterodactyl.this.moveTargetPoint.distanceToSqr(Pterodactyl.this.getX(), Pterodactyl.this.getY(), Pterodactyl.this.getZ()) < 4.0D;
        }
    }

    class PterodactylSweepAttackGoal extends Pterodactyl.PterodactylMoveTargetGoal {
        private static final int CAT_SEARCH_TICK_DELAY = 20;
        private boolean isScaredOfCat;
        private int catSearchTick;

        public boolean canUse() {
            return Pterodactyl.this.getTarget() != null && Pterodactyl.this.attackPhase == Pterodactyl.AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = Pterodactyl.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player) {
                    Player player = (Player)livingentity;
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                if (!this.canUse()) {
                    return false;
                } else {
                    if (Pterodactyl.this.tickCount > this.catSearchTick) {
                        this.catSearchTick = Pterodactyl.this.tickCount + 20;
                        List<Cat> list = Pterodactyl.this.level.getEntitiesOfClass(Cat.class, Pterodactyl.this.getBoundingBox().inflate(16.0D), EntitySelector.ENTITY_STILL_ALIVE);

                        for(Cat cat : list) {
                            cat.hiss();
                        }

                        this.isScaredOfCat = !list.isEmpty();
                    }

                    return !this.isScaredOfCat;
                }
            }
        }

        public void start() {
        }

        public void stop() {
            Pterodactyl.this.setTarget((LivingEntity)null);
            Pterodactyl.this.attackPhase = Pterodactyl.AttackPhase.CIRCLE;
        }

        public void tick() {
            LivingEntity livingentity = Pterodactyl.this.getTarget();
            if (livingentity != null) {
                Pterodactyl.this.moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
                if (Pterodactyl.this.getBoundingBox().inflate((double)0.2F).intersects(livingentity.getBoundingBox())) {
                    Pterodactyl.this.doHurtTarget(livingentity);
                    Pterodactyl.this.attackPhase = Pterodactyl.AttackPhase.CIRCLE;
                    if (!Pterodactyl.this.isSilent()) {
                        Pterodactyl.this.level.levelEvent(1039, Pterodactyl.this.blockPosition(), 0);
                    }
                } else if (Pterodactyl.this.horizontalCollision || Pterodactyl.this.hurtTime > 0) {
                    Pterodactyl.this.attackPhase = Pterodactyl.AttackPhase.CIRCLE;
                }

            }
        }
    }
}
