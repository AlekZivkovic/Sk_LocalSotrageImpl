package implementations;

import model.FAFile;
import utility.FUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import java.util.*;


public class FileUtiles {
    private final FUtility fUtility;
    public FileUtiles() {
        this.fUtility=new FUtility();
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
            //noinspection ResultOfMethodCallIgnored
            sk.mkdirs();
        }
        File source = new File(file);
        File dest = new File(path+File.separator+source.getName());
        try {
            Files.copy(source.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
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

        File f=new File(path+File.separator+name);
        if(dir){
            //noinspection ResultOfMethodCallIgnored
            f.mkdirs();
        }else{
            try {
                //noinspection ResultOfMethodCallIgnored
                new File(path).mkdirs();
                //noinspection ResultOfMethodCallIgnored
                f.createNewFile();
            } catch (IOException e) {
                //e.printStackTrace();
                //System.out.println("Neuspeh pravljena file "+name+" "+path+" FU:69");
                return false;
            }
        }


        return true;
    }

    //Prolazimo pomocu BFS algoritma
    public Map<String, Integer> readFiles(String koren) {

        Map<String,Integer> map=new HashMap<>();
        List<String> queue=new LinkedList<>();


        queue.add(koren);
        while (queue.size() != 0){
            //noinspection rawtypes
            File ftren= new File((String) ((LinkedList) queue).poll());
            int sum=0;
            for(File f: Objects.requireNonNull(ftren.listFiles())){
                if(f.isDirectory()){
                    queue.add(f.getPath());
                }
                sum++;
            }
            //dodavanje u mapu
            map.put(ftren.getPath(),sum);
        }



        return map;
    }

    public List<FAFile> listSkladiste(String koren) {
        List<FAFile> list=new LinkedList<>();
        List<String> queue=new LinkedList<>();
        queue.add(koren);
        dfs(list,queue,0);

        return  list;
    }

    private void dfs(List<FAFile> list, List<String> queue,int depth) {

        if(queue.size() == 0 )return;

            @SuppressWarnings("rawtypes")
            File ftren= new File((String) ((LinkedList) queue).poll());

            FAFile faf=new FAFile();
            faf.setFileId(Integer.toString(depth++));
            faf.setName(ftren.getPath());
            list.add(faf);


            for(File file: Objects.requireNonNull(ftren.listFiles())){
                if(file.isDirectory()){
                    queue.add(file.getPath());
                    dfs(list,queue,depth);
                    continue;
                }
                FAFile faTren=new FAFile();
                faTren.setFileId(Integer.toString(depth));
                faTren.setName(file.getPath());
                list.add(faTren);

            }

    }


    public boolean moveFile(String file, String path, String koren) {
        if(!file.contains(koren) || !path.contains(koren))return  false;

        File source=new File(file);
        if(!source.exists()){
            //System.out.println("Error pri pomeranju fajlova: source ne postoji : FU:moveFile");
            return false;
        }
        File dest=new File(path);
        //noinspection ResultOfMethodCallIgnored
        dest.mkdirs();

        try {
            Files.move
                    (Paths.get(source.getPath()),
                            Paths.get(dest.getPath() + File.separator + source.getName()), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            //System.out.println("Nastao error pri pomeranju fajlova: FU:moveFile");
            return false;
            //e.printStackTrace();
        }
        return true;
    }


    public  boolean downloadFile(String filepath, String koren) {
        if(!filepath.contains(koren))return  false;

        String home = System.getProperty("user.home");
        File dest = new File(home+"/Desktop/");

        try {
            File f=new File(filepath);

            fUtility.copyDirectory(filepath,dest.getPath(),f.getParentFile().getPath().length());

        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("Desio se error pri skidanju file : FU:downloadFile");
        }

        return  true;
    }


    public long getFileSize(String file) {
        return fUtility.fileSize(Paths.get(file));
    }
}
