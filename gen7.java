/**
 * gen7.java generates bytecode to implement a while loop that prints the loop counter
 * @author Jared Rosenberger
 * @version 2/16/24
 */
import static utils.Utilities.writeFile;
import org.objectweb.asm.*;
public class gen7 {
    public static void main(String[] args){
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,"program7", null, "java/lang/Object",null);

        //main visitor
        {
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();

            //label declaration
            Label end = new Label();
            Label loop = new Label();

            //loop counter set to 0 and stored
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitVarInsn(Opcodes.ISTORE, 1);
            
            //begin loop and check if counter >= 10
            mv.visitLabel(loop);
            mv.visitVarInsn(Opcodes.ILOAD, 1);
            mv.visitIntInsn(Opcodes.BIPUSH, 10);
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, end);
            
            //Body of Loop, print value of counter
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitVarInsn(Opcodes.ILOAD, 1);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
            mv.visitIincInsn(1, 1);
            mv.visitJumpInsn(Opcodes.GOTO, loop);

            //Return & End Visit
            mv.visitLabel(end);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }
        cw.visitEnd();
        //Write bytecode to another file
        byte[] b = cw.toByteArray();
        writeFile(b,"program7.class");
        System.out.println("Done!");
    }//end main
}//end gen7