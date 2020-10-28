package bounce.forms;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import bounce.ImageRectangleShape;
import bounce.NestingShape;
import bounce.ShapeModel;
import bounce.forms.util.Form;
import bounce.forms.util.FormHandler;

public class ImageShapeFormHandler implements FormHandler {
	private ShapeModel _model;
    private NestingShape _parentOfNewShape;
    
    public ImageShapeFormHandler(ShapeModel model,NestingShape parent) {
        _model = model;
        _parentOfNewShape = parent;
    }

    @Override
    public void processForm(Form form) {
		long startTime = System.currentTimeMillis();
		
		// Read field values from the form.
		File imageFile = (File)form.getFieldValue(File.class, ImageFormElement.IMAGE);
		int width = form.getFieldValue(Integer.class, ShapeFormElement.WIDTH);
		int deltaX = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_X);
		int deltaY = form.getFieldValue(Integer.class, ShapeFormElement.DELTA_Y);

		SwingWorker<BufferedImage, Void> worker = new ImageShapeScalerWorker(imageFile, width, deltaX, deltaY);
		worker.execute();

		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("Image loading and scaling took " + elapsedTime + "ms.");

    }
	
	private class ImageShapeScalerWorker extends SwingWorker<BufferedImage, Void> {

		private File _imageFile;
		private int _width, _deltaX, _deltaY;

		public ImageShapeScalerWorker(File imageFile, int width, int deltaX, int deltaY){
			_imageFile = imageFile;
			_width = width;
			_deltaX = deltaX;
			_deltaY = deltaY;
		}

		@Override
		protected BufferedImage doInBackground() throws Exception {
			// Load the original image (ImageIO.read() is a blocking call).
		BufferedImage fullImage = null;
		try {
			fullImage = ImageIO.read(_imageFile);
		} catch(IOException e) {
			System.out.println(e);
			System.out.println("Error loading image.");
		}
		
		int fullImageWidth = fullImage.getWidth();
		int fullImageHeight = fullImage.getHeight();
				
		BufferedImage scaledImage = fullImage;
				
		// Scale the image if necessary.
		if(fullImageWidth > _width) {
			double scaleFactor = (double)_width / (double)fullImageWidth;
			int height = (int)((double)fullImageHeight * scaleFactor);
					
			scaledImage = new BufferedImage(_width,height,BufferedImage.TYPE_INT_RGB); 
			Graphics2D g = scaledImage.createGraphics();
					
			// Method drawImage() scales an already loaded image. The 
			// ImageObserver argument is null because we don't need to monitor 
			// the scaling operation.
			g.drawImage(fullImage, 0, 0, _width, height, null);
		}
			return scaledImage;
		}

		@Override
		protected void done() {
			try {
				BufferedImage scaledImage = get();
				// Create the new Shape and add it to the model.
				ImageRectangleShape imageShape = new ImageRectangleShape(_deltaX, _deltaY, scaledImage);
				_model.add(imageShape, _parentOfNewShape);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}
}