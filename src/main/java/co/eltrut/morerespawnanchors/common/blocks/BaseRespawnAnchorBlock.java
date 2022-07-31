package co.eltrut.morerespawnanchors.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;
import java.util.Random;

public class BaseRespawnAnchorBlock extends Block implements IRespawnAnchorBlock {
	
	public BaseRespawnAnchorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.RESPAWN_ANCHOR_CHARGES, 0));
	}

	@Override
	public IntegerProperty getCharges() {
		return BlockStateProperties.RESPAWN_ANCHOR_CHARGES;
	}

	@Override
	public int getMaxCharges() {
		return 4;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player,
								 InteractionHand handIn, BlockHitResult hit) {
		ItemStack itemstack = player.getItemInHand(handIn);
		if (handIn == InteractionHand.MAIN_HAND && !this.isValidFuel(itemstack)
				&& this.isValidFuel(player.getItemInHand(InteractionHand.OFF_HAND))) {
			return InteractionResult.PASS;
		} else if (isValidFuel(itemstack) && notFullyCharged(state)) {
			this.chargeAnchor(worldIn, pos, state);
			if (!player.isCreative()) {
				itemstack.shrink(1);
			}

			return InteractionResult.sidedSuccess(worldIn.isClientSide);
		} else if (state.getValue(this.getCharges()) == 0) {
			return InteractionResult.PASS;
		} else if (!this.doesRespawnAnchorWork(worldIn)) {
			if (!worldIn.isClientSide) {
				this.triggerExplosion(state, worldIn, pos);
			}

			return InteractionResult.sidedSuccess(worldIn.isClientSide);
		} else {
			if (!worldIn.isClientSide) {
				ServerPlayer serverplayerentity = (ServerPlayer) player;
				if (serverplayerentity.getRespawnDimension() != worldIn.dimension()
						|| !serverplayerentity.getRespawnPosition().equals(pos)) {
					serverplayerentity.setRespawnPosition(worldIn.dimension(), pos, 0.0F, false, true);
					worldIn.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
							(double) pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN,
							SoundSource.BLOCKS, 1.0F, 1.0F);
					return InteractionResult.SUCCESS;
				}
			}

			return InteractionResult.CONSUME;
		}
	}

	public boolean isValidFuel(ItemStack itemStack) {
		return itemStack.is(Items.GLOWSTONE);
	}

	public boolean notFullyCharged(BlockState state) {
		return state.getValue(this.getCharges()) < 4;
	}

	@Override
	public boolean doesRespawnAnchorWork(Level world) {
		return RespawnAnchorBlock.canSetSpawn(world);
	}
	
	@Override
	public void chargeAnchor(Level world, BlockPos pos, BlockState state) {
		world.setBlock(pos, state.setValue(this.getCharges(), Integer.valueOf(state.getValue(this.getCharges()) + 1)), 3);
		world.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
				(double) pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level worldIn, BlockPos pos) {
		return RespawnAnchorBlock.getScaledChargeLevel(state, 15);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter getter, BlockPos pos, PathComputationType type) {
		return false;
	}

	@SuppressWarnings("deprecation")
	private void triggerExplosion(BlockState state, Level world, final BlockPos pos2) {
		world.removeBlock(pos2, false);
		boolean flag = Direction.Plane.HORIZONTAL.stream().map(pos2::relative).anyMatch((posIn) -> {
			return RespawnAnchorBlock.isWaterThatWouldFlow(posIn, world);
		});
		final boolean flag1 = flag || world.getFluidState(pos2.above()).is(FluidTags.WATER);
		ExplosionDamageCalculator explosioncontext = new ExplosionDamageCalculator() {
			@Override
			public Optional<Float> getBlockExplosionResistance(Explosion explosion, BlockGetter reader, BlockPos pos,
															   BlockState state, FluidState fluid) {
				return pos.equals(pos2) && flag1 ? Optional.of(Blocks.WATER.getExplosionResistance())
						: super.getBlockExplosionResistance(explosion, reader, pos, state, fluid);
			}
		};
		world.explode((Entity) null, DamageSource.badRespawnPointExplosion(), explosioncontext,
				(double) pos2.getX() + 0.5D, (double) pos2.getY() + 0.5D, (double) pos2.getZ() + 0.5D, 5.0F, true,
				Explosion.BlockInteraction.DESTROY);
	}

	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(this.getCharges()) != 0) {
			if (rand.nextInt(100) == 0) {
				worldIn.playSound((Player) null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
						(double) pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS,
						1.0F, 1.0F);
			}

			double d0 = (double) pos.getX() + 0.5D + (0.5D - rand.nextDouble());
			double d1 = (double) pos.getY() + 1.0D;
			double d2 = (double) pos.getZ() + 0.5D + (0.5D - rand.nextDouble());
			double d3 = (double) rand.nextFloat() * 0.04D;
			worldIn.addParticle(ParticleTypes.REVERSE_PORTAL, d0, d1, d2, 0.0D, d3, 0.0D);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(this.getCharges());
	}

}
