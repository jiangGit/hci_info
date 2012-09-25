/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.scauhci.official.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.scauhci.official.Config;

/**
 *
 * @author jiang
 */
public class GetPhotoUtil {
   
    private static String urlPrefix = Config.urlPrefix;
    private static String filePath = Config.downloadAvatarPath;
    public static File getPhoto(String id) {
            InputStream in;
            File file=null;

    
            
        try {
            URL url = new URL(urlPrefix + id+".jpg");
            try {
                in = url.openStream();

            } catch (IOException ex) {
                Logger.getLogger(GetPhotoUtil.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            file=new File(filePath+"/"+id+".jpg");
             //System.err.println(filePath);
            FileOutputStream fos=null;
            try {
                 fos = new FileOutputStream(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GetPhotoUtil.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
            byte[] buffer=new byte[1024];
            int size;
            try {
                while ((size=in.read(buffer))!= -1) {
                    fos.write(buffer, 0, size);
                }
                in.close();
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(GetPhotoUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GetPhotoUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return file;
    }

}
