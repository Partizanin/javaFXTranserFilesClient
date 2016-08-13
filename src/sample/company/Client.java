package sample.company;

import sample.Controller;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class Client {
    private Controller controller;
    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    public Client() {
    }

    private String[] getProps(){
        Properties prop = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("settings.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String serverIpAddress = prop.getProperty("server.ip.adders");
        String serverPort = prop.getProperty("server.port");
        String filesFolderPath = prop.getProperty("file.folderPath");

        return new String[]{serverIpAddress, serverPort,filesFolderPath};
    }

    public Client(Controller controller) {
        this.controller = controller;
    }

    public void sendFile() {
        File folder = new File(getProps()[2]);
        ArrayList<File> files = new ArrayList<File>();
        File[] arrFiles = folder.listFiles();

        if (arrFiles != null) {
            for (File file : arrFiles) {
                if (file.isFile()) {
                    files.add(file);
                }
            }
            senFiles(files);
        }else {
            controller.sendMessage("В папці '" + folder.getPath() + "' файлів не знайдено!!!","green");
        }
    }

    private void senFiles(ArrayList<File> files) {
        String logMessage = "";
        String errorMessage = "";
        Socket socket = null;
        for (File file : files) {
            try {
                socket = new Socket(getProps()[0], Integer.parseInt(getProps()[1]));
            } catch (IOException e) {
                errorMessage = e.getMessage();
                controller.sendMessage(errorMessage, "red");
                e.printStackTrace();
            }
            try {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                int n = 0;
                byte[] buf = new byte[4092];

                String fileName = file.getName();
                logMessage = df.format(new Date()) + " Знайдено файл: " + fileName;

                System.out.println(logMessage);
                controller.sendLog(logMessage);

                //write file names
//                dos.writeLong(file.length());
                dos.writeUTF(fileName);

                //create new fileinputstream for each file
                FileInputStream fis = new FileInputStream(file);

                //write file to dos
                while ((n = fis.read(buf)) != -1) {
                    dos.write(buf, 0, n);

                }
                dos.flush();
                dos.close();
                logMessage = df.format(new Date()) + " Передано файл: " + fileName;
                controller.sendLog(logMessage);

                System.out.println(logMessage);

            } catch (IOException e) {
                e.printStackTrace();
                errorMessage = e.getMessage();
                controller.sendMessage(errorMessage, "red");
            }


        }
        logMessage = df.format(new Date()) + " Файлы успешно переданы!!!";
        controller.sendMessage(logMessage, "green");
    }

}

