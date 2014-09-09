public class SeamCarver {
	private Picture picture;
	
	public SeamCarver(Picture pic) {
		picture = pic;
	}
	
	Picture getPicture () {
		return picture;
	}
	
	int width() {
		return picture.width();
	}
	
	int height() {
		return picture.height();
	}
	
	public double energy(int x, int y) {
        if (x < 0 || x >= width())  throw new IndexOutOfBoundsException("x must be between 0 and " + (width()-1) + ". Entered: " + x);
        if (y < 0 || y >= height()) throw new IndexOutOfBoundsException("y must be between 0 and " + (height()-1) + ". Entered: " + y);
        
		double deltaX = getDelta(x, y, true);
		double deltaY = getDelta(x, y, false);
		
		return deltaX + deltaY;
	}
	
	public double getDelta(int x, int y, boolean deltaXOrY) {
		int loX = x, roX = x; //left of X, right of X
		int toY = y, boY = y; //top of Y, bottom of Y
		
		if(deltaXOrY) {
			loX= x - 1;
			roX = x + 1;
		} else {
			toY = y - 1;
			boY = y + 1;
		}
		
		double r1 = 0, r2 = 0, g1 = 0, g2 = 0, b1 = 0, b2 = 0;
		
		if(loX >= 0 && toY >= 0) {
			r1 = picture.get(loX, toY).getRed();
			g1 = picture.get(loX, toY).getGreen();
			b1 = picture.get(loX, toY).getBlue();
		}
		if(roX < width() && boY < height()) {
			r2 = picture.get(roX, boY).getRed();
			g2 = picture.get(roX, boY).getGreen();
			b2 = picture.get(roX, boY).getBlue();
		}
		
		double R, G, B;

		R = r1 - r2;
		G = g1 - g2;
		B = b1 - b2;
		
		return (R * R) + (G * G) + (B * B);
	}

	//find (w*h + w + h)
	public int[] findHorizontalSeam() {
		int[] result = new int[width()];
		double[][] energies = new double[width()][height()];
		
		for(int i = 0; i < width(); i++) { //w
			for(int j = 0; j < height(); j++) { //h
				double lowestEnergy = 0;
				if(i > 0) {
					double aboveValue = (j > 0) ? energies[i-1][j-1] : -1;
					double forwardValue = energies[i-1][j];
					double bottomValue = (j < height()-1) ? energies[i-1][j+1] : -1;
					
					lowestEnergy = (aboveValue != -1) ? Math.min(aboveValue, forwardValue) : forwardValue;
					lowestEnergy = (bottomValue != -1) ? Math.min(lowestEnergy, bottomValue) : lowestEnergy;
				}
				energies[i][j] = lowestEnergy + energy(i, j);
			}
		}
		int lowestIndex = 0;
		int maxX = width()-1;
		
		for(int i = 1; i < height(); i++) { //h
			if(energies[maxX][i] < energies[maxX][lowestIndex]) {
				lowestIndex = i;
			}
		}
		
		result[maxX] = lowestIndex;
		
		int currentX = maxX;
		while(currentX > 0) { //w
			int index = result[currentX];
			currentX--;
			
			int nextLowestIndex = index;
			double lowestEnergy = energies[currentX][index];
			
			if(index != 0) {
				double tempnergy = energies[currentX][index-1];
				if(tempnergy < lowestEnergy) {
					lowestEnergy = tempnergy;
					nextLowestIndex = index-1;
				}
			}
			if(index < height()-1) {
				double tempnergy = energies[currentX][index+1];
				if(tempnergy < lowestEnergy) {
					nextLowestIndex = index+1;
				}
			}
			
			result[currentX] = nextLowestIndex;
		}
		
		return result;
	}

	public int[] findVerticalSeam() {
		int[] result = new int[height()];
		double[][] energies = new double[width()][height()];
		
		for(int i = 0; i < height(); i++) { //w
			for(int j = 0; j < width(); j++) { //h
				double lowestEnergy = 0;
				if(i > 0) {
					double aboveValue = (j > 0) ? energies[j-1][i-1] : -1;
					double forwardValue = energies[j][i-1];
					double bottomValue = (j < width()-1) ? energies[j+1][i-1] : -1;
					
					lowestEnergy = (aboveValue != -1) ? Math.min(aboveValue, forwardValue) : forwardValue;
					lowestEnergy = (bottomValue != -1) ? Math.min(lowestEnergy, bottomValue) : lowestEnergy;
				}
				energies[j][i] = lowestEnergy + energy(j, i);
			}
		}
		int lowestIndex = 0;
		int maxY = height()-1;
		
		for(int i = 1; i < width(); i++) { //w
			if(energies[i][maxY] < energies[lowestIndex][maxY]) {
				lowestIndex = i;
			}
		}
		
		result[maxY] = lowestIndex;
		
		int currentY = maxY;
		while(currentY > 0) { //h
			int index = result[currentY];
			currentY--;
			
			int nextLowestIndex = index;
			double lowestEnergy = energies[index][currentY];
			
			if(index != 0) {
				double tempnergy = energies[index-1][currentY];
				if(tempnergy < lowestEnergy) {
					lowestEnergy = tempnergy;
					nextLowestIndex = index-1;
				}
			}
			if(index < width()-1) {
				double tempnergy = energies[index+1][currentY];
				if(tempnergy < lowestEnergy) {
					nextLowestIndex = index+1;
				}
			}
			
			result[currentY] = nextLowestIndex;
		}
		
		return result;
	}
	
	//removal (w * h)
	public void removeHorizontalSeam(int[] indices) {
		if(height() == 1) throw new IllegalArgumentException("Picture height is 1. Cannot seam.");
		if(indices.length != width()) throw new IllegalArgumentException("Received parameter array is not the correct size.");
		
		Picture pic = new Picture(width(), height()-1);
		
		for(int i = 0; i < width(); i++) { //w
			int offsetPoint = indices[i];
			for(int j = 0; j < pic.height(); j++) { //h-1
				if(j >= offsetPoint) {
					pic.set(i, j, picture.get(i, j+1) );
				} else {
					pic.set(i, j, picture.get(i, j) );
				}
			}
		}
		
		picture = pic;
	}

	public void removeVerticalSeam(int[] indices) {
		if(width() == 1) throw new IllegalArgumentException("Picture width is 1. Cannot seam.");
		if(indices.length != height()) throw new IllegalArgumentException("Received parameter array is not the correct size.");
		
		Picture pic = new Picture(width()-1, height());
		
		for(int i = 0; i < height(); i++) { //h
			int offsetPoint = indices[i];
			for(int j = 0; j < pic.width(); j++) { //w-1
				if(j >= offsetPoint) {
					pic.set(j, i, picture.get(j+1, i) );
				} else {
					pic.set(j, i, picture.get(j, i) );
				}
			}
		}
		
		picture = pic;
	}
}
