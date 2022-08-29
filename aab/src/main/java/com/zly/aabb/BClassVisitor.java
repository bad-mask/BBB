package com.zly.aabb;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

class BClassVisitor extends ClassVisitor {

    BClassVisitor(ClassVisitor nextVisitor) {
        super(Opcodes.ASM5, nextVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor visitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        // AdviceAdapter 是 MethodVisitor 的子类，使用 AdviceAdapter 可以更方便的修改方法的字节码。
        // AdviceAdapter其中几个重要方法如下：
        // void visitCode()：表示 ASM 开始扫描这个方法
        // void onMethodEnter()：进入这个方法
        // void onMethodExit()：即将从这个方法出去
        // void onVisitEnd()：表示方法扫描完毕
        MethodVisitor newVisitor = new AdviceAdapter(Opcodes.ASM5, visitor, access, name, descriptor) {
            int slotIndex = 0;

            @Override
            protected void onMethodEnter() {
                slotIndex = newLocal(Type.LONG_TYPE);
                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                visitVarInsn(LSTORE, slotIndex);
                visitLdcInsn("zly_1111");
                visitLdcInsn("\u8fdb\u5165\u65f6\u95f4: ");
                visitVarInsn(LLOAD, slotIndex);
                visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                visitMethodInsn(INVOKESTATIC, "kotlin/jvm/internal/Intrinsics", "stringPlus", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;", false);
                visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                visitInsn(POP);
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int opcode) {
                visitLdcInsn("zly_1111");
                visitLdcInsn( "test get result");
                visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                visitInsn(POP);

                int slotIndex2 = newLocal(Type.LONG_TYPE);
                visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                visitVarInsn(LSTORE, slotIndex2);
                visitLdcInsn("zly_1111");
                visitLdcInsn(name + " ----- \u79bb\u5f00\u65f6\u95f4\u5dee: ");
                visitVarInsn(LLOAD, slotIndex2);
                visitVarInsn(LLOAD, slotIndex);
                visitInsn(LSUB);
                visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                visitMethodInsn(INVOKESTATIC, "kotlin/jvm/internal/Intrinsics", "stringPlus", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;", false);
                visitMethodInsn(INVOKESTATIC, "android/util/Log", "e", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                visitInsn(POP);

                super.onMethodExit(opcode);
            }
        };
        return newVisitor;
    }
}






















