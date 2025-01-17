package generator_language_config.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MainFrame extends JFrame implements ActionListener {
    private JButton excelToXmlButton;
    private JButton xmlToExcelButton;
    private JButton StringsToExcelButton;
    private JButton excelToStringsButton;

    public MainFrame() {
        super("多语言工具-Metal");
        initUI();
        this.setResizable(false);
        this.setSize(500, 550);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static String androidKey = "Android-Excel生成XML";
    public static String iosKey = "IOS-Excel生成Strings";

    private void initUI() {
        this.setLayout(null);

        excelToXmlButton = new JButton(androidKey);
        excelToXmlButton.setBounds(130, 100, 240, 40);
        this.add(excelToXmlButton);

        xmlToExcelButton = new JButton("XML生成Excel");
        xmlToExcelButton.setBounds(130, 200, 240, 40);
        this.add(xmlToExcelButton);

        excelToStringsButton = new JButton(iosKey);
        excelToStringsButton.setBounds(130, 300, 240, 40);
        this.add(excelToStringsButton);


        StringsToExcelButton = new JButton("Strings生成Excel");
        StringsToExcelButton.setBounds(130, 400, 240, 40);
        this.add(StringsToExcelButton);

        setEvent();
    }

    private void setEvent() {
        excelToXmlButton.addActionListener(this);
        xmlToExcelButton.addActionListener(this);
        excelToStringsButton.addActionListener(this);
        StringsToExcelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals(androidKey)) {
            MainFrame.this.dispose();
            ExcelToXmlFrame excelToXmlFrame = new ExcelToXmlFrame();
        } else if (command.equals("XML生成Excel")) {
            MainFrame.this.dispose();
            XmlToExcelFrame xmlToExcelFrame = new XmlToExcelFrame();
        } else if (command.equals(iosKey)) {
            MainFrame.this.dispose();
            ExcelToStringsFrame excelToStringsFrame = new ExcelToStringsFrame();
        } else if (command.equals("Strings生成Excel")) {
            MainFrame.this.dispose();
            StringsToExcelFrame excelToStringsFrame = new StringsToExcelFrame();
        }
    }
}
