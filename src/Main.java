import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class Main {
    private static ArrayList<String> targetWords = new ArrayList<>();
    private static String targetWord;
    private static StringBuilder currentGuess;
    private static int remainingGuesses = 7;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("wordlist.txt"));
        while(in.hasNext()){
            targetWords.add(in.next());
        }
        targetWord = getWord();
        currentGuess = new StringBuilder("_".repeat(targetWord.length()));
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    public static String getWord(){
        Random r = new Random();
        String word = targetWords.get(r.nextInt(targetWords.size()));
        System.out.println("The target word is: " + word);
        return word;
    }

    private static void createAndShowGUI()  {
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.setPreferredSize(new Dimension(1050, 950));
        frame.add(panel);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;


        ImageIcon gallows = new ImageIcon("gallows.png");
        ImageIcon head = new ImageIcon("head.png");
        ImageIcon body = new ImageIcon("body.png");
        ImageIcon rightArm = new ImageIcon("right arm.png");
        ImageIcon leftArm = new ImageIcon("left arm.png");
        ImageIcon rightLeg = new ImageIcon("right leg.png");
        ImageIcon leftLeg = new ImageIcon("left leg.png");

        JLabel hang = new JLabel();

        JLabel guessText = new JLabel("Guess");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(guessText, gbc);

        JLabel guessLabel = new JLabel(currentGuess.toString(), SwingConstants.CENTER);
        guessLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;
        panel.add(guessLabel, gbc);

        JLabel remainingLabel = new JLabel("Guesses Remaining: " + remainingGuesses);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(remainingLabel, gbc);

        JLabel incorrectLabel = new JLabel("Incorrect guesses: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(incorrectLabel, gbc);

        JTextField letterField = new JTextField(1);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(letterField, gbc);

        JButton letterButton = new JButton("Submit Guess");
        letterButton.addActionListener(e -> {
            String guess = letterField.getText().toLowerCase();
            if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                JOptionPane.showMessageDialog(frame, "Please enter a single letter.");
                return;
            }
            boolean correctGuess = false;
            for (int i = 0; i < targetWord.length(); i++) {
                if (targetWord.charAt(i) == guess.charAt(0)) {
                    currentGuess.setCharAt(i, guess.charAt(0));
                    correctGuess = true;
                }
            }
            if (!correctGuess) {
                remainingGuesses--;
                remainingLabel.setText("Guesses Remaining: " + remainingGuesses);
                incorrectLabel.setText(incorrectLabel.getText() + " " + guess);
                if (remainingGuesses == 0) {
                    leftLeg.getImage().flush();
                    hang.setIcon(leftLeg);
                    JOptionPane.showMessageDialog(frame, "Game over! The word was: " + targetWord);
                    System.exit(0);
                }
                if (remainingGuesses == 1) {
                    rightLeg.getImage().flush();
                    hang.setIcon(rightLeg);
                }
                if (remainingGuesses == 2) {
                    leftArm.getImage().flush();
                    hang.setIcon(leftArm);
                }
                if (remainingGuesses == 3) {
                    rightArm.getImage().flush();
                    hang.setIcon(rightArm);
                }
                if (remainingGuesses == 4) {
                    body.getImage().flush();
                    hang.setIcon(body);
                }
                if (remainingGuesses == 5) {
                    head.getImage().flush();
                    hang.setIcon(head);
                }
                if (remainingGuesses == 6) {
                    gallows.getImage().flush();
                    hang.setIcon(gallows);
                }
                if (remainingGuesses == 7) {
                    hang.setIcon(null);
                }

            } else if (currentGuess.toString().equals(targetWord)) {
                JOptionPane.showMessageDialog(frame, "You win! The word was: " + targetWord);
                System.exit(0);
            }
            guessLabel.setText(currentGuess.toString());
            letterField.setText("");
        });

        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(letterButton, gbc);

        JTextField wordField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 0;
        panel.add(wordField, gbc);

        JButton wordButton = new JButton("Submit Word");
        wordButton.addActionListener(e -> {
            String guess = wordField.getText().toLowerCase();
            if (guess.equals(targetWord)) {
                JOptionPane.showMessageDialog(frame, "You win! The word was: " + targetWord);
                System.exit(0);
            } else {
                if (guess.matches(".*[\\d\\W].*") || guess.length() < 7 || guess.length() > 10 ) {
                    JOptionPane.showMessageDialog(frame, "Guesses can't contain numbers or symbols and must be a 7 to 10 letter word");
                } else {
                    remainingGuesses--;
                    remainingLabel.setText("Guesses Remaining: " + remainingGuesses);
                    incorrectLabel.setText(incorrectLabel.getText() + " " + guess);
                    if (remainingGuesses == 0) {
                        leftLeg.getImage().flush();
                        hang.setIcon(leftLeg);
                        JOptionPane.showMessageDialog(frame, "Game over! The word was: " + targetWord);
                        System.exit(0);
                    }
                    if (remainingGuesses == 1) {
                        rightLeg.getImage().flush();
                        hang.setIcon(rightLeg);
                    }
                    if (remainingGuesses == 2) {
                        leftArm.getImage().flush();
                        hang.setIcon(leftArm);
                    }
                    if (remainingGuesses == 3) {
                        rightArm.getImage().flush();
                        hang.setIcon(rightArm);
                    }
                    if (remainingGuesses == 4) {
                        body.getImage().flush();
                        hang.setIcon(body);
                    }
                    if (remainingGuesses == 5) {
                        head.getImage().flush();
                        hang.setIcon(head);
                    }
                    if (remainingGuesses == 6) {
                        gallows.getImage().flush();
                        hang.setIcon(gallows);
                    }
                    if (remainingGuesses == 7) {
                        hang.setIcon(null);
                    }
                }
            }
            wordField.setText("");
        });

        gbc.gridx = 4;
        gbc.gridy = 0;
        panel.add(wordButton, gbc);

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> {
            for (int i = 0; i < targetWord.length(); i++) {
                if (currentGuess.charAt(i) == '_') {
                    currentGuess.setCharAt(i, targetWord.charAt(i));
                    remainingGuesses--;
                    remainingLabel.setText("Guesses Remaining: " + remainingGuesses);
                    if (remainingGuesses == 0) {
                        leftLeg.getImage().flush();
                        hang.setIcon(leftLeg);
                        JOptionPane.showMessageDialog(frame, "Game over! The word was: " + targetWord);
                        System.exit(0);
                    }
                    if (remainingGuesses == 1) {
                        rightLeg.getImage().flush();
                        hang.setIcon(rightLeg);
                    }
                    if (remainingGuesses == 2) {
                        leftArm.getImage().flush();
                        hang.setIcon(leftArm);
                    }
                    if (remainingGuesses == 3) {
                        rightArm.getImage().flush();
                        hang.setIcon(rightArm);
                    }
                    if (remainingGuesses == 4) {
                        body.getImage().flush();
                        hang.setIcon(body);
                    }
                    if (remainingGuesses == 5) {
                        head.getImage().flush();
                        hang.setIcon(head);
                    }
                    if (remainingGuesses == 6) {
                        gallows.getImage().flush();
                        hang.setIcon(gallows);
                    }
                    if (remainingGuesses == 7) {
                        hang.setIcon(null);
                    }
                    break;
                }
            }
            guessLabel.setText(currentGuess.toString());
        });
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(hintButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(hang, gbc);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}