package generator_language_config;

import generator_language_config.ui.MainFrame;

import javax.swing.*;
import java.io.File;

public class UIMain {

    public static final File LanguageFile = new File("E:\\Temple\\XlenLanguage");
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
            }
        });
    }
}
