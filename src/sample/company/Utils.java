package sample.company;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created with Intellij IDEA.
 * Project name: socketObjectTransfer.
 * Date: 14.08.2016.
 * Time: 16:13.
 * To change this template use File|Setting|Editor|File and Code Templates.
 */
public class Utils {

    private SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

    public String getCurrentDateTime() {
        return df.format(new Date());
    }

    public String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public int getLogCounter(long length) {
        int divider = 1;
        double divedResult = length / (1024d * divider);

        while (divedResult >= 16000) {
            divedResult = length / (1024d * divider);
            divider++;
        }

        return divider;
    }

    private String getProperties(String key) {
        String result = "";
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = getClass().getClassLoader().getResourceAsStream("settings.properties");

            // load a properties file
            prop.load(input);
            // get the property value and print it out

            result = prop.getProperty(key);


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public String getServerIpAddress(){
        return getProperties("server.ip.adders");
    }
    public int getServerPort(){
        return Integer.parseInt(getProperties("server.port"));
    }

    public String getArchivePath() {
        return getProperties("file.archivePath");
    }

    public int getServerPort2() {
        return Integer.parseInt(getProperties("server.port2"));
    }

    public String getFolderPath() {
        return getProperties("file.folderPath");
    }
}
