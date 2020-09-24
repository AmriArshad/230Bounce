package bounce;

import java.awt.Color;

/**
 * Class to represent a dynamic rectangle.
 * 
 */
public class DynamicRectangleShape extends RectangleShape{

	protected Color COLOR = Color.red;
	protected boolean bounced = false;

    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height){
        super(x, y, deltaX, deltaY, width, height);
    }

    public DynamicRectangleShape(int x, int y, int deltaX, int deltaY, int width, int height, Color color){
        super(x, y, deltaX, deltaY, width, height);
        COLOR = color;
    }

    @Override
    public void paint(Painter painter) {
		if (bounced == false){
			painter.setColor(COLOR);
			painter.fillRect(_x,_y,_width,_height);
			painter.setColor(Color.black);	
		}
		else{
			super.paint(painter);
		}
    }

    @Override
    public void move(int width, int height) {
		super.move(width, height);

		if (x() == 0 || x() == width - width()){
			bounced = false;
		}
		else if (y() == 0 || y() == height - height()){
			bounced = true;
		}
	}

}
