package editor.FileManagers;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

//Java program to create open or
//save dialog using JFileChooser
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
public class FileChooser extends JFrame implements ActionListener {
	// Jlabel to show the files user selects
    JLabel l;
    private boolean hasFileName;
    private String fileName;
    private boolean saving, loading;
    
 // frame to contains GUI elements
    private JFrame frame;
    private JPanel panel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    boolean loadingOnly;
    private static boolean menuOpen = false;

    public FileChooser()
    {
    	frame = new JFrame("file chooser");
        panel = new JPanel();
        button1 = new JButton("save");
        button2 = new JButton("open");
        button3 = new JButton("ok");
    }
 
    public boolean isHasFileName() {
		return hasFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void openMenu()
    {
		if (menuOpen)
			return;
		menuOpen = true;
    	frame = new JFrame("file chooser");
    	l = new JLabel("no file selected");
        // set the size of the frame
        frame.setSize(400, 400);
 
        // set the frame's visibility
        frame.setVisible(true);
 
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        
 
        // add action listener to the button to capture user
        // response on buttons
        if(!loadingOnly)
            button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
 
        // make a panel to add the buttons and labels
        
 
        // add buttons to the frame
        if(!loadingOnly)
            panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        // set the label to its initial value
        
 
        // add panel to the frame
        panel.add(l);
        frame.add(panel);
 
        //f.show();
    }
    public void actionPerformed(ActionEvent evt)
    {
        // if the user presses the save button show the save dialog
        String com = evt.getActionCommand();
        if (com != "ok" && (com == "save" || com == "open")) {
        	// create an object of JFileChooser class
            JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            // restrict the user to select files of all types
            jFileChooser.setAcceptAllFileFilterUsed(false);

            // set a title for the dialog
            jFileChooser.setDialogTitle("Select a .png file");

            // only allow files of .txt extension
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .png files", "png");
            jFileChooser.addChoosableFileFilter(restrict);

            // invoke the showsSaveDialog function to show the save dialog
            int r;
            if (com.equals("save")) {
            	saving = true;
            	loading = false;
            	r = jFileChooser.showSaveDialog(null);
            }
            else {
            	r = jFileChooser.showOpenDialog(null);
            	saving = false;
            	loading = true;
            }
            	
            // if the user selects a file
            if (r == JFileChooser.APPROVE_OPTION){
      
            	hasFileName = true;
            	fileName = jFileChooser.getSelectedFile().getAbsolutePath();
                l.setText(fileName);
                closeMenu();
            } // if the user cancelled the operation
            else {
            	hasFileName = false;
            	l.setText("the user cancelled the operation");    
            }         
        } else {
        	closeMenu();
        }
         
     
    }
    
    private void closeMenu() {
    	menuOpen = false;
    	frame.dispose();
    	l.setText(null);;
    }

	public boolean isSaving() {
		return saving;
	}

	public boolean isLoading() {
		return loading;
	}
	
	public boolean getMenuState() {
		return menuOpen;
	}
	
	public void resetChooser() {
		hasFileName = false;
		saving = false;
		loading = false;
		
	}

    public void setLoadOnly(boolean loading){
        loadingOnly = loading;
    }
}