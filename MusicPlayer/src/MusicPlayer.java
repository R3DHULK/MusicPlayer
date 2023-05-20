import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;

public class MusicPlayer extends JFrame implements ActionListener {

    private JButton playButton;
    private JButton stopButton;
    private JButton uploadButton;
    private Clip clip;

    public MusicPlayer() {
        setTitle("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        playButton = new JButton("Play");
        playButton.addActionListener(this);
        add(playButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        add(stopButton);

        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(this);
        add(uploadButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicPlayer());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            playMusic("path/to/your/music/file.wav");
        } else if (e.getSource() == stopButton) {
            stopMusic();
        } else if (e.getSource() == uploadButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                playMusic(selectedFile.getAbsolutePath());
            }
        }
    }

    private void playMusic(String filepath) {
        try {
            File file = new File(filepath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioStream.getFormat();

            if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                JOptionPane.showMessageDialog(this, "Unsupported audio format. Please select a different file.");
                return;
            }

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            JOptionPane.showMessageDialog(this, "Unsupported audio format. Please select a different file.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
