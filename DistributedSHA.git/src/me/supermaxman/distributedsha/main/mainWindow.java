package me.supermaxman.distributedsha.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Imports

//End Imports

@SuppressWarnings("serial")
public class mainWindow extends JFrame implements ActionListener {
	
	
    public static String name;
    public static String seed;

    private final JLabel labelUser;
    private final JLabel labelSeed;
    private final JTextField textUser;
    private final JTextField textSeed;
    private final JButton button;
    
    public static JLabel info;
    
    public mainWindow() {
        //Initialize
        labelUser = new JLabel("Enter Name:");
        labelSeed = new JLabel("Enter Seed:");
        
        info = new JLabel("ssss");
        
        textUser = new JTextField("");
        textSeed = new JTextField("");
        
        button = new JButton("Play");
        button.setPreferredSize(new Dimension(100, 25));
        button.addActionListener(this);
        //Arrange
        textUser.setPreferredSize(new Dimension(100, 25));
        textSeed.setPreferredSize(new Dimension(100, 25));
        info.setPreferredSize(new Dimension(400, 25));
        
        setLayout(new FlowLayout());
        add(labelUser);
        add(textUser);
        add(labelSeed);
        add(textSeed);
        add(button);
        add(info);
		info.setVisible(true);
		
        setSize(new Dimension(600, 100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(300, 600));
		getContentPane().doLayout();
        setAlwaysOnTop(true);
        setResizable(false);
		setLocationRelativeTo(null);
        this.setTitle("Generate!");
        this.setVisible(true);
    }


    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == button) {

            name = textUser.getText();
            seed = textSeed.getText();

            if (name.equals("") || name.length() > 20) {
            	//errors
                return;
            }
			if (seed.equals("") || seed.length() > 20) {
			    //errors
                return;
            }

        }
    }

    //
}