/**
 * gen3.java generates bytecode to divide two numbers, then store and display the result
 * @author Jared Rosenberger
 * @version 2/19/24
 */
import static utils.Utilities.writeFile;
import org.objectweb.asm.*;
public class gen3 {
    public static void main(String[] args){
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,"program3", null, "java/lang/Object",null);

        //main visitor
        {
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();

            //StringBuilder init
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitVarInsn(Opcodes.ASTORE, 1);
            
            //Int Section
            //Set String
            setBuilder(mv, "The int quotient is: ");

            //Declare & Store ints
            mv.visitIntInsn(Opcodes.BIPUSH, 100);
            mv.visitVarInsn(Opcodes.ISTORE, 2);
            mv.visitIntInsn(Opcodes.BIPUSH, 30);
            mv.visitVarInsn(Opcodes.ISTORE, 3);

            //Load & Divide ints 1&2
            mv.visitVarInsn(Opcodes.ILOAD, 2);
            mv.visitVarInsn(Opcodes.ILOAD, 3);
            mv.visitInsn(Opcodes.IDIV);

            //Store & append result to StringBuilder
            mv.visitVarInsn(Opcodes.ISTORE, 4);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.ILOAD, 4);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(Opcodes.POP);

            //print and clear StringBuilder
            printBuilder(mv);
            clearBuilder(mv);

            
            //Long Section
            //Set String
            setBuilder(mv, "The long quotient is: ");
            
            //Declare 2 longs
            mv.visitLdcInsn((Long)10000000000l);
            mv.visitVarInsn(Opcodes.LSTORE, 5);
            mv.visitLdcInsn((Long)4000000000l);
            mv.visitVarInsn(Opcodes.LSTORE, 7);
            
            //Load & Divide Longs
            mv.visitVarInsn(Opcodes.LLOAD, 5);
            mv.visitVarInsn(Opcodes.LLOAD, 7);
            mv.visitInsn(Opcodes.LDIV);
            
            //Store & append result to StringBuilder
            mv.visitVarInsn(Opcodes.LSTORE, 9);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.LLOAD, 9);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(Opcodes.POP);
            
            //print and clear StringBuilder
            printBuilder(mv);
            clearBuilder(mv);


            //Float Section
            //Set String
            setBuilder(mv, "The float quotient is: ");
            
            //Declare two floats
            mv.visitLdcInsn((Float)300.397f);
            mv.visitVarInsn(Opcodes.FSTORE, 11);
            mv.visitLdcInsn((Float)187.4879f);
            mv.visitVarInsn(Opcodes.FSTORE, 12);
            
            //Load & divide floats
            mv.visitVarInsn(Opcodes.FLOAD, 11);
            mv.visitVarInsn(Opcodes.FLOAD, 12);
            mv.visitInsn(Opcodes.FDIV);
            
            //Store & append result to StringBuilder
            mv.visitVarInsn(Opcodes.FSTORE, 13);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.FLOAD, 13);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(F)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(Opcodes.POP);
            
            //print and clear StringBuilder
            printBuilder(mv);
            clearBuilder(mv);

            
            //Double Section
            //Set String
            setBuilder(mv, "The double quotient is: ");

            //Declare two doubles
            mv.visitLdcInsn((Double)232148.1654879354);
            mv.visitVarInsn(Opcodes.DSTORE, 14);
            mv.visitLdcInsn((Double)125654.6487987);
            mv.visitVarInsn(Opcodes.DSTORE, 16);

            //Load & Subtract doubles
            mv.visitVarInsn(Opcodes.DLOAD, 14);
            mv.visitVarInsn(Opcodes.DLOAD, 16);
            mv.visitInsn(Opcodes.DDIV);

            //Store & append result to StringBuilder
            mv.visitVarInsn(Opcodes.DSTORE, 18);
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            mv.visitVarInsn(Opcodes.DLOAD, 18);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(D)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(Opcodes.POP);
            
            //Print and clear StringBuilder
            printBuilder(mv);
            clearBuilder(mv);
            
            //Return & End Visit
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(0,0);
            mv.visitEnd();
        }
        cw.visitEnd();
        //Write bytecode to another file
        byte[] b = cw.toByteArray();
        writeFile(b,"program3.class");
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
}//end gen1