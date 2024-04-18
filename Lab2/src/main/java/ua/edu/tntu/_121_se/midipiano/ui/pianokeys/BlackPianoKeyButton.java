package ua.edu.tntu._121_se.midipiano.ui.pianokeys;

import java.awt.*;

public class BlackPianoKeyButton extends PianoKeyButton {

    public static final int BUTTON_WIDTH = 30;
    public static final int BUTTON_HEIGHT = 100;
    private static final int TEXT_FONT_SIZE = 15;

    private static final Color COLOR = Color.BLACK;
    private static final Color COLOR_PRESSED = new Color(50, 50, 50);
    private static final Color COLOR_TEXT = Color.WHITE;
    public BlackPianoKeyButton(int correspondingKeyCode) {
        super(correspondingKeyCode);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(isPressed ? COLOR_PRESSED : COLOR);

        g2.fillRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);

        g.setColor(COLOR_TEXT);
        g.setFont(new Font(KEY_TEXT_FONT, Font.PLAIN, TEXT_FONT_SIZE));
        g.drawString(getCorrespondingKeyName(), BUTTON_WIDTH / 2 - TEXT_FONT_SIZE / 3, BUTTON_HEIGHT - TEXT_FONT_SIZE);

        g2.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}
