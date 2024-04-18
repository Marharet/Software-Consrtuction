package ua.edu.tntu._121_se.midipiano.ui.pianokeys;

import javax.swing.*;
import java.awt.*;

public class WhitePianoKeyButton extends PianoKeyButton {

    public enum WhiteButtonType {
        Whole,
        LeftGroove,
        RightGroove,
        BothGrooves
    }
    public static final int BUTTON_WIDTH = 50;
    public static final int BUTTON_HEIGHT = 200;
    private static final int BORDER_WIDTH = 1;
    private static final int TEXT_FONT_SIZE = 20;

    private static final Color COLOR = Color.WHITE;
    private static final Color COLOR_PRESSED = new Color(220, 220, 220);
    private static final Color COLOR_BORDER = Color.BLACK;
    private static final Color COLOR_TEXT = Color.BLACK;

    private WhiteButtonType type;

    public WhitePianoKeyButton(int correspondingKeyCode) {
        this(WhiteButtonType.Whole, correspondingKeyCode);
    }

    public WhitePianoKeyButton(WhiteButtonType type, int correspondingKeyCode) {
        super(correspondingKeyCode);
        this.type = type;

        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public void setType(WhiteButtonType type) {
        this.type = type;
    }

    public WhiteButtonType getType() {
        return type;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(COLOR_BORDER);
        g2.fillRect(0, 0, BUTTON_WIDTH , BUTTON_HEIGHT);

        g2.setColor(isPressed ? COLOR_PRESSED : COLOR);
        if (type == WhiteButtonType.Whole) {
            g2.fillRect(BORDER_WIDTH, BORDER_WIDTH, BUTTON_WIDTH - 2 * BORDER_WIDTH, BUTTON_HEIGHT - 2 * BORDER_WIDTH);
            drawKeyName(g);
            g2.dispose();
            return;
        }

        g2.fillRect(BORDER_WIDTH, BlackPianoKeyButton.BUTTON_HEIGHT, BUTTON_WIDTH - 2 * BORDER_WIDTH, BUTTON_HEIGHT - BlackPianoKeyButton.BUTTON_HEIGHT - BORDER_WIDTH);
        switch (type) {
            case LeftGroove -> {
                g2.fillRect(BlackPianoKeyButton.BUTTON_WIDTH / 2 + BORDER_WIDTH, BORDER_WIDTH,  BUTTON_WIDTH - BlackPianoKeyButton.BUTTON_WIDTH / 2 - 2 * BORDER_WIDTH, BlackPianoKeyButton.BUTTON_HEIGHT);
            }
            case RightGroove -> {
                g2.fillRect(BORDER_WIDTH, BORDER_WIDTH, BUTTON_WIDTH - BlackPianoKeyButton.BUTTON_WIDTH / 2 - 2 * BORDER_WIDTH, BlackPianoKeyButton.BUTTON_HEIGHT);
            }
            case BothGrooves -> {
                g2.fillRect(BlackPianoKeyButton.BUTTON_WIDTH / 2 + BORDER_WIDTH, BORDER_WIDTH, BUTTON_WIDTH - BlackPianoKeyButton.BUTTON_WIDTH - 2 * BORDER_WIDTH, BlackPianoKeyButton.BUTTON_HEIGHT);
            }
            default -> {
                throw new IllegalArgumentException("Unexpected value: " + type);
            }
        }

        drawKeyName(g);

        g2.dispose();
    }

    private void drawKeyName(Graphics g) {
        g.setColor(COLOR_TEXT);
        g.setFont(new Font(KEY_TEXT_FONT, Font.PLAIN, TEXT_FONT_SIZE));
        g.drawString(getCorrespondingKeyName(), BUTTON_WIDTH / 2 - TEXT_FONT_SIZE / 3, BUTTON_HEIGHT - TEXT_FONT_SIZE);
    }
}
