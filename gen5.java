/**
 * gen5.java generates bytecode to delcare and print String variables
 * @author Jared Rosenberger
 * @version 2/19/24
 */
import static utils.Utilities.writeFile;
import org.objectweb.asm.*;
public class gen5 {
    public static void main(String[] args){
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,"program5", null, "java/lang/Object",null);

        //main visitor
        {
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();

            //Declare and store Strings
            mv.visitLdcInsn((String)"Hello World");
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            mv.visitLdcInsn((String)"This is a String Variable");
            mv.visitVarInsn(Opcodes.ASTORE, 2);
            mv.visitLdcInsn((String)"42");
            mv.visitVarInsn(Opcodes.ASTORE, 3);
            mv.visitLdcInsn((String)"True");
            mv.visitVarInsn(Opcodes.ASTORE, 4);

            //print Strings
            for(int i = 1; i<5; i++) {
                print(mv, i);
            }

            //Return & End Visit
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }
        cw.visitEnd();
        //Write bytecode to another file
        byte[] b = cw.toByteArray();
        writeFile(b,"program5.class");
        System.out.println("Done!");
    }//end main

    /**
     * print prints out a string stored at the given index
     * @param m is the current method visitor
     * @param index is the index a string is stored at
     */
    private static void print(MethodVisitor m, int index) {
        m.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        m.visitVarInsn(Opcodes.ALOAD, index);
        m.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }//end printBuilder
}//end gen5