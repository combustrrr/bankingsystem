package main.java.com.banking.system.ui.panels.buttons;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A custom JButton with a rounded rectangle shape and a modern design.
 */
public class RoundedButton extends JButton {

    private static final int DEFAULT_CORNER_RADIUS = 20;
    private static final int DEFAULT_PADDING = 10;
    private static final int DEFAULT_FONT_SIZE = 14;

    private int cornerRadius;
    private Color backgroundColor;
    private Color foregroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private Font font;

    /**
     * Creates a new RoundedButton with the specified text.
     *
     * @param text the text to be displayed on the button
     */
    public RoundedButton(String text) {
        this(text, DEFAULT_CORNER_RADIUS, new Color(0, 150, 255), Color.WHITE, new Color(0, 180, 255), new Color(0, 120, 255), new Font("Arial", Font.BOLD, DEFAULT_FONT_SIZE));
    }

    /**
     * Creates a new RoundedButton with the specified text, corner radius, background color, foreground color, hover color, pressed color, and font.
     *
     * @param text the text to be displayed on the button
     * @param cornerRadius the corner radius of the button
     * @param backgroundColor the background color of the button
     * @param foregroundColor the foreground color of the button
     * @param hoverColor the hover color of the button
     * @param pressedColor the pressed color of the button
     * @param font the font of the button
     */
    public RoundedButton(String text, int cornerRadius, Color backgroundColor, Color foregroundColor, Color hoverColor, Color pressedColor, Font font) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        this.font = font;

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(true);
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setFont(font);
        setBorder(new EmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
        setMargin(new Insets(0, 0, 0, 0));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g);
    }
}