package utility;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;

public class FUtility {


    public long fileSize(Path path) {

        File file=new File(String.valueOf(path));
        //ako je file inace prolazi korz dir
        if(!file.isDirectory()) return  file.length();

        final AtomicLong size = new AtomicLong(0);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {

                    System.out.println("skipped: " + file + " (" + exc + ")");
                    // Skip folders that can't be traversed
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                    if (exc != null)
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    // Ignore errors traversing a folder
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
    }


    //downloadFile("D:\\InteliJ\\Test\\test1","D:\\InteliJ\\Test");

    public void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation,int sub)
            throws IOException {
        Files.walk(Paths.get(sourceDirectoryLocation))
                .forEach(source -> {
                    Path destination = Paths.get(destinationDirectoryLocation, (source.toString())
                            .substring(sub));
                    System.out.println("napravljen path : "+destinationDirectoryLocation+source.toString().substring(sub));

                    try {
                        Files.copy(source, destination,StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                });
    }

    public void  copyFile(String filepath,String todest) throws  IOException{
        File source = new File(filepath);
        File dest = new File(todest+source.getName());

        Files.copy(source.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

    }

}
