package hr.fer.zemris.java.hw11.notepad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class JNotepad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	private	String file = "file";
	private String edit = "edit";
	private String open = "open";
 	private String save = "save";
	private String delete = "delete";
	private	String toggle = "toggle";
	private	String exit = "exit";
	private	String openDescription = "openDescription"; 
	private	String saveDescription = "saveDescription";
	private	String deleteDescription = "deleteDescription";
	private	String toggleDescription = "toggleDescription";
	private	String exitDescription = "exitDescription";
	private String error = "error";
	private String openError = "openError";
	private String readError = "readError";
	private String warning = "warning";
	private String saveWarning = "saveWarning";
	private String writeError = "writeError";
	private String info = "info";
	private String saveInfo = "saveInfo";
	private String language = "language";
	private String hr = "hr";
	private String en = "en";
	
	public JNotepad() {
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		
		initGUI();
	}
	
	private void initGUI() {
		
		editor = new JTextArea();
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);
		
		createActions();
		createMenus();
		createToolbars();
		
	}

	private void createActions() {
		openDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")); 
		openDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_O);
		
		saveDocumentAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")); 
		saveDocumentAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_S);  
		
		deleteSelectedPartAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("F2")); 
		deleteSelectedPartAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_D);  
		
		toggleCaseAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control F3")); 
		toggleCaseAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_T); 
		
		
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X"));
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyEvent.VK_X);
	}

	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new LocalizableMenu(file, flp);
		
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		JMenu editMenu = new LocalizableMenu(edit, flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(deleteSelectedPartAction));
		editMenu.add(new JMenuItem(toggleCaseAction));
		

		JMenu languages = new LocalizableMenu(language, flp);
		menuBar.add(languages);
		
		final JRadioButtonMenuItem hrButton = new JRadioButtonMenuItem(hr);
		final JRadioButtonMenuItem enButton = new JRadioButtonMenuItem(en);
		
		hrButton.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(hrButton.isSelected()) {
					LocalizationProvider.getInstance().setLanguage("hr");
				}
			}
		});
		enButton.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(enButton.isSelected()) {
					LocalizationProvider.getInstance().setLanguage("en");
				}
			}
		});
		
		ButtonGroup grupa = new ButtonGroup();
		grupa.add(hrButton);
		grupa.add(enButton);
		
		hrButton.setSelected(true);
		languages.add(hrButton);
		languages.add(enButton);
		
		this.setJMenuBar(menuBar);
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Alati");
		toolBar.setFloatable(true);
		
		
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(deleteSelectedPartAction));
		toolBar.add(new JButton(toggleCaseAction));
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	
	private Action openDocumentAction = new LocalizableAction(open, openDescription, flp)  {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepad.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepad.this, 
						flp.getString(openError), 
						flp.getString(error), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(
						JNotepad.this, 
						flp.getString(readError), 
						flp.getString(error), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			editor.setText(tekst);
			openedFilePath = filePath;
		}
	};
	
	private Action saveDocumentAction = new LocalizableAction(save, saveDescription, flp)  {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(openedFilePath==null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(JNotepad.this)!=JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepad.this, 
							flp.getString(saveWarning), 
							flp.getString(warning), 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JNotepad.this, 
						flp.getString(writeError), 
						flp.getString(error), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(
					JNotepad.this, 
					flp.getString(saveInfo), 
					flp.getString(info),  
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action deleteSelectedPartAction = new LocalizableAction(delete, deleteDescription, flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			if(len==0) return;
			int offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	
	private Action toggleCaseAction = new LocalizableAction(toggle, toggleDescription, flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot()-editor.getCaret().getMark());
			int offset = 0;
			if(len!=0) {
				offset = Math.min(editor.getCaret().getDot(),editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	
	private Action exitAction = new LocalizableAction(exit, exitDescription, flp)  {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JNotepad().setVisible(true);
			}
		});
	}

}
