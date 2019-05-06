package Configuration;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;;

//Format d'affichage des logs dans la console
public class FormatLog extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuffer s = new StringBuffer(200);
        if(record.getLevel().equals(Level.SEVERE)) {
            s.append("\033[1;31mGrave        :\033[1;37m ");
        }
        else if(record.getLevel().equals(Level.WARNING)) {
            s.append("\033[1;33mAvertissement:\033[1;37m ");
        }
        else if(record.getLevel().equals(Level.INFO)) {
            s.append("\033[1;34mInfos        :\033[1;37m ");
        }
        else {
            s.append("Niveau inconnu");
        }

        s.append(record.getMessage()+"\n");

        return s.toString();
    }

    // entête du fichier de log
    public String getHead(Handler h) {
        return "";
    }

    // fin du fichier de log
    public String getTail(Handler h) {
        return "";
    }
}