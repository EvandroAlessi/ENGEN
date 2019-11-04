/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrossCutting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Responsável por gravar em arquivo um LOG de erros
 * @author Evandro Alessi
 * @author Eric Ueta
 */
public class Log {

    /**
     *
     * @param exeption
     */
    public static void saveLog(Exception exeption) {
        try {
            File log = new File("log.txt");
            if (log.createNewFile()) {
                System.out.println("criado: " + log.getName());
            } else {
                System.out.println("já existe.");
            }

            try (FileWriter escrever = new FileWriter(log, true)) {
                Date data = Calendar.getInstance().getTime();
                SimpleDateFormat dataPTBR = new SimpleDateFormat("EEEEEE',' dd 'de' MMMM 'de' yyyy 'às' HH:mm:ss");
                escrever.append("\n" + dataPTBR.format(data) + "\n");
                escrever.append("Mensagem:" + exeption.getMessage() + "\n");
                escrever.append("StackTrace:" + Arrays.toString(exeption.getStackTrace()) + "\n");
            }
        } catch (IOException e) {
            System.out.println("erro.");

        }
    }
}
