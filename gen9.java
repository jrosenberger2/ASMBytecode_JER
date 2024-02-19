/**
 * gen9.java generates bytecode to accept a user input, accumulate it, and print the total
 * @author Jared Rosenberger
 * @version 2/19/24
 */
import static utils.Utilities.writeFile;
import org.objectweb.asm.*;
public class gen9 {
    public static void main(String[] args){
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,"program9", null, "java/lang/Object",null);

        //main visitor
        {
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();

            //StringBuilder init
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            
            //Scanner init
            mv.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
            mv.visitInsn(Opcodes.DUP);
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
            mv.visitVarInsn(Opcodes.ASTORE, 2);

            //label declaration
            Label end = new Label();
            Label loop = new Label();
            
            //Get double input from user
            setBuilder(mv, "Please input a double");
            printBuilder(mv);
            //Get user input
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextDouble", "()D", false);
            mv.visitVarInsn(Opcodes.DSTORE, 3);
            
            //Declare and store accumulator variable
            mv.visitInsn(Opcodes.DCONST_0);
            mv.visitVarInsn(Opcodes.DSTORE, 5);

            //loop counter
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitVarInsn(Opcodes.ISTORE, 7);
            //begin loop
            mv.visitLabel(loop);
            mv.visitVarInsn(Opcodes.ILOAD, 7);
            mv.visitIntInsn(Opcodes.BIPUSH, 10);
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, end);
            //Body of Loop
            mv.visitVarInsn(Opcodes.DLOAD, 5);
            mv.visitVarInsn(Opcodes.DLOAD, 3);
            mv.visitInsn(Opcodes.DADD);
            mv.visitVarInsn(Opcodes.DSTORE, 5);
            mv.visitIincInsn(7, 1);
            mv.visitJumpInsn(Opcodes.GOTO, loop);

            //print total and end
            mv.visitLabel(end);
            clearBuilder(mv);
            setBuilder(mv, "Your total is: ");
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.DLOAD, 5);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(D)Ljava/lang/StringBuilder;", false);
            printBuilder(mv);
            //Return & End Visit
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }
        cw.visitEnd();
        //Write bytecode to another file
        byte[] b = cw.toByteArray();
        writeFile(b,"program9.class");
        System.out.println("Done!");
    }//end main

    /**
     * clearBuilder empties the string builder object used to print outputs
     * clearBuilder assumes that there is a StringBuilder Object at index 1
     * @param m is the MethodVisitor being used to visit the main method
     */
    private static void clearBuilder(MethodVisitor m) {
        m.visitVarInsn(Opcodes.ALOAD, 1);
        m.visitInsn(Opcodes.ICONST_0);
        m.visitVarInsn(Opcodes.ALOAD, 1);
        m.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "length", "()I", false);
        m.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "delete", "(II)Ljava/lang/StringBuilder;", false);
        m.visitInsn(Opcodes.POP);
    }//end clearBuilder

    /**
     * setBuilder sets the string section for each println statement
     * setBuilder assumes that there is a Stringbuilder Object at index 1
     * @param m is the MethodVisitor being used to visit the main method
     * @param str is the String to append to the StringBuilder
     */
    private static void setBuilder(MethodVisitor m, String str) {
        m.visitVarInsn(Opcodes.ALOAD, 1);
        m.visitLdcInsn((String)str);
        m.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        m.visitInsn(Opcodes.POP);
    }//end setBuilder

    /**
     * printBuilder prints the current StringBuilder Object
     * printBuilder assumes there is an Object at index 1
     * @param m is the MethodVisitor being used to visit the main method
     */
    private static void printBuilder(MethodVisitor m) {
        m.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        m.visitVarInsn(Opcodes.ALOAD, 1);
        m.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/Object;)V", false);
    }//end printBuilder
}//end gen9