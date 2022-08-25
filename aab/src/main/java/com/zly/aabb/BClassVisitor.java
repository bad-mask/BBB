package com.zly.aabb;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

class BClassVisitor extends ClassVisitor {

    BClassVisitor(ClassVisitor nextVisitor) {
        super(Opcodes.ASM5, nextVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor visitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        MethodVisitor newVisitor = new AdviceAdapter(Opcodes.ASM5, visitor, access, name, descriptor) {
            @Override
            protected void onMethodEnter() {
                System.out.println("access:" + access + ", name:" + name + " , descriptor:" + descriptor + " ===> onMethodEnter");
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int opcode) {
                System.out.println("access:" + access + ", name:" + name + " , descriptor:" + descriptor + " ===> onMethodExit opcode:$opcode   <===");
                super.onMethodExit(opcode);
            }
        };
        return newVisitor;
    }
}
