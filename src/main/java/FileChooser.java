import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileChooser extends JFrame {

    //    JButton open = null;
    private JButton srcBtn, destBtn, confirmBtn;
    private String src, target;
    private TextField statusText;
    private OnConfirmListener listener;

    private static FileChooser INSTANCE = null;

    private FileChooser() {
        srcBtn = new JButton("choose src");
        destBtn = new JButton("choose target");
        confirmBtn = new JButton("OK");
        statusText = new TextField();

        this.add(srcBtn, BorderLayout.WEST);
        this.add(destBtn, BorderLayout.EAST);
        this.add(confirmBtn, BorderLayout.CENTER);
        this.add(statusText, BorderLayout.SOUTH);
        this.setBounds(400, 200, 400, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        srcBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "Choose");
                src = jfc.getSelectedFile().getAbsolutePath();
            }
        });
        destBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                jfc.showDialog(new JLabel(), "Choose");
                target = jfc.getSelectedFile().getAbsolutePath();
            }
        });
        confirmBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                statusText.setText("copying...");
                listener.onConfirm();
            }
        });
    }

    public static FileChooser getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileChooser();
        }
        return INSTANCE;
    }

    interface OnConfirmListener {
        void onConfirm();
    }

    public void setOnConfirmListener(OnConfirmListener listener) {
        this.listener = listener;
    }

    public String getSrc() {
        return src;
    }

    public String getTarget() {
        return target;
    }

    public TextField getStatusText() {
        return statusText;
    }
}
