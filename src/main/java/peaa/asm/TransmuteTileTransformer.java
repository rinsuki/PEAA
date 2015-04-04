package peaa.asm;

import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.*;

import peaa.asm.MK2TextureTransformer.CustomMethodVisitor;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import peaa.asm.MethodNameList;

public class TransmuteTileTransformer implements IClassTransformer, Opcodes
{
	private static final String TARGETCLASSNAME = "moze_intel.projecte.gameObjs.tiles.TransmuteTile";

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(!TARGETCLASSNAME.equals(transformedName)) {return basicClass;}

		try {
			PEAACoreCorePlugin.logger.info("-------------------------Start TransmuteTile Transform--------------------------");
			ClassReader cr = new ClassReader(basicClass);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			cr.accept(new CustomVisitor(name, cw), 8);
			basicClass = cw.toByteArray();
			PEAACoreCorePlugin.logger.info("-------------------------Finish TransmuteTile Transform-------------------------");
		} catch (Exception e) {
			throw new RuntimeException("failed : TransmuteTileTransformer loading", e);
		}
		return basicClass;
	}

	class CustomVisitor extends ClassVisitor
	{
		String owner;
		public CustomVisitor(String owner, ClassVisitor cv)
		{
			super(Opcodes.ASM4, cv);
			this.owner = owner;
		}

		static final String targetMethodName = "<init>";
		static final String targetMethodName2 = "updateOutputs";

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			// 初期値追加
			if (targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				PEAACoreCorePlugin.logger.info("Start [<init>] Transform");
				return new CustomMethodVisitor(this.api, super.visitMethod(access, name, desc, signature, exceptions));

			}
			// 処理追加
			if (targetMethodName2.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc))) {
				PEAACoreCorePlugin.logger.info("Start [updateOutputs] Transform");
				overrideUpdateOutputs();
				return super.visitMethod(access, name+"Old", desc, signature, exceptions);

			}

			return super.visitMethod(access, name, desc, signature, exceptions);
		}

		int cnt = 0;
		/**
		 * フィールドの追加
		 * public ItemStack beforeLockStack = null;		// 前回検索欄にセットされたものを記録
		 * public int sameItemSetNum = 0;				// 同じアイテムを検索欄にセットした回数
		 */
		@Override
		public FieldVisitor visitField(int access, String name, String desc,
	            String signature, Object value) {
			if (cnt == 0) {
				FieldVisitor fv = super.visitField(ACC_PUBLIC, "beforeLockStack", "Lnet/minecraft/item/ItemStack;", null, null);
				fv.visitEnd();
				fv = super.visitField(ACC_PUBLIC, "sameItemSetNum", "I", null, null);
				fv.visitEnd();
				cnt++;
			}

			return super.visitField(access, name, desc, signature, value);
	    }

		public void overrideUpdateOutputs() {
			MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "updateOutputs", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
			Label l3 = new Label();
			Label l4 = new Label();
			Label l5 = new Label();
			mv.visitTryCatchBlock(l3, l4, l5, "java/lang/Exception");
			Label l6 = new Label();
			mv.visitLabel(l6);
			mv.visitLineNumber(94, l6);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "player", "Lnet/minecraft/entity/player/EntityPlayer;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/entity/player/EntityPlayer", MethodNameList.getName("getCommandSenderName"), "()Ljava/lang/String;", false);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/playerData/Transmutation", "getKnowledge", "(Ljava/lang/String;)Ljava/util/LinkedList;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/LinkedList", "clone", "()Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "java/util/LinkedList");
			mv.visitVarInsn(ASTORE, 1);
			Label l7 = new Label();
			mv.visitLabel(l7);
			mv.visitLineNumber(96, l7);
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "MATTER_INDEXES", "[I");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ASTORE, 5);
			mv.visitInsn(ARRAYLENGTH);
			mv.visitVarInsn(ISTORE, 4);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 3);
			Label l8 = new Label();
			mv.visitJumpInsn(GOTO, l8);
			Label l9 = new Label();
			mv.visitLabel(l9);
			mv.visitFrame(Opcodes.F_FULL, 6, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", Opcodes.TOP, Opcodes.INTEGER, Opcodes.INTEGER, "[I"}, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 5);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ISTORE, 2);
			Label l10 = new Label();
			mv.visitLabel(l10);
			mv.visitLineNumber(98, l10);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitVarInsn(ILOAD, 2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(AASTORE);
			Label l11 = new Label();
			mv.visitLabel(l11);
			mv.visitLineNumber(96, l11);
			mv.visitIincInsn(3, 1);
			mv.visitLabel(l8);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitJumpInsn(IF_ICMPLT, l9);
			Label l12 = new Label();
			mv.visitLabel(l12);
			mv.visitLineNumber(101, l12);
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "FUEL_INDEXES", "[I");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ASTORE, 5);
			mv.visitInsn(ARRAYLENGTH);
			mv.visitVarInsn(ISTORE, 4);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 3);
			Label l13 = new Label();
			mv.visitJumpInsn(GOTO, l13);
			Label l14 = new Label();
			mv.visitLabel(l14);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ISTORE, 2);
			Label l15 = new Label();
			mv.visitLabel(l15);
			mv.visitLineNumber(103, l15);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitVarInsn(ILOAD, 2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(AASTORE);
			Label l16 = new Label();
			mv.visitLabel(l16);
			mv.visitLineNumber(101, l16);
			mv.visitIincInsn(3, 1);
			mv.visitLabel(l13);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitJumpInsn(IF_ICMPLT, l14);
			Label l17 = new Label();
			mv.visitLabel(l17);
			mv.visitLineNumber(106, l17);
			mv.visitInsn(ACONST_NULL);
			mv.visitVarInsn(ASTORE, 2);
			Label l18 = new Label();
			mv.visitLabel(l18);
			mv.visitLineNumber(108, l18);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitIntInsn(BIPUSH, 8);
			mv.visitInsn(AALOAD);
			Label l19 = new Label();
			mv.visitJumpInsn(IFNULL, l19);
			Label l20 = new Label();
			mv.visitLabel(l20);
			mv.visitLineNumber(110, l20);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitIntInsn(BIPUSH, 8);
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/utils/Utils", "getEmcValue", "(Lnet/minecraft/item/ItemStack;)I", false);
			mv.visitVarInsn(ISTORE, 3);
			Label l21 = new Label();
			mv.visitLabel(l21);
			mv.visitLineNumber(112, l21);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "getStoredEmc", "()D", false);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitInsn(I2D);
			mv.visitInsn(DCMPG);
			Label l22 = new Label();
			mv.visitJumpInsn(IFGE, l22);
			Label l23 = new Label();
			mv.visitLabel(l23);
			mv.visitLineNumber(114, l23);
			mv.visitInsn(RETURN);
			mv.visitLabel(l22);
			mv.visitLineNumber(117, l22);
			mv.visitFrame(Opcodes.F_FULL, 4, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", Opcodes.INTEGER}, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitIntInsn(BIPUSH, 8);
			mv.visitInsn(AALOAD);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/utils/Utils", "getNormalizedStack", "(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", false);
			mv.visitVarInsn(ASTORE, 2);
			Label l24 = new Label();
			mv.visitLabel(l24);
			mv.visitLineNumber(119, l24);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", MethodNameList.getName("hasTagCompound"), "()Z", false);
			Label l25 = new Label();
			mv.visitJumpInsn(IFEQ, l25);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/utils/NBTWhitelist", "shouldDupeWithNBT", "(Lnet/minecraft/item/ItemStack;)Z", false);
			mv.visitJumpInsn(IFNE, l25);
			Label l26 = new Label();
			mv.visitLabel(l26);
			mv.visitLineNumber(121, l26);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitTypeInsn(NEW, "net/minecraft/nbt/NBTTagCompound");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/nbt/NBTTagCompound", "<init>", "()V", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", MethodNameList.getName("setTagCompound"), "(Lnet/minecraft/nbt/NBTTagCompound;)V", false);
			mv.visitLabel(l25);
			mv.visitLineNumber(124, l25);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/LinkedList", "iterator", "()Ljava/util/Iterator;", false);
			mv.visitVarInsn(ASTORE, 4);
			Label l27 = new Label();
			mv.visitLabel(l27);
			mv.visitLineNumber(126, l27);
			Label l28 = new Label();
			mv.visitJumpInsn(GOTO, l28);
			Label l29 = new Label();
			mv.visitLabel(l29);
			mv.visitLineNumber(128, l29);
			mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"java/util/Iterator"}, 0, null);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
			mv.visitVarInsn(ASTORE, 5);
			Label l30 = new Label();
			mv.visitLabel(l30);
			mv.visitLineNumber(130, l30);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/utils/Utils", "getEmcValue", "(Lnet/minecraft/item/ItemStack;)I", false);
			mv.visitVarInsn(ILOAD, 3);
			Label l31 = new Label();
			mv.visitJumpInsn(IF_ICMPLE, l31);
			Label l32 = new Label();
			mv.visitLabel(l32);
			mv.visitLineNumber(132, l32);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "remove", "()V", true);
			Label l33 = new Label();
			mv.visitLabel(l33);
			mv.visitLineNumber(133, l33);
			mv.visitJumpInsn(GOTO, l28);
			mv.visitLabel(l31);
			mv.visitLineNumber(136, l31);
			mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/item/ItemStack"}, 0, null);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/utils/Utils", "basicAreStacksEqual", "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", false);
			Label l34 = new Label();
			mv.visitJumpInsn(IFEQ, l34);
			Label l35 = new Label();
			mv.visitLabel(l35);
			mv.visitLineNumber(138, l35);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "remove", "()V", true);
			Label l36 = new Label();
			mv.visitLabel(l36);
			mv.visitLineNumber(139, l36);
			mv.visitJumpInsn(GOTO, l28);
			mv.visitLabel(l34);
			mv.visitLineNumber(142, l34);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitLdcInsn("");
			mv.visitVarInsn(ASTORE, 6);
			mv.visitLabel(l0);
			mv.visitLineNumber(146, l0);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", MethodNameList.getName("getDisplayName"), "()Ljava/lang/String;", false);
			mv.visitVarInsn(ASTORE, 6);
			mv.visitLabel(l1);
			mv.visitLineNumber(147, l1);
			Label l37 = new Label();
			mv.visitJumpInsn(GOTO, l37);
			mv.visitLabel(l2);
			mv.visitLineNumber(148, l2);
			mv.visitFrame(Opcodes.F_FULL, 7, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", Opcodes.INTEGER, "java/util/Iterator", "net/minecraft/item/ItemStack", "java/lang/String"}, 1, new Object[] {"java/lang/Exception"});
			mv.visitVarInsn(ASTORE, 7);
			Label l38 = new Label();
			mv.visitLabel(l38);
			mv.visitLineNumber(150, l38);
			mv.visitJumpInsn(GOTO, l28);
			mv.visitLabel(l37);
			mv.visitLineNumber(153, l37);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "filter", "Ljava/lang/String;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
			mv.visitJumpInsn(IFLE, l28);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "filter", "Ljava/lang/String;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z", false);
			mv.visitJumpInsn(IFNE, l28);
			Label l39 = new Label();
			mv.visitLabel(l39);
			mv.visitLineNumber(155, l39);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "remove", "()V", true);
			mv.visitLabel(l28);
			mv.visitLineNumber(126, l28);
			mv.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l29);
			Label l40 = new Label();
			mv.visitLabel(l40);
			mv.visitLineNumber(158, l40);
			Label l41 = new Label();
			mv.visitJumpInsn(GOTO, l41);
			mv.visitLabel(l19);
			mv.visitLineNumber(161, l19);
			mv.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/LinkedList", "iterator", "()Ljava/util/Iterator;", false);
			mv.visitVarInsn(ASTORE, 3);
			Label l42 = new Label();
			mv.visitLabel(l42);
			mv.visitLineNumber(163, l42);
			Label l43 = new Label();
			mv.visitJumpInsn(GOTO, l43);
			Label l44 = new Label();
			mv.visitLabel(l44);
			mv.visitLineNumber(165, l44);
			mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"java/util/Iterator"}, 0, null);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
			mv.visitVarInsn(ASTORE, 4);
			Label l45 = new Label();
			mv.visitLabel(l45);
			mv.visitLineNumber(167, l45);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKEVIRTUAL, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "getStoredEmc", "()D", false);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/utils/Utils", "getEmcValue", "(Lnet/minecraft/item/ItemStack;)I", false);
			mv.visitInsn(I2D);
			mv.visitInsn(DCMPG);
			Label l46 = new Label();
			mv.visitJumpInsn(IFGE, l46);
			Label l47 = new Label();
			mv.visitLabel(l47);
			mv.visitLineNumber(169, l47);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "remove", "()V", true);
			Label l48 = new Label();
			mv.visitLabel(l48);
			mv.visitLineNumber(170, l48);
			mv.visitJumpInsn(GOTO, l43);
			mv.visitLabel(l46);
			mv.visitLineNumber(173, l46);
			mv.visitFrame(Opcodes.F_APPEND,1, new Object[] {"net/minecraft/item/ItemStack"}, 0, null);
			mv.visitLdcInsn("");
			mv.visitVarInsn(ASTORE, 5);
			mv.visitLabel(l3);
			mv.visitLineNumber(177, l3);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", MethodNameList.getName("getDisplayName"), "()Ljava/lang/String;", false);
			mv.visitVarInsn(ASTORE, 5);
			mv.visitLabel(l4);
			mv.visitLineNumber(178, l4);
			Label l49 = new Label();
			mv.visitJumpInsn(GOTO, l49);
			mv.visitLabel(l5);
			mv.visitLineNumber(179, l5);
			mv.visitFrame(Opcodes.F_FULL, 6, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", "java/util/Iterator", "net/minecraft/item/ItemStack", "java/lang/String"}, 1, new Object[] {"java/lang/Exception"});
			mv.visitVarInsn(ASTORE, 6);
			Label l50 = new Label();
			mv.visitLabel(l50);
			mv.visitLineNumber(181, l50);
			mv.visitJumpInsn(GOTO, l43);
			mv.visitLabel(l49);
			mv.visitLineNumber(184, l49);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "filter", "Ljava/lang/String;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "length", "()I", false);
			mv.visitJumpInsn(IFLE, l43);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "toLowerCase", "()Ljava/lang/String;", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "filter", "Ljava/lang/String;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z", false);
			mv.visitJumpInsn(IFNE, l43);
			Label l51 = new Label();
			mv.visitLabel(l51);
			mv.visitLineNumber(186, l51);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "remove", "()V", true);
			mv.visitLabel(l43);
			mv.visitLineNumber(163, l43);
			mv.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l44);
			mv.visitLabel(l41);
			mv.visitLineNumber(191, l41);
			mv.visitFrame(Opcodes.F_CHOP,1, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/utils/Comparators", "ITEMSTACK_DESCENDING", "Ljava/util/Comparator;");
			mv.visitMethodInsn(INVOKESTATIC, "java/util/Collections", "sort", "(Ljava/util/List;Ljava/util/Comparator;)V", false);
			Label l52 = new Label();
			mv.visitLabel(l52);
			mv.visitLineNumber(193, l52);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 3);
			Label l53 = new Label();
			mv.visitLabel(l53);
			mv.visitLineNumber(194, l53);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 4);
			Label l54 = new Label();
			mv.visitLabel(l54);
			mv.visitLineNumber(196, l54);
			mv.visitVarInsn(ALOAD, 2);
			Label l55 = new Label();
			mv.visitJumpInsn(IFNULL, l55);
			Label l56 = new Label();
			mv.visitLabel(l56);
			mv.visitLineNumber(198, l56);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/emc/FuelMapper", "isStackFuel", "(Lnet/minecraft/item/ItemStack;)Z", false);
			Label l57 = new Label();
			mv.visitJumpInsn(IFEQ, l57);
			Label l58 = new Label();
			mv.visitLabel(l58);
			mv.visitLineNumber(200, l58);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "FUEL_INDEXES", "[I");
			mv.visitInsn(ICONST_0);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(AASTORE);
			Label l59 = new Label();
			mv.visitLabel(l59);
			mv.visitLineNumber(201, l59);
			mv.visitIincInsn(4, 1);
			Label l60 = new Label();
			mv.visitLabel(l60);
			mv.visitLineNumber(202, l60);
			mv.visitJumpInsn(GOTO, l55);
			mv.visitLabel(l57);
			mv.visitLineNumber(205, l57);
			mv.visitFrame(Opcodes.F_APPEND,2, new Object[] {Opcodes.INTEGER, Opcodes.INTEGER}, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "MATTER_INDEXES", "[I");
			mv.visitInsn(ICONST_0);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(AASTORE);
			Label l61 = new Label();
			mv.visitLabel(l61);
			mv.visitLineNumber(206, l61);
			mv.visitIincInsn(3, 1);
			mv.visitLabel(l55);
			mv.visitLineNumber(210, l55);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 2);
			Label l62 = new Label();
			mv.visitJumpInsn(IFNULL, l62);
			Label l63 = new Label();
			mv.visitLabel(l63);
			mv.visitLineNumber(211, l63);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "beforeLockStack", "Lnet/minecraft/item/ItemStack;");
			mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/item/ItemStack", MethodNameList.getName("areItemStacksEqual"), "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z", false);
			Label l64 = new Label();
			mv.visitJumpInsn(IFEQ, l64);
			Label l65 = new Label();
			mv.visitLabel(l65);
			mv.visitLineNumber(212, l65);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(DUP);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			mv.visitInsn(ICONST_1);
			mv.visitInsn(IADD);
			mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			Label l66 = new Label();
			mv.visitLabel(l66);
			mv.visitLineNumber(213, l66);
			mv.visitJumpInsn(GOTO, l62);
			mv.visitLabel(l64);
			mv.visitLineNumber(214, l64);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_0);
			mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			mv.visitLabel(l62);
			mv.visitLineNumber(217, l62);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 5);
			Label l67 = new Label();
			mv.visitLabel(l67);
			mv.visitLineNumber(218, l67);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/util/LinkedList", "iterator", "()Ljava/util/Iterator;", false);
			mv.visitVarInsn(ASTORE, 7);
			Label l68 = new Label();
			mv.visitJumpInsn(GOTO, l68);
			Label l69 = new Label();
			mv.visitLabel(l69);
			mv.visitFrame(Opcodes.F_FULL, 8, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 7);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
			mv.visitVarInsn(ASTORE, 6);
			Label l70 = new Label();
			mv.visitLabel(l70);
			mv.visitLineNumber(221, l70);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKESTATIC, "moze_intel/projecte/emc/FuelMapper", "isStackFuel", "(Lnet/minecraft/item/ItemStack;)Z", false);
			Label l71 = new Label();
			mv.visitJumpInsn(IFEQ, l71);
			Label l72 = new Label();
			mv.visitLabel(l72);
			mv.visitLineNumber(223, l72);
			mv.visitVarInsn(ALOAD, 2);
			Label l73 = new Label();
			mv.visitJumpInsn(IFNULL, l73);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			mv.visitInsn(ICONST_3);
			mv.visitInsn(IMUL);
			Label l74 = new Label();
			mv.visitJumpInsn(IF_ICMPLT, l74);
			mv.visitLabel(l73);
			mv.visitLineNumber(224, l73);
			mv.visitFrame(Opcodes.F_FULL, 8, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER, "net/minecraft/item/ItemStack", "java/util/Iterator"}, 0, new Object[] {});
			mv.visitVarInsn(ILOAD, 4);
			mv.visitInsn(ICONST_4);
			mv.visitJumpInsn(IF_ICMPGE, l74);
			Label l75 = new Label();
			mv.visitLabel(l75);
			mv.visitLineNumber(226, l75);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "FUEL_INDEXES", "[I");
			mv.visitVarInsn(ILOAD, 4);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitInsn(AASTORE);
			Label l76 = new Label();
			mv.visitLabel(l76);
			mv.visitLineNumber(228, l76);
			mv.visitIincInsn(4, 1);
			Label l77 = new Label();
			mv.visitLabel(l77);
			mv.visitLineNumber(231, l77);
			mv.visitJumpInsn(GOTO, l74);
			mv.visitLabel(l71);
			mv.visitLineNumber(234, l71);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 2);
			Label l78 = new Label();
			mv.visitJumpInsn(IFNULL, l78);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			mv.visitIntInsn(BIPUSH, 11);
			mv.visitInsn(IMUL);
			mv.visitJumpInsn(IF_ICMPLT, l74);
			mv.visitLabel(l78);
			mv.visitLineNumber(235, l78);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ILOAD, 3);
			mv.visitIntInsn(BIPUSH, 12);
			mv.visitJumpInsn(IF_ICMPGE, l74);
			Label l79 = new Label();
			mv.visitLabel(l79);
			mv.visitLineNumber(237, l79);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "inventory", "[Lnet/minecraft/item/ItemStack;");
			mv.visitFieldInsn(GETSTATIC, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "MATTER_INDEXES", "[I");
			mv.visitVarInsn(ILOAD, 3);
			mv.visitInsn(IALOAD);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitInsn(AASTORE);
			Label l80 = new Label();
			mv.visitLabel(l80);
			mv.visitLineNumber(239, l80);
			mv.visitIincInsn(3, 1);
			mv.visitLabel(l74);
			mv.visitLineNumber(243, l74);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitIincInsn(5, 1);
			mv.visitLabel(l68);
			mv.visitLineNumber(218, l68);
			mv.visitFrame(Opcodes.F_FULL, 8, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.TOP, "java/util/Iterator"}, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 7);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l69);
			Label l81 = new Label();
			mv.visitLabel(l81);
			mv.visitLineNumber(245, l81);
			mv.visitInsn(ICONST_3);
			mv.visitVarInsn(ISTORE, 6);
			Label l82 = new Label();
			mv.visitLabel(l82);
			mv.visitLineNumber(246, l82);
			mv.visitVarInsn(ILOAD, 4);
			Label l83 = new Label();
			mv.visitJumpInsn(IFNE, l83);
			Label l84 = new Label();
			mv.visitLabel(l84);
			mv.visitLineNumber(247, l84);
			mv.visitIntInsn(BIPUSH, 11);
			mv.visitVarInsn(ISTORE, 6);
			mv.visitLabel(l83);
			mv.visitLineNumber(249, l83);
			mv.visitFrame(Opcodes.F_FULL, 7, new Object[] {"moze_intel/projecte/gameObjs/tiles/TransmuteTile", "java/util/LinkedList", "net/minecraft/item/ItemStack", Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER, Opcodes.INTEGER}, 0, new Object[] {});
			mv.visitVarInsn(ILOAD, 5);
			mv.visitVarInsn(ILOAD, 6);
			mv.visitInsn(IDIV);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			Label l85 = new Label();
			mv.visitJumpInsn(IF_ICMPGE, l85);
			Label l86 = new Label();
			mv.visitLabel(l86);
			mv.visitLineNumber(250, l86);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ICONST_0);
			mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");
			Label l87 = new Label();
			mv.visitLabel(l87);
			mv.visitLineNumber(251, l87);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(ACONST_NULL);
			mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "beforeLockStack", "Lnet/minecraft/item/ItemStack;");
			Label l88 = new Label();
			mv.visitLabel(l88);
			mv.visitLineNumber(252, l88);
			Label l89 = new Label();
			mv.visitJumpInsn(GOTO, l89);
			mv.visitLabel(l85);
			mv.visitLineNumber(253, l85);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitJumpInsn(IFNULL, l89);
			Label l90 = new Label();
			mv.visitLabel(l90);
			mv.visitLineNumber(254, l90);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "beforeLockStack", "Lnet/minecraft/item/ItemStack;");
			mv.visitLabel(l89);
			mv.visitLineNumber(256, l89);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(RETURN);
			Label l91 = new Label();
			mv.visitLabel(l91);
			mv.visitLocalVariable("this", "Lmoze_intel/projecte/gameObjs/tiles/TransmuteTile;", null, l6, l91, 0);
			mv.visitLocalVariable("knowledge", "Ljava/util/LinkedList;", "Ljava/util/LinkedList<Lnet/minecraft/item/ItemStack;>;", l7, l91, 1);
			mv.visitLocalVariable("i", "I", null, l10, l11, 2);
			mv.visitLocalVariable("i", "I", null, l15, l16, 2);
			mv.visitLocalVariable("lockCopy", "Lnet/minecraft/item/ItemStack;", null, l18, l91, 2);
			mv.visitLocalVariable("reqEmc", "I", null, l21, l40, 3);
			mv.visitLocalVariable("iter", "Ljava/util/Iterator;", "Ljava/util/Iterator<Lnet/minecraft/item/ItemStack;>;", l27, l40, 4);
			mv.visitLocalVariable("stack", "Lnet/minecraft/item/ItemStack;", null, l30, l28, 5);
			mv.visitLocalVariable("displayName", "Ljava/lang/String;", null, l0, l28, 6);
			mv.visitLocalVariable("e", "Ljava/lang/Exception;", null, l38, l37, 7);
			mv.visitLocalVariable("iter", "Ljava/util/Iterator;", "Ljava/util/Iterator<Lnet/minecraft/item/ItemStack;>;", l42, l41, 3);
			mv.visitLocalVariable("stack", "Lnet/minecraft/item/ItemStack;", null, l45, l43, 4);
			mv.visitLocalVariable("displayName", "Ljava/lang/String;", null, l3, l43, 5);
			mv.visitLocalVariable("e", "Ljava/lang/Exception;", null, l50, l49, 6);
			mv.visitLocalVariable("matterCounter", "I", null, l53, l91, 3);
			mv.visitLocalVariable("fuelCounter", "I", null, l54, l91, 4);
			mv.visitLocalVariable("i", "I", null, l67, l91, 5);
			mv.visitLocalVariable("stack", "Lnet/minecraft/item/ItemStack;", null, l70, l68, 6);
			mv.visitLocalVariable("divisionNum", "I", null, l82, l91, 6);
			mv.visitMaxs(4, 8);
			mv.visitEnd();
		}
	}

	class CustomMethodVisitor extends MethodVisitor {
		public CustomMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

		// 上で追加したフィールドに初期値をセット
		public void visitFieldInsn(int opcode, String owner, String name,
	            String desc) {
			super.visitFieldInsn(opcode, owner, name, desc);

			if (opcode == PUTFIELD && owner.equals("moze_intel/projecte/gameObjs/tiles/TransmuteTile")
					&& name.equals("filter") && desc.equals("Ljava/lang/String;")) {
				PEAACoreCorePlugin.logger.info("Add Initial value");
				mv.visitVarInsn(ALOAD, 0);
				mv.visitInsn(ACONST_NULL);
				mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "beforeLockStack", "Lnet/minecraft/item/ItemStack;");
				mv.visitVarInsn(ALOAD, 0);
				mv.visitInsn(ICONST_0);
				mv.visitFieldInsn(PUTFIELD, "moze_intel/projecte/gameObjs/tiles/TransmuteTile", "sameItemSetNum", "I");

			}
		}

	}
}
