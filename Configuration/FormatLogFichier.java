package Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

//Format d'affichage des logs dans un fichier
public class FormatLogFichier extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuffer s = new StringBuffer(200);
        if(record.getLevel().equals(Level.SEVERE)) { //On regarde le niveau d'affichage
            s.append("Grave        : ");
        }
        else if(record.getLevel().equals(Level.WARNING)) {
            s.append("Avertissement: ");
        }
        else if(record.getLevel().equals(Level.INFO)) {
            s.append("Infos        : ");
        }
        else {
            s.append("Niveau inconnu");
        }

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat ("[HH:mm:ss:SS]");
        s.append(dateFormat.format(date)+" - "); //Affichage de la date
        s.append(record.getMessage()+"\n"); //Affichage du message

        return s.toString();
    }

    // entête du fichier de log
    public String getHead(Handler h) {
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat ("dd/MM/YYYY à HH:mm:ss:SS");
        return "Début session de log le"+ dateFormat.format(date) +"\n";
    }

    // fin du fichier de log
    public String getTail(Handler h) {
        return "Fin log!";
    }
}