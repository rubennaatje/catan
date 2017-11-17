package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.net.MalformedURLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class SplashScreenView {
	
	private boolean doneLoading = false;
    private JDialog dialog;
    private JFrame frame;
    private JProgressBar progress;

	public SplashScreenView() {
		try {
			initUI();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

    protected void initUI() throws MalformedURLException {
        showSplashScreen();
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(20);// Simulate loading
                    publish(i);// Notify progress
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                progress.setValue(chunks.get(chunks.size() - 1));
            }

            //Splashscreen is done loading
            @Override
            protected void done() {
            	doneLoading = true;
                hideSplashScreen();
            }

        };
        worker.execute();
    }
    
    //Splashscreen disappears
    protected void hideSplashScreen() {
        dialog.setVisible(false);
        dialog.dispose();
    }

    protected void showSplashScreen() throws MalformedURLException {
        dialog = new JDialog((Frame) null);
        dialog.setModal(false);
        dialog.setUndecorated(true);
        //splashscreen background image
        JLabel background = new JLabel(new ImageIcon(("@../../images/Splashscreen.png")));  
        background.setLayout(new BorderLayout());
        dialog.add(background);
        //spalshscreen text
        JLabel text = new JLabel("Loading, please wait...");  
        text.setForeground(Color.BLACK);
        text.setBorder(BorderFactory.createEmptyBorder(100, 50, 100, 50));
        background.add(text);
        progress = new JProgressBar();
        background.add(progress, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        progress.setStringPainted(true);
        progress.setForeground(Color.RED);
    }

    public boolean getDoneLoading(){
    	return doneLoading;
    }
}
