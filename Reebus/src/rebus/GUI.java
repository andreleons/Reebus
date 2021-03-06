package rebus;

import java.awt.EventQueue;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

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

import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI {

	// private Font font;
	Font font = new Font("gautami", Font.PLAIN, 14);
	@SuppressWarnings("rawtypes")
	private JComboBox selectGameMode;
	@SuppressWarnings("rawtypes")
	private JComboBox wordBankWordStrengthMax;
	@SuppressWarnings("rawtypes")
	private JComboBox selectWordLength;
	Rebus rebus = new Rebus();

	// package
	JFrame frame;
	@SuppressWarnings("rawtypes")
	private JTabbedPane tabStrip;
	private SavedGame selectedSavedGame;
	private JList manageGameList;

	private JTextArea manageGameWords;
	private JTextField wordBankSizeTextField;
	private JTextField solutionBankSizeTextField;
	private JTextField solutionWordTextFieldAdmin;
	private JTextArea solutionWordTextAreaAdmin;
	private JList<String> adminWordsJList;
	private JScrollPane scrollPane_1;
	private SavedGameCollection games;
	private JPanel growlerPanelAdmin;
	private JPanel growlerPanelConfig;
	private JButton btnNewButton;
	private JButton generateHTML1;
	private JButton saveGameButton;
	private JButton btnNewButton_2;
	private JButton swapButton;
	private JButton solutionWordButtonAdmin;

	// Growler Message Displays
	Growler growlerAdmin;
	Growler growlerConfig;
	private JTextField manageSolutionWord;

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
		// main window
		frame = new JFrame("Rebus");
		frame.setResizable(false);
		frame.setBounds(100, 100, 793, 836);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		games = new SavedGameCollection();
		games.setGames(games.readGames());

		DefaultListModel model = new DefaultListModel();
		for (SavedGame game : games.getGames()) {
			model.addElement(game);
		}
		manageGameList = new JList();
		manageGameList.setModel(model);
		manageGameList.setCellRenderer(new SavedGameListCellRenderer());
		manageGameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

				SavedGame game = (SavedGame) manageGameList.getSelectedValue();
				if (game != null) {
					manageSolutionWord.setText(game.getSolutionWord());
					manageGameWords.setText("");
					String gameWords = "";

					for (BigWord word : game.getGameWords()) {
						gameWords += word.getProcessedWord() + "\n";
					}
					manageGameWords.setText(gameWords);
					selectedSavedGame = game;
				}
			}
		});

		// Font
		// font = null;
		// InputStream fontFile =
		// getClass().getResourceAsStream(Config.FontFile);
		// try {
		// font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(
		// Font.PLAIN, 24);
		// } catch (FontFormatException | IOException e1) {
		// e1.printStackTrace();
		// }
		// GraphicsEnvironment ge = GraphicsEnvironment
		// .getLocalGraphicsEnvironment();
		// ge.registerFont(font);
		// tab strip
		tabStrip = new JTabbedPane(JTabbedPane.TOP);
		tabStrip.setBorder(null);
		tabStrip.setBounds(10, 11, 767, 787);
		frame.getContentPane().add(tabStrip);

		// init welcome panel
		JPanel panelWelcome = new JPanel();
		panelWelcome.setBackground(Color.GRAY);
		panelWelcome.setBounds(10, 51, 424, 431);
		panelWelcome.setLayout(null);

		tabStrip.addTab("Welcome", panelWelcome);

		// crossword logo image icon
		// ImageIcon crosswordsIcon = new
		// ImageIcon(getClass().getResource(Config.AppIcon32));
		JLabel cwIconLabel = new JLabel("");
		cwIconLabel.setBounds(95, 25, 32, 32);
		// cwIconLabel.setIcon(crosswordsIcon);
		cwIconLabel.setVisible(true);
		panelWelcome.add(cwIconLabel);

		// label welcome header
		JLabel lblWelcomeHeader = new JLabel("Rebus!");
		lblWelcomeHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeHeader
				.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblWelcomeHeader.setBounds(143, 25, 458, 29);

		JLabel lblInstructions = new JLabel("Instructions");
		lblInstructions.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblInstructions.setBounds(113, 150, 523, 25);
		panelWelcome.add(lblInstructions);

		JLabel label = new JLabel(
				"1. To start creating a puzzle click the button below or click on the \"Config\" tab. ");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(113, 206, 606, 14);
		panelWelcome.add(label);

		JLabel lblNewLabel = new JLabel(
				"There you can set all the different options you want to use for generating your puzzle.");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(113, 225, 593, 14);
		panelWelcome.add(lblNewLabel);

		JLabel label_1 = new JLabel(
				"2. After choosing the options for the words you'll be using, you can go to the admin tab");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(113, 270, 606, 14);
		panelWelcome.add(label_1);

		JLabel lblNewLabel_1 = new JLabel(
				"to generate the word pool or choose each individual word you would like to use.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(112, 289, 594, 14);
		panelWelcome.add(lblNewLabel_1);

		JLabel lblOnceYou = new JLabel(
				"3. Once you are satisfied with your puzzle you can go to the manage tab");
		lblOnceYou.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblOnceYou.setBounds(113, 328, 626, 14);
		panelWelcome.add(lblOnceYou);

		JLabel lblNewLabel_2 = new JLabel(
				"and choose to either save the puzzle or generate an HTML file so the puzzle can be played.");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_2.setBounds(113, 347, 593, 14);
		panelWelcome.add(lblNewLabel_2);

		JButton btnNewButton_3 = new JButton("Let's Get Started!");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabStrip.setSelectedIndex(1);
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_3.setBounds(297, 448, 163, 78);
		panelWelcome.add(btnNewButton_3);

		JLabel lblRebus = new JLabel("Rebus!");
		lblRebus.setHorizontalAlignment(SwingConstants.CENTER);
		lblRebus.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 32));
		lblRebus.setBounds(113, 68, 493, 35);
		panelWelcome.add(lblRebus);

		// init config panel
		JPanel panelConfig = new JPanel();
		panelConfig.setBackground(Color.GRAY);
		panelConfig.setBounds(10, 51, 424, 431);
		panelConfig.setLayout(null);
		tabStrip.addTab("Config", panelConfig);

		// ------------------------------------------------------
		// ---------------------CONFIG PANEL---------------------
		// ------------------------------------------------------

		// label # puzzles to generate
		JLabel lblGameModesToGen = new JLabel("Select Game Mode");
		lblGameModesToGen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGameModesToGen.setForeground(Color.BLACK);
		lblGameModesToGen.setBounds(242, 120, 151, 14);
		panelConfig.add(lblGameModesToGen);

		// number of Game Modes to generate
		selectGameMode = new JComboBox(populateGameModes());
		selectGameMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.rebusX = (selectGameMode.getSelectedIndex() + 1);
				System.out.println("Rebus: " + Config.rebusX + " selected");
				updateWordBankSize();
			}
		});
		selectGameMode.setBounds(435, 118, 81, 19);
		panelConfig.add(selectGameMode);

		// Word Length
		selectWordLength = new JComboBox(populateWordMaxLengthBox());
		selectWordLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankMaxLength = (Integer) selectWordLength
						.getSelectedIndex();
				System.out.println("Selected new word bank max size of "
						+ Config.wordBankMaxLength);
				updateWordBankSize();
			}
		});
		selectWordLength.setBounds(283, 448, 66, 20);
		panelConfig.add(selectWordLength);

		// label time elapsed
		JLabel wordStrengthLabel = new JLabel("Word Strength");
		wordStrengthLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		wordStrengthLabel.setForeground(Color.BLACK);
		wordStrengthLabel.setBounds(71, 511, 151, 14);
		panelConfig.add(wordStrengthLabel);

		// label config tab header
		JLabel lblConfigHeader = new JLabel("Setup Configuration");
		lblConfigHeader.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfigHeader
				.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 32));
		lblConfigHeader.setBounds(135, 40, 493, 35);
		panelConfig.add(lblConfigHeader);
		panelConfig.setVisible(true);
		panelConfig.setLayout(null);

		wordBankSizeTextField = new JTextField();
		wordBankSizeTextField.setEditable(false);
		wordBankSizeTextField.setBounds(283, 328, 66, 20);
		panelConfig.add(wordBankSizeTextField);
		wordBankSizeTextField.setColumns(10);

		JLabel wordBankSizeLabel = new JLabel("Size of Current Word Pool");
		wordBankSizeLabel.setForeground(Color.BLACK);
		wordBankSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		wordBankSizeLabel.setBounds(71, 323, 175, 27);
		panelConfig.add(wordBankSizeLabel);

		JLabel topicLabel = new JLabel("Topic");
		topicLabel.setForeground(Color.BLACK);
		topicLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		topicLabel.setBounds(71, 389, 101, 14);
		panelConfig.add(topicLabel);

		JComboBox topicComboBox = new JComboBox(populateTopicBox());
		topicComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankTopic = (String) topicComboBox.getSelectedItem();
				System.out.println("Selected " + Config.wordBankTopic
						+ " as the new word bank topic");
				updateWordBankSize();
			}
		});
		topicComboBox.setBounds(198, 388, 151, 20);
		panelConfig.add(topicComboBox);

		JLabel solutionLengthLabel = new JLabel("Solution Length");
		solutionLengthLabel.setForeground(Color.BLACK);
		solutionLengthLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		solutionLengthLabel.setBounds(428, 448, 101, 14);
		panelConfig.add(solutionLengthLabel);

		JComboBox solutionLengthComboBox = new JComboBox(
				populateSolutionLengthBox());
		solutionLengthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionLength = (Integer) solutionLengthComboBox
						.getSelectedIndex();
				System.out.println("Selected new solution length of "
						+ Config.solutionLength);
				updateSolutionBankSize();
			}
		});
		solutionLengthComboBox.setBounds(618, 445, 75, 20);
		panelConfig.add(solutionLengthComboBox);

		JLabel languageLabel = new JLabel("Language");
		languageLabel.setForeground(Color.BLACK);
		languageLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		languageLabel.setBounds(242, 161, 89, 27);
		panelConfig.add(languageLabel);

		JComboBox languageComboBox = new JComboBox(populateLanguageBox());
		languageComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String language = (String) languageComboBox.getSelectedItem();
				Config.LANGUAGE = language.substring(0, 2);
				updateSolutionBankSize();
				updateWordBankSize();
				growlerConfig.showMessage("Set " + language
						+ " as the language");
			}
		});
		languageComboBox.setBounds(435, 166, 81, 20);
		panelConfig.add(languageComboBox);

		JLabel solutionBankSizeLabel = new JLabel(
				"Size of Current Solution Pool");
		solutionBankSizeLabel.setForeground(Color.BLACK);
		solutionBankSizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		solutionBankSizeLabel.setBounds(428, 331, 181, 14);
		panelConfig.add(solutionBankSizeLabel);

		solutionBankSizeTextField = new JTextField();
		solutionBankSizeTextField.setEditable(false);
		solutionBankSizeTextField.setBounds(618, 328, 75, 20);
		panelConfig.add(solutionBankSizeTextField);
		solutionBankSizeTextField.setColumns(10);

		JLabel solutionTopicLabel = new JLabel("Topic");
		solutionTopicLabel.setForeground(Color.BLACK);
		solutionTopicLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		solutionTopicLabel.setBounds(428, 391, 104, 14);
		panelConfig.add(solutionTopicLabel);

		JComboBox solutionTopicComboBox = new JComboBox(
				populateSolutionTopics());
		solutionTopicComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionBankTopic = (String) solutionTopicComboBox
						.getSelectedItem();
				System.out.println("Selected " + Config.solutionBankTopic
						+ " as the new solution bank topic");
				updateSolutionBankSize();
			}
		});
		solutionTopicComboBox.setBounds(558, 388, 135, 20);
		panelConfig.add(solutionTopicComboBox);

		JLabel lblSolutionWordStrength = new JLabel("Word Strength");
		lblSolutionWordStrength.setForeground(Color.BLACK);
		lblSolutionWordStrength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSolutionWordStrength.setBounds(428, 511, 135, 14);
		panelConfig.add(lblSolutionWordStrength);

		JComboBox solutionBankWordStrengthMin = new JComboBox(
				populateWordStrengths());
		solutionBankWordStrengthMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionBankWordStrengthMin = (Integer) solutionBankWordStrengthMin
						.getSelectedItem();
				System.out.println("Selected "
						+ Config.solutionBankWordStrengthMin
						+ " as the new solution bank min word strength");
				updateSolutionBankSize();
			}
		});
		solutionBankWordStrengthMin.setBounds(558, 510, 65, 20);
		panelConfig.add(solutionBankWordStrengthMin);

		JComboBox solutionBankWordStrengthMax = new JComboBox(
				populateWordStrengths());
		solutionBankWordStrengthMax.setSelectedIndex(9);
		solutionBankWordStrengthMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.solutionBankWordStrengthMax = (Integer) solutionBankWordStrengthMax
						.getSelectedItem();
				System.out.println("Selected "
						+ Config.solutionBankWordStrengthMax
						+ " as the new solution bank max word strength");
				updateSolutionBankSize();
			}
		});
		solutionBankWordStrengthMax.setBounds(633, 510, 60, 20);
		panelConfig.add(solutionBankWordStrengthMax);

		JComboBox wordBankWordStrengthMin = new JComboBox(
				populateWordStrengths());
		wordBankWordStrengthMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankWordStrengthMin = (Integer) wordBankWordStrengthMin
						.getSelectedItem();
				System.out.println("Selected " + Config.wordBankWordStrengthMin
						+ " as the new word bank min word strength");
				updateWordBankSize();
			}
		});
		wordBankWordStrengthMin.setBounds(198, 510, 75, 20);
		panelConfig.add(wordBankWordStrengthMin);

		// max elapsed time
		wordBankWordStrengthMax = new JComboBox(populateWordStrengths());
		wordBankWordStrengthMax.setSelectedIndex(9);
		wordBankWordStrengthMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Config.wordBankWordStrengthMax = (Integer) wordBankWordStrengthMax
						.getSelectedItem();
				System.out.println("Selected " + Config.wordBankWordStrengthMax
						+ " as the new word bank max word strength");
				updateWordBankSize();
			}
		});
		wordBankWordStrengthMax.setBounds(283, 510, 66, 20);
		panelConfig.add(wordBankWordStrengthMax);

		// label rows / columns
		JLabel lblRows = new JLabel("Max Word Length: ");
		lblRows.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRows.setForeground(Color.BLACK);
		lblRows.setBounds(71, 449, 151, 14);
		panelConfig.add(lblRows);

		JLabel lblWordBankOptions = new JLabel("Word Bank Options");
		lblWordBankOptions.setFont(new Font("David", Font.BOLD | Font.ITALIC,
				26));
		lblWordBankOptions.setBounds(93, 244, 256, 29);
		panelConfig.add(lblWordBankOptions);

		JLabel lblSolutionBankOptions = new JLabel("Solution Bank Options");
		lblSolutionBankOptions.setFont(new Font("David", Font.BOLD
				| Font.ITALIC, 26));
		lblSolutionBankOptions.setBounds(448, 244, 264, 29);
		panelConfig.add(lblSolutionBankOptions);

		updateWordBankSize();
		updateSolutionBankSize();

		// init Admin Panel
		JPanel panelAdmin = new JPanel();
		panelAdmin.setBackground(Color.GRAY);
		panelAdmin.setBounds(10, 51, 424, 431);
		panelAdmin.setLayout(null);
		tabStrip.addTab("Admin", panelAdmin);

		JLabel solutionWordLabelAdmin = new JLabel("Solution Word");
		solutionWordLabelAdmin.setBounds(10, 110, 87, 26);
		panelAdmin.add(solutionWordLabelAdmin);

		JLabel lblAdminPanel = new JLabel("Admin Panel");
		lblAdminPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminPanel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 29));
		lblAdminPanel.setBounds(135, 27, 493, 35);
		panelAdmin.add(lblAdminPanel);

		solutionWordTextFieldAdmin = new JTextField();
		solutionWordTextFieldAdmin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setButtonStatuses();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				setButtonStatuses();
			}
		});
		solutionWordTextFieldAdmin.setFont(font);
		solutionWordTextFieldAdmin.setBounds(99, 113, 135, 20);
		panelAdmin.add(solutionWordTextFieldAdmin);
		solutionWordTextFieldAdmin.setColumns(10);

		solutionWordButtonAdmin = new JButton("Generate Game Words");
		solutionWordButtonAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = "";
				try {
					word = solutionWordTextFieldAdmin.getText();
				} catch (NullPointerException error) {
					System.out.println("Solution word can't be empty");
					error.printStackTrace();
				}
				WordProcessor wp = new WordProcessor(word);
				BigWord bigWord = new BigWord();
				if (Config.LANGUAGE.equals("En")) {
					bigWord.setEnglish(word);
				} else {
					bigWord.setTelugu(word);
				}
				Config.solutionWord = wp.getLogicalChars();
				Config.solutionBigWord = bigWord;
				if (Config.LANGUAGE.equals("En")) {
					Config.solutionBigWord.setProcessedEnglish(wp
							.getLogicalChars());
				} else {
					Config.solutionBigWord.setProcessedTelegu(wp
							.getLogicalChars());
				}
				rebus.findGameWords();
				generateChosenWords();
				generateWordOptions();
				setButtonStatuses();
			}
		});
		solutionWordButtonAdmin.setBounds(529, 112, 184, 23);
		panelAdmin.add(solutionWordButtonAdmin);

		JLabel lblGameWords = new JLabel("Game Words");
		lblGameWords.setBounds(10, 338, 99, 14);
		panelAdmin.add(lblGameWords);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(245, 331, -11, -8);
		panelAdmin.add(textArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(84, 170, 206, 375);
		panelAdmin.add(scrollPane);

		solutionWordTextAreaAdmin = new JTextArea();
		solutionWordTextAreaAdmin.addKeyListener(new KeyAdapter() {
			@Override
			// Good when typing in characters (reacts to first char)
			public void keyReleased(KeyEvent e) {
				setButtonStatuses();
			}

			@Override
			// Good when trying to delete
			public void keyTyped(KeyEvent e) {
				setButtonStatuses();
			}
		});
		solutionWordTextAreaAdmin.setFont(font);
		scrollPane.setViewportView(solutionWordTextAreaAdmin);

		JButton btnNewButton_1 = new JButton("Generate Solution Word");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				if (Config.LANGUAGE.equals("En")) {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getEnglish());
				} else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
				}
				setButtonStatuses();
			}
		});
		btnNewButton_1.setBounds(300, 112, 174, 23);
		panelAdmin.add(btnNewButton_1);

		btnNewButton = new JButton("Commit Manually Typed Entries");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int wordNumber = 0;
				int stringsRead = 0;
				boolean badPuzzle = false;
				String word = "";
				try {
					word = solutionWordTextFieldAdmin.getText();
				} catch (NullPointerException error) {
					System.out.println("Solution word can't be empty");
					error.printStackTrace();
				}
				WordProcessor wp = new WordProcessor(word);
				BigWord bigWord = new BigWord();
				if (Config.LANGUAGE.equals("En")) {
					bigWord.setEnglish(word);
				} else {
					bigWord.setTelugu(word);
				}
				Config.solutionWord = wp.getLogicalChars();
				Config.solutionBigWord = bigWord;
				Config.GAME_WORDS.clear();
				Config.gameBigWords.clear();
				if (Config.LANGUAGE.equals("En")) {
					for (String line : solutionWordTextAreaAdmin.getText()
							.split("\\n")) {
						BigWord temp = null;
						for (int i = 0; i < Config.entireCollection.size(); i++) {
							if (Config.entireCollection.getBigWord(i)
									.getEnglish().equals(line)) {
								bigWord = Config.entireCollection.getBigWord(i);
							}
						}
						if (temp == null) {
							temp = new BigWord();
							temp.setEnglish(line);
							temp.setProcessedWord(line);
						}
						Config.gameBigWords.add(temp);
						// System.out.println("Adding " + temp.getEnglish());
						WordProcessor wordProccessor = new WordProcessor(line);
						Config.GAME_WORDS.add(wordProccessor.getLogicalChars());
						temp.setProcessedEnglish(wordProccessor
								.getLogicalChars());
						if (temp.getProcessedEnglish().size() < Config.rebusX) {
							badPuzzle = true;
							JOptionPane.showMessageDialog(
									null,
									"This puzzle is incorrect, game word "
											+ temp.getProcessedWord()
											+ " does not result in character "
											+ Config.solutionWord
													.get(wordNumber));
						} else if (!Config.solutionWord.get(wordNumber).equals(
								temp.getProcessedEnglish().get(
										Config.rebusX - 1))) {
							badPuzzle = true;
							JOptionPane.showMessageDialog(
									null,
									"This puzzle is incorrect, game word "
											+ temp.getProcessedWord()
											+ " does not result in character "
											+ Config.solutionWord
													.get(wordNumber));
						}
						stringsRead++;
						wordNumber++;
					}
				} else {
					for (String line : solutionWordTextAreaAdmin.getText()
							.split("\\n")) {
						BigWord temp = null;
						for (int i = 0; i < Config.entireCollection.size(); i++) {
							if (Config.entireCollection.getBigWord(i)
									.getEnglish().equals(line)) {
								bigWord = Config.entireCollection.getBigWord(i);
							}
						}
						if (temp == null) {
							temp = new BigWord();
							temp.setTelugu(line);
							temp.setProcessedWord(line);
						}
						Config.gameBigWords.add(temp);
						// System.out.println("Adding " + temp.getTelugu());
						WordProcessor wordProccessor = new WordProcessor(line);
						Config.GAME_WORDS.add(wordProccessor.getLogicalChars());
						temp.setProcessedTelegu(wordProccessor
								.getLogicalChars());
						stringsRead++;
						wordNumber++;
					}
				}
				if (stringsRead > Config.solutionWord.size()) {
					badPuzzle = true;
					JOptionPane
							.showMessageDialog(null,
									"This puzzle is incorrect, game words longer than solution length");
				}
				if (!badPuzzle) {
					growlerAdmin
							.showMessage("updated game solution and puzzle words");
				}
				setButtonStatuses();
			}
		});
		btnNewButton.setBounds(45, 581, 216, 56);
		panelAdmin.add(btnNewButton);

		JButton letsPlayButton1 = new JButton("I'm Feeling Lucky!");
		letsPlayButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rebus.pickSolutionWord();
				rebus.findGameWords();
				solutionWordTextAreaAdmin.setText("");
				if (Config.LANGUAGE.equals("En")) {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getEnglish());
				} else {
					solutionWordTextFieldAdmin.setText(Config.solutionBigWord
							.getTelugu());
				}
				generateChosenWords();
				generateWordOptions();
				growlerAdmin
						.showMessage("Generated a brand new complete puzzle");
				setButtonStatuses();
			}
		});
		letsPlayButton1.setBounds(271, 581, 216, 56);
		panelAdmin.add(letsPlayButton1);

		scrollPane_1 = new JScrollPane();
		scrollPane_1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(452, 170, 282, 375);
		panelAdmin.add(scrollPane_1);

		adminWordsJList = new JList();
		adminWordsJList.setCellRenderer(new TeluguFontListCellRenderer());
		scrollPane_1.setViewportView(adminWordsJList);
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	          JList theList = (JList) mouseEvent.getSource();
	          if (mouseEvent.getClickCount() == 2) {
	            int index = theList.locationToIndex(mouseEvent.getPoint());
	            System.out.println("double clicked index is" + index);
	            if (index >= 0) {
	              String entireString = (String) theList.getModel().getElementAt(index);
	              System.out.println("Double-clicked on: " + entireString);
	              int cutPoint = entireString.indexOf(" [");
					String word = "";
					word = entireString.substring(0, cutPoint);
					entireString = entireString.substring(cutPoint + 7,
							entireString.length());
					cutPoint = entireString.indexOf(",");
					int wordNumber = Integer.valueOf(entireString.substring(0,
							cutPoint));
					System.out.println(entireString);
					System.out.println(word);
					System.out.println(wordNumber);
					BigWord temp = new BigWord();
					temp.setProcessedWord(word);
					Config.gameBigWords.set(wordNumber, temp);
					generateChosenWords();
					setButtonStatuses();
	            }
	          }
	        }
	      };
	      adminWordsJList.addMouseListener(mouseListener);


		swapButton = new JButton("<<<  Swap In  <<<");
		swapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String entireString = adminWordsJList.getSelectedValue()
							.toString();
					System.out.println(entireString);
					int cutPoint = entireString.indexOf(" [");
					String word = "";
					word = entireString.substring(0, cutPoint);
					entireString = entireString.substring(cutPoint + 7,
							entireString.length());
					cutPoint = entireString.indexOf(",");
					int wordNumber = Integer.valueOf(entireString.substring(0,
							cutPoint));
					System.out.println(entireString);
					System.out.println(word);
					System.out.println(wordNumber);
					BigWord temp = new BigWord();
					temp.setProcessedWord(word);
					Config.gameBigWords.set(wordNumber, temp);
					generateChosenWords();
				} catch (NullPointerException exception) {
					JOptionPane
							.showMessageDialog(null,
									"you must select a word on the right to swap in first");
				}
				setButtonStatuses();

			}
		});
		swapButton.setBounds(300, 334, 142, 23);
		panelAdmin.add(swapButton);

		btnNewButton_2 = new JButton("Clear All Fields");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllFields();
				setButtonStatuses();
			}
		});
		btnNewButton_2.setBounds(497, 581, 216, 56);
		panelAdmin.add(btnNewButton_2);

		growlerPanelAdmin = new JPanel();
		growlerPanelAdmin.setBounds(45, 715, 668, 28);
		growlerPanelAdmin.setVisible(false);
		panelAdmin.add(growlerPanelAdmin);
		growlerAdmin = new Growler(Color.DARK_GRAY, Color.LIGHT_GRAY,
				growlerPanelAdmin);

		growlerPanelConfig = new JPanel();
		growlerPanelConfig.setBounds(55, 648, 658, 28);
		growlerPanelConfig.setVisible(false);
		panelConfig.add(growlerPanelConfig);
		growlerConfig = new Growler(Color.DARK_GRAY, Color.LIGHT_GRAY,
				growlerPanelConfig);

		// init Manage Panel
		JPanel panelManage = new JPanel();
		panelManage.setBackground(Color.GRAY);
		panelManage.setBounds(10, 51, 424, 431);
		panelManage.setLayout(null);
		tabStrip.addTab("Manage", panelManage);

		JLabel lblManagePanel = new JLabel("Manage Games");
		lblManagePanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblManagePanel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 40));
		lblManagePanel.setBounds(133, 64, 493, 49);
		panelManage.add(lblManagePanel);

		JLabel lblSolutionWord = new JLabel("Solution Word: ");
		lblSolutionWord.setBounds(62, 137, 106, 16);
		panelManage.add(lblSolutionWord);

		manageSolutionWord = new JTextField();
		manageSolutionWord.setFont(font);
		manageSolutionWord.setBounds(153, 134, 116, 22);
		panelManage.add(manageSolutionWord);
		manageSolutionWord.setColumns(20);
		manageSolutionWord.setEditable(false);

		JLabel lblGameWords_1 = new JLabel("Game Words:");
		lblGameWords_1.setBounds(62, 177, 89, 16);
		panelManage.add(lblGameWords_1);
		
		manageGameWords = new JTextArea();
		manageGameWords.setFont(font);
		
		JScrollPane scrollGame = new JScrollPane();
		scrollGame
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollGame
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollGame.setBounds(153, 177, 116, 220);
		panelManage.add(scrollGame);
		
		manageGameWords.setEditable(false);
		scrollGame.setViewportView(manageGameWords);
		JLabel lblGames = new JLabel("Games");
		lblGames.setBounds(437, 126, 56, 16);
		panelManage.add(lblGames);

		
		JScrollPane scrollGameList = new JScrollPane();
		scrollGameList
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollGameList
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollGameList.setBounds(358, 155, 204, 242);
		panelManage.add(scrollGameList);
		scrollGameList.setViewportView(manageGameList); 

		JButton btnGenerateHtml = new JButton("Generate HTML");
		btnGenerateHtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedSavedGame != null) {
					HtmlOutputProducer.openHtml(selectedSavedGame);
					growlerAdmin.showMessage("Generated HTML");
				}
			}
		});
		btnGenerateHtml.setBounds(133, 428, 136, 25);
		panelManage.add(btnGenerateHtml);

		JButton btnUpdateSavedGames = new JButton("Update Saved Games");
		btnUpdateSavedGames.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel model = new DefaultListModel();
				selectedSavedGame = null;
				for (SavedGame game : games.getGames()) {
					model.addElement(game);
				}
				games.saveGames();
				manageGameWords.setText("");
				manageSolutionWord.setText("");
				manageGameList.setModel(model);
			}
		});
		btnUpdateSavedGames.setBounds(358, 428, 204, 25);
		panelManage.add(btnUpdateSavedGames);

		generateHTML1 = new JButton("Generate HTML");
		generateHTML1.setForeground(new Color(0, 0, 0));
		// generateHTML1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		generateHTML1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Config.solutionWord != null && Config.gameBigWords != null) {
					HtmlOutputProducer.openHtml();
					growlerAdmin.showMessage("Generated HTML");
				}
				growlerAdmin.showMessage("No game to create HTML ");
			}
		});

		generateHTML1.setBounds(45, 648, 216, 56);
		panelAdmin.add(generateHTML1);

		saveGameButton = new JButton("Save Game");
		saveGameButton.setForeground(new Color(0, 0, 0));
		// saveGameButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		saveGameButton.setBounds(497, 648, 216, 56);
		saveGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Config.gameBigWords != null && Config.solutionWord != null) {
					SavedGame game = new SavedGame(new ArrayList<String>(
							Config.solutionWord), new ArrayList<BigWord>(
							Config.gameBigWords));
					games.addGames(game);
					games.saveGames();
					growlerAdmin.showMessage("Saved Game");
					System.out.println("Saved Game Count: "
							+ games.getSavedGameCount());
				}

			}

		});
		panelAdmin.add(saveGameButton);
		setButtonStatuses();
	}

	public Vector<String> populateGameModes() {
		Vector<String> retVal = new Vector<String>();
		retVal.add("Rebus 1");
		retVal.add("Rebus 2");
		retVal.add("Rebus 3");
		retVal.add("Rebus 4");
		retVal.add("Rebus 5");
		return retVal;
	}

	/**
	 * Generates a new Config.gameCollectionWordBank based on the current
	 * settings Updates the field that display the size of the current
	 * collection
	 */
	public void updateWordBankSize() {
		rebus.generateWordBank();
		wordBankSizeTextField.setText(String
				.valueOf(Config.gameCollectionWordBank.size()));
	}

	/**
	 * Generates a new Config.gameCollectionSolutionBank based on the current
	 * settings Updates the field that display the size of the current
	 * collection
	 */
	public void updateSolutionBankSize() {
		rebus.generateSolutionBank();
		solutionBankSizeTextField.setText(String
				.valueOf(Config.gameCollectionSolutionBank.size()));
	}

	/**
	 * Populates the topic list for the word bank
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

	/**
	 * Populates the topic list for the solution bank
	 *
	 *
	 * @return
	 */
	private Object[] populateSolutionTopics() {
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

	private Vector<Object> populateSolutionLengthBox() {
		Vector<Object> retVal = new Vector<Object>();
		retVal.add("Any");
		for (int i = 1; i < 30; i++) {
			retVal.add(i);
		}
		return retVal;
	}

	private Vector<Object> populateWordMaxLengthBox() {
		Vector<Object> retVal = new Vector<Object>();
		retVal.add("Any");
		for (int i = 1; i < 30; i++) {
			retVal.add(i);
		}
		return retVal;
	}

	public Vector<String> populateLanguageBox() {
		Vector<String> retVal = new Vector<String>();
		retVal.add("English");
		retVal.add("Telugu");
		return retVal;
	}

	private Vector<Integer> populateWordStrengths() {
		Vector<Integer> retVal = new Vector<Integer>();
		for (int i = 1; i < 11; i++) {
			retVal.add(i);
		}
		return retVal;
	}

	private void generateWordOptions() {
		Vector<String> options = new Vector<String>();
		if (Config.LANGUAGE.equals("En")) {
			for (int i = 0; i < Config.solutionWord.size(); i++) {
				for (int j = 0; j < Config.potentialGameWords.size(); j++) {
					if (Config.potentialGameWords.get(j).getProcessedEnglish()
							.get(Config.rebusX - 1)
							.equals(Config.solutionWord.get(i))) {
						options.add(Config.potentialGameWords.get(j)
								.getEnglish()
								+ " [Word "
								+ i
								+ ", Rebus "
								+ Config.rebusX
								+ ", Letter "
								+ Config.solutionWord.get(i) + "]\n");
					}
				}
			}
		} else {
			for (int i = 0; i < Config.solutionWord.size(); i++) {
				for (int j = 0; j < Config.potentialGameWords.size(); j++) {
					if (Config.potentialGameWords.get(j).getProcessedTelegu()
							.get(Config.rebusX - 1)
							.equals(Config.solutionWord.get(i))) {
						options.add(Config.potentialGameWords.get(j)
								.getTelugu()
								+ " [Word "
								+ i
								+ ", Rebus "
								+ Config.rebusX
								+ ", Letter "
								+ Config.solutionWord.get(i) + "]\n");
					}
				}
			}
		}
		adminWordsJList = new JList<String>(options);
		adminWordsJList.setCellRenderer(new TeluguFontListCellRenderer());
		scrollPane_1.setViewportView(adminWordsJList);
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	          JList theList = (JList) mouseEvent.getSource();
	          if (mouseEvent.getClickCount() == 2) {
	            int index = theList.locationToIndex(mouseEvent.getPoint());
	            System.out.println("double clicked index is" + index);
	            if (index >= 0) {
	              String entireString = (String) theList.getModel().getElementAt(index);
	              System.out.println("Double-clicked on: " + entireString);
	              int cutPoint = entireString.indexOf(" [");
					String word = "";
					word = entireString.substring(0, cutPoint);
					entireString = entireString.substring(cutPoint + 7,
							entireString.length());
					cutPoint = entireString.indexOf(",");
					int wordNumber = Integer.valueOf(entireString.substring(0,
							cutPoint));
					System.out.println(entireString);
					System.out.println(word);
					System.out.println(wordNumber);
					BigWord temp = new BigWord();
					temp.setProcessedWord(word);
					Config.gameBigWords.set(wordNumber, temp);
					generateChosenWords();
					setButtonStatuses();
	            }
	          }
	        }
	      };
	      adminWordsJList.addMouseListener(mouseListener);
	}

	private void generateChosenWords() {
		solutionWordTextAreaAdmin.setText("");
		if (Config.LANGUAGE.equals("En")) {
			solutionWordTextFieldAdmin.setText(Config.solutionBigWord
					.getEnglish());
			for (int i = 0; i < Config.gameBigWords.size(); i++) {
				solutionWordTextAreaAdmin.append(Config.gameBigWords.get(i)
						.getProcessedWord() + "\n");
			}
		} else {
			solutionWordTextFieldAdmin.setText(Config.solutionBigWord
					.getTelugu());
			for (int i = 0; i < Config.gameBigWords.size(); i++) {
				solutionWordTextAreaAdmin.append(Config.gameBigWords.get(i)
						.getProcessedWord() + "\n");
			}
		}
	}

	private void clearAllFields() {
		solutionWordTextAreaAdmin.setText("");
		solutionWordTextFieldAdmin.setText("");
		adminWordsJList = new JList<String>();
		adminWordsJList.setCellRenderer(new TeluguFontListCellRenderer());
		scrollPane_1.setViewportView(adminWordsJList);
		adminWordsJList.setCellRenderer(new TeluguFontListCellRenderer());
		Config.solutionBigWord = null;
		Config.gameBigWords.clear();
	}

	private void setButtonStatuses() {
		String solutionWordTextField = solutionWordTextFieldAdmin.getText();
		solutionWordTextField.replaceAll("\\s+", "");
		String solutionWordTextArea = solutionWordTextAreaAdmin.getText();
		solutionWordTextArea.replaceAll("\\s+", "");
		// Committ Manually Typed Entries Button
		btnNewButton.setEnabled(true);
		generateHTML1.setEnabled(true);
		saveGameButton.setEnabled(true);
		solutionWordButtonAdmin.setEnabled(true);
		// Clear All Fields Button
		btnNewButton_2.setEnabled(true);
		swapButton.setEnabled(true);
		// If Jlist is empty
		if (Config.gameBigWords.size() == 0) {
			generateHTML1.setEnabled(false);
			saveGameButton.setEnabled(false);
		}
		if (adminWordsJList.getModel().getSize() == 0) {
			swapButton.setEnabled(false);
		}
		if (solutionWordTextField.equals("")) {
			btnNewButton.setEnabled(false);
			generateHTML1.setEnabled(false);
			saveGameButton.setEnabled(false);
			solutionWordButtonAdmin.setEnabled(false);
			if (solutionWordTextArea.equals("")) {
				if (adminWordsJList.getLastVisibleIndex() == -1) {
					btnNewButton_2.setEnabled(false);
				}
			}
		} else if (solutionWordTextArea.equals("")) {
			btnNewButton.setEnabled(false);
			generateHTML1.setEnabled(false);
			saveGameButton.setEnabled(false);
		}
	}
}