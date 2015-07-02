package rebus;

import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.SwingConstants;



public class GUI {
	

	
	private Font font;
	@SuppressWarnings("rawtypes")
	private JComboBox selectGameMode;
	@SuppressWarnings("rawtypes")
	private JComboBox wordStrengthDropDown;	
	@SuppressWarnings("rawtypes")
	private JComboBox selectWordLength;
	Rebus rebus = new Rebus();

	//package
	JFrame frame;
	@SuppressWarnings("rawtypes")
	JTabbedPane tabStrip;
	private JTextField wordPoolSizeTextField;



	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		//main window
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 524, 590);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Font
		font = null;
		InputStream fontFile = getClass().getResourceAsStream(Config.FontFile);
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 24);
		} catch (FontFormatException | IOException e1) {
			e1.printStackTrace();
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);

		//tab strip
		tabStrip = new JTabbedPane(JTabbedPane.TOP);
		tabStrip.setBorder(null);
		tabStrip.setBounds(10, 11, 498, 539);
		frame.getContentPane().add(tabStrip);

		//init welcome panel
		JPanel panelWelcome = new JPanel();
		panelWelcome.setBackground(Color.GRAY);
		panelWelcome.setBounds(10, 51, 424, 431);
		panelWelcome.setLayout(null);

		//number of Game Modes to generate
		selectGameMode = new JComboBox(populateGameModes());
		selectGameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.rebusX=(selectGameMode.getSelectedIndex() + 1);
				System.out.println("Rebus: " + Config.rebusX + " selected");
				updateWordPoolSize();
			}
		});
		selectGameMode.setBounds(257, 84, 100, 25);
		panelWelcome.add(selectGameMode);

		//label # puzzles to generate
		JLabel lblGameModesToGen = new JLabel("Select Game Mode: ");
		lblGameModesToGen.setForeground(Color.BLACK);
		lblGameModesToGen.setBounds(113, 89, 151, 14);
		panelWelcome.add(lblGameModesToGen);
		tabStrip.addTab( "Welcome", panelWelcome );


		//crossword logo image icon
		//ImageIcon crosswordsIcon = new ImageIcon(getClass().getResource(Config.AppIcon32));
		JLabel cwIconLabel = new JLabel("");
		cwIconLabel.setBounds(95, 25, 32, 32);
		//cwIconLabel.setIcon(crosswordsIcon);
		cwIconLabel.setVisible(true);
		panelWelcome.add(cwIconLabel);


		//label welcome header
		JLabel lblWelcomeHeader = new JLabel("Rebus!");
		lblWelcomeHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeHeader.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblWelcomeHeader.setBounds(0, 25, 458, 29);
		panelWelcome.add(lblWelcomeHeader);

		JButton letsPlayButton1 = new JButton("Let's Play!");
		letsPlayButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				rebus.findGameWords();
			}
		});
		letsPlayButton1.setBounds(105, 379, 131, 56);
		panelWelcome.add(letsPlayButton1);

		JButton generateHTML1 = new JButton("Generate HTML");
		generateHTML1.setBounds(279, 377, 131, 61);
		panelWelcome.add(generateHTML1);

		//init config panel
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(10, 51, 424, 431);
		panelConfig.setLayout(null);
		tabStrip.addTab( "Config", panelConfig );

		//------------------------------------------------------
		//---------------------CONFIG PANEL---------------------
		//------------------------------------------------------

		//Word Length
		selectWordLength = new JComboBox();
		selectWordLength.setBounds(280, 257, 66, 20);
		panelConfig.add(selectWordLength);

		//max elapsed time
		wordStrengthDropDown = new JComboBox();
		wordStrengthDropDown.setBounds(280, 288, 66, 20);
		panelConfig.add(wordStrengthDropDown);

		//label rows / columns
		JLabel lblRows = new JLabel("Max Word Length: ");
		lblRows.setForeground(Color.DARK_GRAY);
		lblRows.setBounds(119, 260, 151, 14);
		panelConfig.add(lblRows);

		//label time elapsed
		JLabel wordStrengthLabel = new JLabel("Word Strength");
		wordStrengthLabel.setForeground(Color.DARK_GRAY);
		wordStrengthLabel.setBounds(119, 291, 151, 14);
		panelConfig.add(wordStrengthLabel);

		//label config tab header
		JLabel lblConfigHeader = new JLabel("Setup Configuration");
		lblConfigHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfigHeader.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblConfigHeader.setBounds(0, 25, 493, 35);
		panelConfig.add(lblConfigHeader);

		//button generate puzzles
		JButton btnPlay = new JButton("Let's Play!");
		btnPlay.setBounds(106, 393, 140, 50);
		btnPlay.setVisible(true);
		panelConfig.add(btnPlay);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				rebus.findGameWords();
			}

		});

		//generate this puzzle button
		JButton btnGenerateHTML = new JButton("Generate HTML");
		btnGenerateHTML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnGenerateHTML.setBounds(260, 393, 140, 50);
		panelConfig.add(btnGenerateHTML);
		panelConfig.setVisible(true);
		panelConfig.setLayout(null);

		wordPoolSizeTextField = new JTextField();
		wordPoolSizeTextField.setEditable(false);
		wordPoolSizeTextField.setBounds(280, 80, 66, 20);
		panelConfig.add(wordPoolSizeTextField);
		wordPoolSizeTextField.setColumns(10);

		JLabel wordPoolSizeLabel = new JLabel("Size of Current Word Pool");
		wordPoolSizeLabel.setBounds(119, 77, 151, 27);
		panelConfig.add(wordPoolSizeLabel);

		JLabel topicLabel = new JLabel("Topic");
		topicLabel.setBounds(119, 117, 46, 14);
		panelConfig.add(topicLabel);

		JComboBox topicComboBox = new JComboBox(populateTopicBox());
		topicComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.topic = (String) topicComboBox.getSelectedItem();
				System.out.println("Selected " + Config.topic + " as the new topic");
				updateWordPoolSize();
			}
		});
		topicComboBox.setBounds(279, 114, 191, 20);
		panelConfig.add(topicComboBox);
		
		JLabel solutionLengthLabel = new JLabel("Solution Length");
		solutionLengthLabel.setBounds(119, 153, 101, 14);
		panelConfig.add(solutionLengthLabel);
		
		JComboBox solutionLengthComboBox = new JComboBox(populateSolutionLengthBox());
		solutionLengthComboBox.setSelectedIndex(3);
		solutionLengthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionLength = (Integer) solutionLengthComboBox.getSelectedItem();
				System.out.println("Selected new solution length of " + Config.solutionLength);
			}
		});
		solutionLengthComboBox.setBounds(280, 150, 66, 20);
		panelConfig.add(solutionLengthComboBox);
		
		JLabel languageLabel = new JLabel("Language");
		languageLabel.setBounds(119, 187, 89, 14);
		panelConfig.add(languageLabel);
		
		JComboBox languageComboBox = new JComboBox(populateLanguageBox());
		languageComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String language = (String) languageComboBox.getSelectedItem();
				Config.LANGUAGE = language.substring(0,2);
				System.out.println("Selected " + Config.LANGUAGE + " as the new Language");
			}
		});
		languageComboBox.setBounds(280, 184, 66, 20);
		panelConfig.add(languageComboBox);

		updateWordPoolSize();
	}

	public Vector<String> populateGameModes() {
		Vector<String> retVal = new Vector<String>();
		retVal.add("Rebus 1");
		retVal.add("Rebus 2");
		retVal.add("Rebus 3");
		retVal.add("Rebus 4");
		retVal.add("Rebus 5");
		retVal.add("Rebus N");
		return retVal;
	}

	/**
	 * Generates a new Config.gameCollection based on the current settings
	 * Updates the field that display the size of the current collection
	 */
	public void updateWordPoolSize() {
		rebus.generateWordPool();
		wordPoolSizeTextField.setText(String.valueOf(Config.gameCollection.size())); 
	}
	
	/**
	 * Populates the topic list
	 *
	 *
	 * @return
	 */
	private Object[] populateTopicBox() {
		ArrayList<String> topicStrings = new ArrayList<String>();
		topicStrings.add("Any");
		Hashtable<String, ArrayList<BigWord>> selects = Config.entireCollection
				.getBigWordsTopicsTable();
		for (Entry<String, ArrayList<BigWord>> entry : selects.entrySet()) {
			String key = entry.getKey();
			topicStrings.add(key);
		}
		return topicStrings.toArray();
	}
	
	private Vector<Integer> populateSolutionLengthBox() {
		Vector<Integer> retVal = new Vector<Integer>();
		for (int i = 0; i < 15; i++) {
			retVal.add(i);
		}
		return retVal;
	}
	
	public Vector<String> populateLanguageBox() {
		Vector<String> retVal = new Vector<String>();
		retVal.add("English");
		//retVal.add("Telugu");
		return retVal;
	}
}