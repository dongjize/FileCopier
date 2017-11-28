import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDirectory {
    public static void main(final String[] args) {
        final FileChooser fileChooser = FileChooser.getInstance();
        fileChooser.getStatusText().setText("");

        fileChooser.setOnConfirmListener(new FileChooser.OnConfirmListener() {
            public void onConfirm() {
                copyFileOrDir(fileChooser.getSrc(), fileChooser.getTarget());
                System.out.println();

                fileChooser.getStatusText().setText("finished");
                int response = JOptionPane.showConfirmDialog(null, "Copy finished! Continue?", "Alert", JOptionPane.YES_NO_OPTION);
                if (response == 0) {
                    main(args);
                } else if (response == 1) {
                    System.exit(0);
                }
            }

        });

    }

    /**
     * @param srcPath    需要复制的目录
     * @param targetPath 复制到哪里
     * @Description: 整合两个操作，一个是文件夹的所有目录，然后复制目录下的所有文件到相应的目录
     */
    public static void copyFileOrDir(String srcPath, String targetPath) {
        File targetFile = new File(targetPath);
        targetFile.mkdirs();

        copyAllFiles(srcPath, targetPath);
    }

    public static void copyAllFiles(String srcPath, String targetPath) {
        File f = new File(srcPath);
        File[] fileList = f.listFiles();
        if (f.listFiles() == null) {
            System.out.println("No such directory! ");
            System.exit(1);
        }
//        File ff = new File(targetPath);
//        if (ff.exists()) {
//            deleteDir(ff);
//        }
        for (File f1 : fileList) {
            if (f1.isFile()) {
                String currentPath = targetPath + "\\" + f1.getName();
                File currentFile = new File(currentPath);
                while (currentFile.exists()) {
                    if (!currentFile.isDirectory() && currentPath.trim().endsWith(")")
                            && currentPath.contains("(")) {
                        String count = currentPath.substring(currentPath.lastIndexOf("(") + 1, currentPath.lastIndexOf(")"));
                        int c;
                        try {
                            c = Integer.parseInt(count);
                            StringBuilder sb = new StringBuilder();
                            sb.append(currentPath.substring(0, currentPath.lastIndexOf("(")));
                            sb.append("(").append(++c).append(")");
                            currentPath = sb.toString();
//                            currentPath = currentPath.substring(0, currentPath.lastIndexOf("(")) + "(" + (++c) + ")";
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    } else {
                        currentPath = currentPath + " (1)";
                    }
                    currentFile = new File(currentPath);
                }
                copyFile(srcPath + "\\" + f1.getName(), currentPath);
            }
            //判断是否是目录
            if (f1.isDirectory()) {
                copyAllFiles(f1.getPath(), targetPath);
            }
        }
    }

    /**
     * @param src    源文件的路径
     * @param target 目标文件的路径
     * @Description: 通过字节流复制一个文件
     */
    public static void copyFile(String src, String target) {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(target);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = is.read(buff, 0, buff.length)) != -1) {
                os.write(buff, 0, len);
            }
            System.out.println("copying...");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}