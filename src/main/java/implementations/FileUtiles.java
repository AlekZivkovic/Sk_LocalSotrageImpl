package implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

public class FileUtiles {

    public FileUtiles() {
    }

    public Map<String, Integer> readFiles() {
        return null;
    }


    public String getFileDir(String filepath) {
        String parent="";
        try {
            File file=new File(filepath);
            parent=file.getParent();
        }catch (NullPointerException e){
            System.out.println("Prosledjen ne postojuci file: FU:22");
        }
        return  parent;
    }

    public boolean addFile(String file, String path,String koren) {
        if(!path.contains(koren))return  false;
        File sk=new File(path);
        if(!sk.exists()){
            sk.mkdirs();
        }
        File source = new File(file);
        File dest = new File(path+"\\"+source.getName());
        try {
            Files.copy(source.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Desio se error pri dodavanju file : "+source.getName()+" FU:41");
            //e.printStackTrace();
            return  false;
        }

        return  true;
    }
    //ukoliko zelimo da dopustimo da izbrise koren onda ova linija se batali
    public boolean deleteFile(String filepath, String koren) {
        if(!filepath.contains(koren) || filepath.equalsIgnoreCase(koren))return false;

        File f=new File(filepath);

        return f.delete();
    }

    public boolean createFiles(String name, String path, boolean dir, String koren) {
        if(!path.contains(koren))return false;

        File f=new File(path+"\\"+name);
        if(dir){
            f.mkdirs();
        }else{
            try {
                new File(path).mkdirs();
                f.createNewFile();
            } catch (IOException e) {
                //e.printStackTrace();
                System.out.println("Neuspeh pravljena file "+name+" "+path+" FU:69");
                return false;
            }
        }


        return true;
    }
}
