package sebcel.inwentarz.gui.splash;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;

public class Splash extends Frame {

    private static final long serialVersionUID = 2580226199612733490L;

    private SplashScreen splash;

    public void show(String title) {
	splash = SplashScreen.getSplashScreen();
	if (splash == null) {
	    throw new RuntimeException("Can not obtain splash");
	}
	Graphics2D g = splash.createGraphics();
	if (g == null) {
	    throw new RuntimeException("Can not create splash graphics");
	}

	g.setComposite(AlphaComposite.Clear);
	g.setPaintMode();
	g.setColor(Color.WHITE);
	g.setFont(new Font("Times", Font.PLAIN, 48));
	Rectangle2D titleBounds = g.getFontMetrics().getStringBounds(title, g);
	int x = ((int) splash.getSize().getWidth() - (int) titleBounds.getWidth()) / 2;
	int y = ((int) splash.getSize().getHeight() - (int) titleBounds.getHeight()) / 2;

	g.drawString(title, x, y);
	splash.update();
    }

    public void hide() {
	splash.close();
    }
}